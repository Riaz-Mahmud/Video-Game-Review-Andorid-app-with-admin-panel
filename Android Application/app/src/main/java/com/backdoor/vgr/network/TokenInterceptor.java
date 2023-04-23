package com.backdoor.vgr.network;


import android.content.Context;

import com.backdoor.vgr.View.Model.PerfConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

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
