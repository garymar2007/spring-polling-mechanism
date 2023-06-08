package org.entelect.polling;

import org.entelect.dto.PollingTask;
import org.entelect.dto.TaskStatus;

public interface IPollingClient {
    final static String BASE_URL = "http://localhost:8080//sell/feed/v1/task/";

    int createTask(PollingTask task);

    String getTask(int taskId);

    void processATask(int taskId);
}
