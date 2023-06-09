package com.backdoor.vgr.network;

import android.content.Context;
import android.util.Log;

import com.backdoor.vgr.Model.Encryption.AESCrypt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    Context context;

    public static final String BASE_URL = "ctTENhYUp5aaRG6yApJTzecddYNyQzSrt2aF6tT6owBw7s1Lscr57LTpS0tKtF8h";
    public static final String IMAGE_BASE_URL = "https://vgr.almahmudriaz.com";
    private static final String API_VERSION_CODE = "v1/";
    private static Retrofit retrofit = null;

    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {

            TokenInterceptor interceptor = new TokenInterceptor(context);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
            try {
                retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl(AESCrypt.decrypt(BASE_URL, context) + API_VERSION_CODE)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            } catch (Exception ex) {
                Log.d("AESCode", "Error AES: " + ex.getMessage());
            }
        }
        return retrofit;
    }

    public static String getImageUrl(Context context) {
        String imageUrl = "";
        try {
            imageUrl = IMAGE_BASE_URL;
        } catch (Exception ex) {
            Log.d("AESCode", "Error ImageURL AES: " + ex.getMessage());
        }
        return imageUrl;
    }

}
