package org.entelect.polling;

import io.netty.handler.timeout.ReadTimeoutException;
import org.entelect.dto.PollingTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

public class WebClientPolling implements IPollingClient{

    private Logger logger = LoggerFactory.getLogger(RestTemplatePolling.class);

    private WebClient webClient;

    private int spinning = 10;//seconds

    public WebClientPolling(WebClient webClient) {
        this.webClient = webClient;
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public int createTask(PollingTask task) {
        Mono<Integer> returnedTaskId = webClient.post().uri(BASE_URL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(task), PollingTask.class)
                .retrieve()
                .bodyToMono(Integer.class);
        int result = Objects.requireNonNull(returnedTaskId.block());
        logger.info("Create Task - " + result);
        return result;
    }

    @Override
    public String getTask(int taskId) {
        try {
            String result = webClient.get()
                    .uri(BASE_URL + taskId)
                    .retrieve()
                    .bodyToFlux(String.class)
                    .timeout(Duration.ofSeconds(spinning))
                    .blockFirst();
            System.out.println("WebClient response: " + result);
            logger.info(result);
            return result;
        } catch (ReadTimeoutException e) {
            e.printStackTrace();
            System.out.println("Web Client Error: " + e.getMessage());
            logger.error("Web Client Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void processATask(int taskId) {

    }
}
