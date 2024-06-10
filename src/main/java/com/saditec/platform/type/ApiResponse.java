package com.saditec.platform.type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {

    private int status;
    private int code;
    private String message;
    private T data;

    public ApiResponse<T> toSuccess(T data) {
        this.code = 200;
        this.message = "OK";
        this.status = HttpStatus.OK.value();
        this.data = data;
        return this;
    }
}
