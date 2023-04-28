package com.backdoor.vgr.Model.Encryption;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Base64;

import com.backdoor.vgr.View.Model.PerfConfig;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypt extends Application {

    private static final String ALGORITHM = "AES";
    public static PerfConfig perfConfig;

    public static String decrypt(String value,Context context) throws Exception {
        Key key = generateKey(context);
        @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue64 = Base64.decode(value, Base64.DEFAULT);
        byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);

        return new String(decryptedByteValue, StandardCharsets.UTF_8);
    }

    private static Key generateKey(Context context) {
        perfConfig = new PerfConfig(context);
        return new SecretKeySpec(perfConfig.readAESKey().getBytes(), AESCrypt.ALGORITHM);
    }


}
