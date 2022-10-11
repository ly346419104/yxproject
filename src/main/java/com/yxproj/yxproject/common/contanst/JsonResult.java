package com.yxproj.yxproject.common.contanst;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Setter
@Getter
public class JsonResult<T> {
    private Integer StatusCode;
    private T Data;
    private String Msg;

    public JsonResult() {

    }

    public JsonResult(HttpStatus code, String message) {
        this(code, message, null);
    }

    public JsonResult(HttpStatus code, String message, T data) {
        super();
        this.StatusCode = code.value();
        this.Msg = message;
        this.Data = data;
    }

}
