package org.entelect.dto;

public enum TaskStatus {
    NOT_STARTED("NOT_STARTED"),
    STARTED("STARTED"),
    STILL_BUSY("STILL_BUSY"),
    ALMOST_DONE("ALMOST_DONE"),
    FINISHED("FINISHED");

    public final String status;

    private TaskStatus(String status) {
        this.status = status;
    }
}
