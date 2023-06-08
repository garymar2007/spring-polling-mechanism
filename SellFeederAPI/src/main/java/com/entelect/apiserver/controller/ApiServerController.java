package com.entelect.apiserver.controller;

import com.entelect.apiserver.dto.PollingTask;
import com.entelect.apiserver.dto.TaskStatus;
import com.entelect.apiserver.taskprocess.TaskProcessing;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@RestController
@RequestMapping("/sell/feed/v1/")
public class ApiServerController {
    private final static int MIN = 1;
    private final static int MAX = 99999;

    private Logger logger = LoggerFactory.getLogger(ApiServerController.class);

    private static List<PollingTask> taskList = new ArrayList<>();

    @PostMapping("task")
    @ResponseStatus(HttpStatus.CREATED)
    public int createTask(@RequestBody PollingTask task) {
        final int taskId = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
        task.setId(taskId);
        task.setStatus(TaskStatus.NOT_STARTED.status);
        taskList.add(task);
        TaskProcessing thread = new TaskProcessing();
        thread.setTask(task);
        thread.setSpinning(3000);
        thread.run();
        return taskId;
    }

    @GetMapping("task/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public DeferredResult<String> getTaskStatus(@PathVariable int taskId) throws Exception{
        DeferredResult<String> result = new DeferredResult<>();
        try {
            Thread.sleep(5000);
            System.out.println("The size of taskList is " + taskList.size());
            logger.info("The size of taskList is " + taskList.size());
            PollingTask task = taskList.stream().filter((e)-> e.getId() == taskId)
                    .collect(Collectors.toList()).get(0);
            if(task != null) {
                result.setResult("The task " + task.getId() + " is " + task.getStatus());
            } else {
                throw new NotFoundException("The task id provided is not found - " + taskId);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        return result;
    }
}
