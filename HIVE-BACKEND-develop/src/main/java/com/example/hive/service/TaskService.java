package com.example.hive.service;

import com.example.hive.dto.request.TaskDto;
import com.example.hive.dto.response.AppResponse;
import com.example.hive.dto.response.TaskResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import com.example.hive.entity.User;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface TaskService {

public AppResponse<TaskResponseDto> createTask(TaskDto taskDto, User user, HttpServletRequest request);
    public AppResponse<TaskResponseDto> updateTask(UUID taskId, TaskDto taskDto, Principal principal);


    List<TaskResponseDto> findAll(int pageNo,int pageSize,String sortBy,String sortDir);

    TaskResponseDto findTaskById(UUID TaskId);

//    List<TaskResponseDto> searchTasksBy(String text, int pageNo,int pageSize,String sortBy,String sortDir);

    String applicationUrl(HttpServletRequest request);


    List<TaskResponseDto> searchTasksBy(String text, int pageNo,int pageSize,String sortBy,String sortDir);

    List<TaskResponseDto> getUserCompletedTasks(User currentUser);

    List<TaskResponseDto> getUserOngoingTasks(User currentUser);
    // doer accepted task
    TaskResponseDto acceptTask(User user, String taskId);

    TaskResponseDto doerCompletesTask(User currentUser, String taskId);

    TaskResponseDto taskerApprovesCompletedTask(User currentUser, String taskId);

    List<TaskResponseDto> getTasksByUserRoleAndStatus(User currentUser, String status);

    boolean cancelNewTaskByTasker(User currentUser, String taskId);
}
