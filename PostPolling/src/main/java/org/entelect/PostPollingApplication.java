package org.entelect;

import org.entelect.dto.PollingTask;
import org.entelect.polling.IPollingClient;
import org.entelect.polling.WebClientPolling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostPollingApplication {
    private static RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    private static WebClient webClient = WebClient.create();

    private static ExecutorService multiThreads = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws Exception {
        IPollingClient pollingClient = new WebClientPolling(webClient);
        Thread.sleep(1000);
        PollingTask task = new PollingTask();
        task.setSchemaVersion("1.0");
        task.setFeedType("LMS_CREATE_TASK_ACK");
        int taskId = pollingClient.createTask(task);
        pollingClient.getTask(taskId);
    }

    public void pollingInThreads(){
        multiThreads.execute(() -> {

        });
    }
}