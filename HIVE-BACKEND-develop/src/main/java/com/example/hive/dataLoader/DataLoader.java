//package com.example.hive.dataLoader;
//
//import com.example.hive.entity.Address;
//import com.example.hive.entity.Task;
//import com.example.hive.entity.User;
//import com.example.hive.enums.Role;
//import com.example.hive.enums.Status;
//import com.example.hive.repository.AddressRepository;
//import com.example.hive.repository.TaskRepository;
//import com.example.hive.repository.UserRepository;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Component
//public class DataLoader {
//    private UserRepository userRepository;
//    private AddressRepository addressRepository;
//    private TaskRepository taskRepository;
//
//    public DataLoader(UserRepository userRepository, AddressRepository addressRepository, TaskRepository taskRepository) {
//        this.userRepository = userRepository;
//        this.addressRepository = addressRepository;
//        this.taskRepository = taskRepository;
//    }
//    @PostConstruct
//
//    public void loadUserData() {
//        // Create an address for the user and task
//        Address address1 = new Address();
//        address1.setNumber(1);
//        address1.setStreet("Lagos Street");
//        address1.setCity("Lagos");
//        address1.setState("Lagos");
//        address1.setCountry("Nigeria");
//
//        // second address
//        Address address2 = new Address();
//        address2.setNumber(2);
//        address2.setStreet("4 Lagos Street");
//        address2.setCity("Calabar");
//        address2.setState("Cross River");
//        address2.setCountry("Nigeria");
//
//        // third address
//        Address address3 = new Address();
//        address3.setNumber(3);
//        address3.setStreet("Maitama Street");
//        address3.setCity("Abuja");
//        address3.setState("Abuja");
//        address3.setCountry("Nigeria");
//
//        Address address4 = new Address();
//        address4.setNumber(4);
//        address4.setStreet("Paolo Street");
//        address4.setCity("Port-Harcourt");
//        address4.setState("Rivers");
//        address4.setCountry("Nigeria");
//
//        Address address5 = new Address();
//        address5.setNumber(5);
//        address5.setStreet("Citadel Road");
//        address5.setCity("Lagos");
//        address5.setState("Lagos");
//        address5.setCountry("Nigeria");
//
//        Address address6 = new Address();
//        address6.setNumber(6);
//        address6.setStreet("United Estate");
//        address6.setCity("Lagos");
//        address6.setState("Lagos");
//        address6.setCountry("Nigeria");
//
//
//        List<Address> addresses = List.of(address1, address2, address3, address4, address5, address6);
//
//        for (Address address : addresses) {
//            Optional<Address> existingAddress = addressRepository.findByNumberAndStreetAndCityAndStateAndCountry(
//                    address.getNumber(),
//                    address.getStreet(),
//                    address.getCity(),
//                    address.getState(),
//                    address.getCountry()
//            );
//            if (!existingAddress.isPresent()) {
//                addressRepository.save(address);
//            }
//        }
//
//
//        // Create a user
//        User user = new User();
//        user.setUser_id(UUID.randomUUID());
//        user.setFullName("Daniel Ijedibia");
//        user.setEmail("daniel@gmail.com");
//        user.setPhoneNumber("00000000000");
//        user.setValidId(String.valueOf(1));
//        user.setPassword("hashedPassword");
//        user.setIsVerified(true);
//        user.setRole(Role.TASKER);
//        user.setAddress(address1);
//
//        // second user
//        User user2 = new User();
//        user2.setUser_id(UUID.randomUUID());
//        user2.setFullName("Daniel");
//        user2.setEmail("dan@gmail.com");
//        user2.setPhoneNumber("000000000");
//        user2.setValidId(String.valueOf(2));
//        user2.setPassword("hashedPassword");
//        user2.setIsVerified(true);
//        user2.setRole(Role.TASKER);
//        user2.setAddress(address2);
//
//
//        // third user
//        User user3 = new User();
//        user3.setUser_id(UUID.randomUUID());
//        user3.setFullName("Don Pablo");
//        user3.setEmail("dp@indian.com");
//        user3.setPhoneNumber("000000");
//        user3.setValidId(String.valueOf(3));
//        user3.setPassword("hashedPassword");
//        user3.setIsVerified(true);
//        user3.setRole(Role.TASKER);
//        user3.setAddress(address3);
//
//
//        User user4 = new User();
//        user4.setUser_id(UUID.randomUUID());
//        user4.setFullName("Christian Fo0bah");
//        user4.setEmail("foobar@gmail.com");
//        user4.setPhoneNumber("7382834");
//        user4.setValidId(String.valueOf(4));
//        user4.setPassword("0000");
//        user4.setIsVerified(true);
//        user4.setRole(Role.DOER);
//        user4.setAddress(address4);
//
//        User user5 = new User();
//        user5.setUser_id(UUID.randomUUID());
//        user5.setFullName("Peter Desmond");
//        user5.setEmail("peter@gmail.com");
//        user5.setPhoneNumber("00002340000");
//        user5.setValidId(String.valueOf(5));
//        user5.setPassword("hashedPassword");
//        user5.setIsVerified(true);
//        user5.setRole(Role.DOER);
//        user5.setAddress(address5);
//
//
//        User user6 = new User();
//        user6.setUser_id(UUID.randomUUID());
//        user6.setFullName("Desmond");
//        user6.setEmail("desd@gmail.com");
//        user6.setPhoneNumber("4502340000");
//        user6.setValidId(String.valueOf(6));
//        user6.setPassword("hashedDesd");
//        user6.setIsVerified(true);
//        user6.setRole(Role.DOER);
//        user6.setAddress(address6);
//
//
//        List<User> users = List.of(user,user2,user3,user4,user5,user6);
//
//        for (User u: users){
//            if (userRepository.findByEmail(u.getEmail()).isEmpty()) {
////                userRepository.save(u);
//            }
//        }
//
//        // Create a task
//        Task task1 = new Task();
//        task1.setJobType("Painting");
//        task1.setTaskDescription("Paint the mansion");
//        task1.setBudgetRate(new BigDecimal("10000"));
//        task1.setTaskAddress("123 Obafemi Awolowo Street, India");
//        task1.setTaskDeliveryAddress("123 Obafemi Awolowo Street, India");
//        task1.setEstimatedTime(5);
//        task1.setTaskDuration(LocalDateTime.now());
//        task1.setStatus(Status.NEW);
//
//        // Second task
//        Task task2 = new Task();
//        task2.setJobType("Logistics");
//        task2.setTaskDescription("Get me food from The Place");
//        task2.setBudgetRate(new BigDecimal("5000"));
//        task2.setTaskAddress("The Place Restaurant");
//        task2.setTaskDeliveryAddress("7 Asajon Way, Sangotedo");
//        task2.setEstimatedTime(1);
//        task2.setTaskDuration(LocalDateTime.now());
//        task2.setStatus(Status.COMPLETED);
//
//        // Third task
//        Task task3 = new Task();
//        task3.setJobType("Logistics");
//        task3.setTaskDescription("Delivery drug to Escobar");
//        task3.setBudgetRate(new BigDecimal("5000000"));
//        task3.setTaskAddress("Nigeria");
//        task3.setTaskDeliveryAddress("Mexico");
//        task3.setEstimatedTime(72);
//        task3.setTaskDuration(LocalDateTime.now());
//        task3.setStatus(Status.ONGOING);
//
//        Task task4 = new Task();
//        task4.setJobType("Logistics");
//        task4.setTaskDescription("Deliver package to my company");
//        task4.setBudgetRate(new BigDecimal("5000"));
//        task4.setTaskAddress("Sangotedo");
//        task4.setTaskDeliveryAddress("Sangotedo");
//        task4.setEstimatedTime(2);
//        task4.setTaskDuration(LocalDateTime.now());
//        task4.setStatus(Status.ONGOING);
//
//        Task task5 = new Task();
//        task5.setJobType("Gardening");
//        task5.setTaskDescription("Tidy my garden");
//        task5.setBudgetRate(new BigDecimal("6000"));
//        task5.setTaskAddress("No 2 Lekki Phase 1 ");
//        task5.setTaskDeliveryAddress("MexicNo 2 Lekki Phase 1 o");
//        task5.setEstimatedTime(3);
//        task5.setTaskDuration(LocalDateTime.now());
//        task5.setStatus(Status.NEW);
//
//        Task task6 = new Task();
//        task6.setJobType("Logistics");
//        task6.setTaskDescription("Delivery flour to bakery");
//        task6.setBudgetRate(new BigDecimal("8000"));
//        task6.setTaskAddress("Epe, Lagos");
//        task6.setTaskDeliveryAddress("Victoria Island, Lagos");
//        task6.setEstimatedTime(2);
//        task6.setTaskDuration(LocalDateTime.now());
//        task6.setStatus(Status.NEW);
//
//        Task task7 = new Task();
//        task7.setJobType("Pick-up");
//        task7.setTaskDescription("Pickup 50 bags of cement from Dangote");
//        task7.setBudgetRate(new BigDecimal("10000"));
//        task7.setTaskAddress("17 Lekki GRA, Lagos Island");
//        task7.setTaskDeliveryAddress("7 Asajon Way, Sangotedo");
//        task7.setEstimatedTime(3);
//        task7.setTaskDuration(LocalDateTime.now());
//        task7.setStatus(Status.EXPIRED);
//
//        Task task8 = new Task();
//        task8.setJobType("Market");
//        task8.setTaskDescription("Pick up groceries from Jumia foods");
//        task8.setBudgetRate(new BigDecimal("6000"));
//        task8.setTaskAddress("Jumia Foods, Chevron Way, Lekki Island");
//        task8.setTaskDeliveryAddress("9 Peace Estate, Ajah.");
//        task8.setEstimatedTime(2);
//        task8.setTaskDuration(LocalDateTime.now());
//        task8.setStatus(Status.NEW);
//
//
//        User tasker1 = userRepository.findByEmail(user.getEmail()).orElse(null);
//        User doer = userRepository.findByEmail(user6.getEmail()).orElse(null);
//        task1.setTasker(tasker1);
//        task1.setDoer(doer);
//
//        User tasker2 = userRepository.findByEmail(user3.getEmail()).orElse(null);
//        User doer2 = userRepository.findByEmail(user5.getEmail()).orElse(null);
//        task3.setTasker(tasker2);
//        task3.setDoer(doer2);
//
//        User tasker3 = userRepository.findByEmail(user2.getEmail()).orElse(null);
//        User doer3 = userRepository.findByEmail(user4.getEmail()).orElse(null);
//        task4.setTasker(tasker3);
//        task4.setDoer(doer3);
//
//        List<Task> tasks = List.of(task1, task2, task3, task4, task5, task6, task7, task8);
//
//        for (Task task : tasks) {
//            Optional<Task> existingTask = taskRepository.findByJobTypeAndTaskDescriptionAndBudgetRateAndTaskAddressAndTaskDeliveryAddressAndEstimatedTimeAndStatus(
//                    task.getJobType(),
//                    task.getTaskDescription(),
//                    task.getBudgetRate(),
//                    task.getTaskAddress(),
//                    task.getTaskDeliveryAddress(),
//                    task.getEstimatedTime(),
//                    task.getStatus()
//            );
//            if (!existingTask.isPresent()) {
//                taskRepository.save(task);
//            }
//
//        }
//
//    }
//}
