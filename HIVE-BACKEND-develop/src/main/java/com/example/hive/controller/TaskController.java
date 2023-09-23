package com.example.hive.controller;

import com.example.hive.constant.AppConstants;
import com.example.hive.constant.ResponseStatus;
import com.example.hive.dto.request.TaskDto;
import com.example.hive.dto.response.AppResponse;
import com.example.hive.dto.response.TaskResponseDto;
import com.example.hive.entity.User;
import com.example.hive.enums.Status;
import com.example.hive.exceptions.ResourceNotFoundException;
import com.example.hive.repository.UserRepository;
import com.example.hive.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<AppResponse<TaskResponseDto>> createTask(@Valid @RequestBody TaskDto taskDto, Principal principal,HttpServletRequest request) {
        String email = principal.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        AppResponse<TaskResponseDto> createdTask = taskService.createTask(taskDto, currentUser, request);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public AppResponse<TaskResponseDto> updateTask(
            @PathVariable UUID taskId,
            @RequestBody TaskDto taskDto,
            Principal principal) {
        return taskService.updateTask(taskId, taskDto,principal);

    }

    @GetMapping(path = "task/details/{taskId}")
    public ResponseEntity<AppResponse<TaskResponseDto>> findTaskById(@PathVariable UUID taskId) {
        TaskResponseDto taskFound = taskService.findTaskById(taskId);

        // creates an ApiResponse object with the retrieved task data
        AppResponse<TaskResponseDto> apiResponse = new AppResponse<>();
        apiResponse.setResult(taskFound);
        apiResponse.setStatusCode(HttpStatus.FOUND.toString()); // a status code indicating success
        apiResponse.setMessage("Task fetched successfully"); // a message describing the response

        // returns an HTTP response with a JSON response containing the ApiResponse object
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("task/list")
    public ResponseEntity<AppResponse<Object>> findAllTasks(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        var tasksFound = taskService.findAll(pageNo, pageSize, sortBy, sortDir);

        return ResponseEntity.status(200).body(AppResponse.builder().statusCode("00").isSuccessful(true).result(tasksFound).build());
    }

    //fetch completed task(filtered by login doer)
    @GetMapping("/user/completed_task")
    public ResponseEntity<AppResponse<Object>> getUserCompletedTasks(Principal principal) {
        try {
            String email = principal.getName();
            User currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            List<TaskResponseDto> doerCompletedTasks = taskService.getUserCompletedTasks(currentUser);
            AppResponse<Object> appResponse = AppResponse.builder()
                    .statusCode(ResponseStatus.SUCCESSFUL.getCode())
                    .result(doerCompletedTasks)
                    .message(ResponseStatus.SUCCESSFUL.getMessage())
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(appResponse, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new AppResponse<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }


    //fetch ongoing task(filtered by login doer)

    @GetMapping("/user/ongoing_task")
    public ResponseEntity<AppResponse<Object>> getUserOngoingTasks(Principal principal) {
        String email = principal.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<TaskResponseDto> doerOngoingTasks = taskService.getUserOngoingTasks(currentUser);
        AppResponse<Object> appResponse = AppResponse.builder()
                .statusCode(ResponseStatus.SUCCESSFUL.getCode())
                .result(doerOngoingTasks)
                .message(ResponseStatus.SUCCESSFUL.getMessage())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(appResponse, HttpStatus.OK);

    }

    @PostMapping("/{taskId}/accept")
    public ResponseEntity<String> acceptTask(@PathVariable("taskId") String taskId, Principal principal) {
        try {
            String email = principal.getName();
            User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found"));
            taskService.acceptTask(currentUser, taskId);
            return new ResponseEntity<>("Task accepted", HttpStatus.OK);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Task not available", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/{taskId}/complete") //doer
    public ResponseEntity<String> doerCompletesTask  (@PathVariable("taskId") String taskId, Principal principal) {
        try {
            String email = principal.getName();
            User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found"));
            taskService.doerCompletesTask(currentUser, taskId);
            return new ResponseEntity<>("Task ready for approval", HttpStatus.OK);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Task not available", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/{taskId}/approve")//tasker
    public ResponseEntity<String> taskerApprovesCompletedTask  (@PathVariable("taskId") String taskId, Principal principal) {
        try {
            String email = principal.getName();
            User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found"));
            taskService.taskerApprovesCompletedTask(currentUser, taskId);
            return new ResponseEntity<>("Task completed", HttpStatus.OK);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Task not available", HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDto>> searchTasks(
            @RequestParam(value = "text") String text,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return ResponseEntity.ok(taskService.searchTasksBy(text, pageNo, pageSize, sortBy, sortDir));
    }


    @GetMapping("/new_task")
    public ResponseEntity<AppResponse<Object>> getAllNewTasks(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        var tasksFound = taskService.findAll(pageNo, pageSize, sortBy, sortDir);

        var newTasks = tasksFound.stream()
                .filter(task -> task.getStatus().equals(Status.NEW)).toList();

        return ResponseEntity.status(200).body(AppResponse.builder().statusCode("00").isSuccessful(true).result(newTasks).build());
    }


    @GetMapping("/fetchTasks/")
    public ResponseEntity<AppResponse<Object>> getAllTasksByUserRoleAndStatus(Principal principal, @RequestParam(value = "status") String status) {
        try {
            String email = principal.getName();
            User currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            List<TaskResponseDto> tasks = taskService.getTasksByUserRoleAndStatus(currentUser,status);
            AppResponse<Object> appResponse = AppResponse.builder()
                    .statusCode(ResponseStatus.SUCCESSFUL.getCode())
                    .result(tasks)
                    .message(ResponseStatus.SUCCESSFUL.getMessage())
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(appResponse, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new AppResponse<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @PostMapping("/{taskId}/cancel")
    public ResponseEntity<Object> cancelNewTaskByTasker(@PathVariable("taskId") String taskId, Principal principal) {

        String email = principal.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (taskService.cancelNewTaskByTasker(currentUser,taskId)) {
            return ResponseEntity.status(200).body(AppResponse.builder().statusCode("00").isSuccessful(true).result("Task cancelled").build());
        }
        return ResponseEntity.status(200).body(AppResponse.builder().statusCode("00").isSuccessful(false).result("Task not cancelled").build());

    }


}

