package com.example.hive.utils.event;

import com.example.hive.entity.Task;
import com.example.hive.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TaskAcceptedEvent extends ApplicationEvent {

    private final User user;
    private final Task task;

    public TaskAcceptedEvent(User user, Task task) {
        super(user);
        this.user = user;
        this.task =task;
    }

}
