package com.entelect.apiserver.taskprocess;

import com.entelect.apiserver.dto.PollingTask;
import com.entelect.apiserver.dto.TaskStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@RequiredArgsConstructor
public class TaskProcessing extends Thread {
    private Logger logger = LoggerFactory.getLogger(TaskProcessing.class);

    private PollingTask task;

    private int spinning;

    @Override
    public void run() {
        task.setStatus(TaskStatus.STARTED.status);
        logger.info("Task " + task.getId() + " is " + task.getStatus());
        try {
            Thread.sleep(spinning);
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
