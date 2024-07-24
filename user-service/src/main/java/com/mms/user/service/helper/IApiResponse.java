package com.mms.user.service.helper;

import java.util.List;

interface IApiResponse<T> {
    String getMessage();
    int getStatus();
    T getData();
    List<Error> getErrors();
}
