package org.tenok.coin.data.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * API Key, Secret Key 복호화 클래스
 */
public class AuthDecryptor {
    private String apiKeyEncrypted;
    private String secretKeyEncrypted;
    private String validationEncrypted;
    private String slackWebhookURLEncrypted;
    private String pw = null;

    private AuthDecryptor(File authFile) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(authFile.getCanonicalFile()));
            this.apiKeyEncrypted = (String) jsonObject.get("apiKey");
            this.secretKeyEncrypted = (String) jsonObject.get("secretKey");
            this.validationEncrypted = (String) jsonObject.get("validation");
            this.slackWebhookURLEncrypted = (String) jsonObject.get("slackWebhookURL");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Secret Key File Path 체크 요망", e);
        }
    }

    private static class AuthHolder {
        public static final AuthDecryptor INSTANCE = new AuthDecryptor(new File("./../secret.auth"));  // 상대주소 입력
    }

    public static AuthDecryptor getInstance() {
        return AuthHolder.INSTANCE;
    }

    public void setPassword(String password) {
        this.pw = password;
    }

    private String getApiKey(String password) {
        return decrypt(this.apiKeyEncrypted, password);
    }

    private String getApiSecretKey(String password) {
        return decrypt(this.secretKeyEncrypted, password);
    }

    private static String decrypt(String cipherText, String password) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes("UTF-8"));
            SecretKeySpec keySpec = new SecretKeySpec(md5.digest(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(md5.digest());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)), "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("복호화 실패");
    }

    /**
     * API Key 리턴
     * @return API Key in String
     */
    public String getApiKey() {
        return getApiKey(pw);
    }

    /**
     * Bybit Signature
     * @return Bybit Signature
     */
    public String generate_signature() {
        return sha256_HMAC("GET/realtime" + String.valueOf(System.currentTimeMillis() + 1000), getApiSecretKey(pw));
    }

    /**
     * 비밀번호 로그인 성공 여부 리턴
     * @return 로그인 성공여부
     */
    public boolean validate() {
        if (this.pw == null) {
            throw new RuntimeException("비밀번호 set 요망");
        }
        return decrypt(validationEncrypted, this.pw).equals("success");
    }

    /**
     * slack webhook 전용 url
     * @return slack webhook url in String
     */
    public String getSlackWebhookURL() {
        return decrypt(this.slackWebhookURLEncrypted, pw);
    }

    private String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    private String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;

    }

}
