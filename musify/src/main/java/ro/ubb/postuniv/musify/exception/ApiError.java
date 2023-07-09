package ro.ubb.postuniv.musify.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApiError {

    private LocalDateTime timeStamp;
    private HttpStatus status;
    private List<String> errorMessages;

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

}
