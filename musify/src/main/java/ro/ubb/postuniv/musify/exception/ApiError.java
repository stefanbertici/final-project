package ro.ubb.postuniv.musify.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ApiError {
    private LocalDateTime timeStamp;
    private HttpStatus status;
    private List<String> errorMessages;

    public ApiError() {
    }

    public ApiError(HttpStatus status, String errorMessage) {
        this.timeStamp = LocalDateTime.now();
        this.status = status;

        if (this.errorMessages == null) {
            this.errorMessages = new ArrayList<>();
        }

        this.errorMessages.add(errorMessage);
    }

    public ApiError(HttpStatus status, List<String> errorMessages) {
        this.timeStamp = LocalDateTime.now();
        this.status = status;
        this.errorMessages = errorMessages;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}


