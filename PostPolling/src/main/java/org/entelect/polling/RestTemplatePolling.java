package org.entelect.polling;

import org.entelect.dto.PollingTask;
import org.entelect.dto.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

public class RestTemplatePolling implements IPollingClient{

    private Logger logger = LoggerFactory.getLogger(RestTemplatePolling.class);

    private RestTemplateBuilder restTemplateBuilder;
    private int taskId;

    private int spinning = 3; //seconds

    public RestTemplatePolling(RestTemplateBuilder restTemplateBuilder, int taskId) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.taskId = taskId;
    }

    @Override
    public int createTask(PollingTask task) {
        return 0;
    }

    @Override
    public String getTask(int taskId) {
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(spinning))
                .setReadTimeout(Duration.ofSeconds(spinning))
                .build();
        try{
            return restTemplate.getForObject(BASE_URL + taskId, String.class);
        }catch(ResourceAccessException e){
            e.printStackTrace();
            System.out.println("REST Template Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void processATask(int taskId) {

    }
}
