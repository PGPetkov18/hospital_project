package com.example.boilerplate.services;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

@Service
public class PasswordHasher {
    private byte[] salt=new byte[16];

    public PasswordHasher() {
        SecureRandom random=new SecureRandom();
        random.nextBytes(salt);
    }

    public  String hashPassword(String password){
        try{

            KeySpec spec = new PBEKeySpec(password.toCharArray(),salt,65334,123);
            SecretKeyFactory factory=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            password="";
            for(Byte da:factory.generateSecret(spec).getEncoded()){
                password=password.concat(da.toString());
            }
            return password;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public byte[] getSalt() {
        return salt;
    }
    public void setSalt(byte[] salt){
        this.salt=salt;
    }
}
