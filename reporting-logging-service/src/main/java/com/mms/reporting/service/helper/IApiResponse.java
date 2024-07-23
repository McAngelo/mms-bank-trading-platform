package com.mms.reporting.service.helper;

import java.util.List;

public interface IApiResponse<T> {
    String getMessage();
    void setMessage(String message);
    int getStatus();
    T getData();
    List<ErrorDetails> getErrors();
}
