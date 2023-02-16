package shop.mtcoding.blog2.ex;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomApiException extends RuntimeException {

    private HttpStatus status;
    private String location;

    public CustomApiException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public CustomApiException(String msg, HttpStatus status, String location) {
        super(msg);
        this.status = status;
        this.location = location;
    }

    public CustomApiException(String msg) {
        this(msg, HttpStatus.BAD_REQUEST);
    }
}
