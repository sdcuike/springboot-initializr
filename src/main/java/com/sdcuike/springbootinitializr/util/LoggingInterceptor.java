package com.sdcuike.springbootinitializr.util;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author sdcuike
 * @date 2018/10/24
 * @since 2018/10/24
 */
class LoggingInterceptor implements Interceptor {
    private static final String PRIVATE_TOKEN = "PRIVATE-TOKEN";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder().header(PRIVATE_TOKEN, GitLabApi.privateToken).build();
        return chain.proceed(request);
    }
}
