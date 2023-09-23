package com.example.hive.service.implementation;

import com.example.hive.dto.request.TaskDto;
import com.example.hive.dto.response.AppResponse;
import com.example.hive.dto.response.TaskResponseDto;
import com.example.hive.entity.*;
import com.example.hive.enums.Role;
import com.example.hive.enums.Status;
import com.example.hive.exceptions.BadRequestException;
import com.example.hive.exceptions.CustomException;
import com.example.hive.exceptions.ResourceNotFoundException;
import com.example.hive.repository.*;
import com.example.hive.service.TaskService;
import com.example.hive.utils.event.TaskAcceptedEvent;
import com.example.hive.utils.event.listeners.TaskCreatedEvent;
import com.example.hive.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final WalletRepository walletRepository;
    private final EscrowWalletRepository escrowWalletRepository;
    private final PaymentLogRepository paymentLogRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final WalletService walletService;
    private final ApplicationEventPublisher eventPublisher;



    @Override
    public AppResponse<TaskResponseDto> createTask(TaskDto taskDto, User user, HttpServletRequest request) {
//         Check if the user has the TASKER role

        if (!user.getRole().equals(Role.TASKER)) {
            throw new RuntimeException("User is not a TASKER");
        }

//       validate taskRequest , if yes create an escrow wallet
        Wallet wallet = walletRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        checkIfWalletBalanceisEnough(taskDto, wallet);

        EscrowWallet escrowWallet = createAndTransferFundsToEscrowWallet(taskDto.getBudgetRate(),wallet);

        Task task = Task.builder()
                .jobType(taskDto.getJobType())
                .taskDescription(taskDto.getTaskDescription())
                .taskAddress(taskDto.getTaskAddress())
                .taskDeliveryAddress(taskDto.getTaskDeliveryAddress())
                .taskDuration(LocalDateTime.parse(taskDto.getTaskDuration()))
                .budgetRate(taskDto.getBudgetRate())
                .estimatedTime(taskDto.getEstimatedTime())
                .tasker(user)
                .isEscrowTransferComplete(false)
                .escrowWallet(escrowWallet)
                .status(Status.NEW)
                .build();

        Task savedTask = taskRepository.save(task);

        escrowWallet.setTask(savedTask);
        escrowWalletRepository.save(escrowWallet);
        eventPublisher.publishEvent(new TaskCreatedEvent(user, savedTask, applicationUrl(request)));

        return AppResponse.buildSuccess(mapToDto(savedTask));
    }


    @Override
    public AppResponse<TaskResponseDto> updateTask(UUID taskId, TaskDto taskDto, Principal principal) {
        // TODO This method is meant to update doer and Status? or rather insensitive details?
        // Check if the user has the DOER role
        String emailOfDoer = principal.getName();

        log.info("about updating task for: " + emailOfDoer);
        User doer = userRepository.findByEmail(emailOfDoer)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!doer.getRole().equals(Role.DOER)) {
            throw new RuntimeException("User is not a DOER");
        }

        // Find and update the task
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Update the status of the task
        task.setDoer(doer);

        Task updatedTask = taskRepository.save(task);

        return  AppResponse.buildSuccess(mapToDto(updatedTask));
    }

    @Override
    public List<TaskResponseDto> findAll(int pageNo,int pageSize,String sortBy,String sortDir) {
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponseDto> taskList =  new ArrayList<>();
        for (Task task: tasks){
            taskList.add(mapToDto(task));
        }

        return taskList;
    }

    @Override
    public TaskResponseDto findTaskById(UUID taskId) {
       Optional<Task> task = taskRepository.findById(taskId);
       if (task.isPresent()){
           Task task1 = task.get();
           return mapToDto(task1);
       }

        return null;
    }

    @Override
    public List<TaskResponseDto> getUserCompletedTasks(User currentUser) {
        log.info("fetching doer with id {} completed task ", currentUser.getUser_id());

        List<Task> doerTasks = taskRepository.findCompletedTasksByDoer(currentUser);
        return doerTasks.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).collect(Collectors.toList());
    }


    @Override
    public List<TaskResponseDto> getUserOngoingTasks(User currentUser) {

        log.info("fetching doer with id {} ongoing task task ", currentUser.getUser_id());
        List<Task> doerTasks = taskRepository.findOngoingTasksByDoer(currentUser);
        return doerTasks.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).collect(Collectors.toList());

    }

    public TaskResponseDto mapToDto(Task task) {

        return TaskResponseDto.builder()
                .jobType(task.getJobType())
                .taskDescription(task.getTaskDescription())
                .taskAddress(task.getTaskAddress())
                .taskDeliveryAddress(task.getTaskDeliveryAddress())
                .taskDuration(task.getTaskDuration().toString())
                .budgetRate(task.getBudgetRate())
                .taskId(task.getTask_id().toString())
                .estimatedTime(task.getEstimatedTime())
                .status(task.getStatus())
                .build();
    }


    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @Override
    public List<TaskResponseDto> searchTasksBy(String text, int pageNo,int pageSize,String sortBy,String sortDir) {
       Optional<List<Task>> tasksList = taskRepository.searchTasksBy(text);
        List<TaskResponseDto> listOfTasks = new ArrayList<>();

        if(tasksList.isPresent()) {
            for (Task task : tasksList.get()) {
                listOfTasks.add(mapToDto(task));
            }
        } else {
            throw new ResourceNotFoundException("Task not found");
        }

        return listOfTasks;

    }

    @Override
    public TaskResponseDto acceptTask(User user, String taskId) {
        Task tasKToUpdate = taskRepository.findById(UUID.fromString(taskId)).orElseThrow(() -> new ResourceNotFoundException("task can not be found"));
        if (!user.getRole().equals(Role.DOER)) throw new BadRequestException("User is not a doer");

        if (isTaskAccepted(tasKToUpdate)) {
            tasKToUpdate.setDoer(user);
            tasKToUpdate.setStatus(Status.ONGOING);
            Task updatedTask = taskRepository.save(tasKToUpdate);
            eventPublisher.publishEvent(new TaskAcceptedEvent(user, updatedTask));
            return modelMapper.map(updatedTask, TaskResponseDto.class);
        }

        throw new CustomException("Task not available", HttpStatus.BAD_REQUEST);
    }

    @Override
    public TaskResponseDto doerCompletesTask(User doer, String taskId) {
        Task tasKToUpdate = taskRepository.findById(UUID.fromString(taskId)).orElseThrow(() -> new ResourceNotFoundException("task can not be found"));

        //check if doer is the same as the doer associated with the task
        if (isTaskOngoing(tasKToUpdate) && isDoerTheSameAsInTheTask(tasKToUpdate,doer)) {
            tasKToUpdate.setStatus(Status.PENDING_APPROVAL);
            Task updatedTask = taskRepository.save(tasKToUpdate);
            return modelMapper.map(updatedTask, TaskResponseDto.class);
        }
        throw new BadRequestException("Something Went wrong");
    }

    @Override
    public TaskResponseDto taskerApprovesCompletedTask(User tasker, String taskId) {
        Task tasKToUpdate = taskRepository.findById(UUID.fromString(taskId)).orElseThrow(() -> new ResourceNotFoundException("task can not be found"));

        //check if tasker is the same as the tasker associated with the task
        if (isTaskPendingApproval(tasKToUpdate) && isTaskerTheOwnerOfTask(tasKToUpdate,tasker)) {
            tasKToUpdate.setStatus(Status.COMPLETED);

            //transfer funds to the doer
            EscrowWallet escrowWallet = tasKToUpdate.getEscrowWallet();

            User doer = tasKToUpdate.getDoer();

            creditTheDoerWalletFromEscrowWallet(escrowWallet,doer,tasKToUpdate);

          //  deleteEscrowWallet and update task
            tasKToUpdate.setIsEscrowTransferComplete(true);

            Task updatedTask = taskRepository.save(tasKToUpdate);
            return modelMapper.map(updatedTask, TaskResponseDto.class);
        }
        throw new BadRequestException("Something Went wrong");
    }

    @Override
    public List<TaskResponseDto> getTasksByUserRoleAndStatus(User currentUser, String status) {

        List<Task> tasks;
        if (currentUser.getRole().equals(Role.DOER)) {
            tasks = taskRepository.findAllByDoerAndStatus(currentUser, Status.valueOf(status.toUpperCase()));
        } else{
            tasks = taskRepository.findAllByTaskerAndStatus(currentUser, Status.valueOf(status.toUpperCase()));
        }
        return tasks.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public boolean cancelNewTaskByTasker(User currentUser, String taskId) {
        Task taskToCancel = taskRepository.findById(UUID.fromString(taskId)).orElseThrow(() -> new ResourceNotFoundException("task can not be found"));

        if (taskToCancel.getStatus().equals(Status.CANCELLED)) throw new BadRequestException("Task is already cancelled");
        if (!taskToCancel.getStatus().equals(Status.NEW)) throw new BadRequestException("Task is not new");
        if ((taskToCancel.getStatus().equals(Status.NEW)) && isTaskerTheOwnerOfTask(taskToCancel,currentUser)) {
            walletService.refundTaskerFromEscrowWallet(taskToCancel);
            refundTasker(taskToCancel);
            taskToCancel.setStatus(Status.CANCELLED);
            taskRepository.save(taskToCancel);
            return true;
        }
        return false;
    }


    private void refundTasker(Task task) {
        User tasker = task.getTasker();
        EscrowWallet escrowWallet = task.getEscrowWallet();
        walletService.fundTaskerWallet(tasker, escrowWallet.getEscrowAmount());
        escrowWallet.setEscrowAmount(new BigDecimal(0));
        escrowWalletRepository.save(escrowWallet);

    }

    private void creditTheDoerWalletFromEscrowWallet(EscrowWallet escrowWallet, User doer, Task task) {

        //check if the task has been paid for

        if (task.getIsEscrowTransferComplete()){throw new BadRequestException("The task has been paid for ");}

        walletService.creditDoerWallet(doer, escrowWallet.getEscrowAmount());

    }

    private boolean isTaskPendingApproval(Task tasK) {
        return tasK.getStatus().equals(Status.PENDING_APPROVAL);
    }

    private boolean isTaskerTheOwnerOfTask(Task task, User tasker) {
        return task.getTasker().equals(tasker);
    }

    private boolean isDoerTheSameAsInTheTask(Task task, User doer) {
        return task.getDoer().equals(doer);
    }

    private boolean isTaskAccepted(Task task) {
        return task.getStatus().equals(Status.NEW);
    }

    private boolean isTaskOngoing(Task task) {
        return task.getStatus().equals(Status.ONGOING);
    }

    private EscrowWallet createAndTransferFundsToEscrowWallet(BigDecimal amount, Wallet wallet) {

        if (walletService.debitTaskerWalletToEscrow(wallet, amount)) {

            EscrowWallet escrowWallet = new EscrowWallet();

            escrowWallet.setEscrowAmount(amount);

            escrowWalletRepository.save(escrowWallet);

            return escrowWallet;

        } else {
            throw new BadRequestException("Insufficient funds");
        }

    }

    private static void checkIfWalletBalanceisEnough(TaskDto taskDto, Wallet wallet) {

        if (taskDto.getBudgetRate().compareTo(wallet.getAccountBalance()) > 0){
            throw new BadRequestException("Insufficient funds");
        }
    }
}


