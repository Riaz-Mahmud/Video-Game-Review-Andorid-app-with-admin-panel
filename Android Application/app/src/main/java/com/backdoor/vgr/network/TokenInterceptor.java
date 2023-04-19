package com.backdoor.vgr.network;


import android.content.Context;

import com.backdoor.vgr.View.Model.PerfConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private static final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vdmdyLmFsbWFobXVkcmlhei5jb20vYXBpL2xvZ2luIiwiaWF0IjoxNjgxODM0OTc3LCJuYmYiOjE2ODE4MzQ5NzcsImp0aSI6IlliVEtHb0Z4dmxzd0dqTXQiLCJzdWIiOiIxIiwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.FMGQYkAuYIYd_5QiveZ9IBys9GnwQd3-5ktlUx8D9bY";

    public static PerfConfig perfConfig;
    private final Context context;

    public TokenInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        perfConfig = new PerfConfig(this.context);
        Request newRequest=chain.request().newBuilder()
                .header("Authorization",perfConfig.readTokenType()+ " "+ perfConfig.readAccessToken())
                .build();

        return chain.proceed(newRequest);
    }

}
