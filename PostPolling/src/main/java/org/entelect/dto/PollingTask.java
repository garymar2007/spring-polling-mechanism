package org.entelect.dto;

import lombok.Data;

@Data
public class PollingTask {
    private String schemaVersion;
    private String feedType;
    private String status;
    private int id;
}
