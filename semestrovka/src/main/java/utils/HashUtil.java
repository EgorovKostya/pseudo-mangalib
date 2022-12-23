package utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashUtil {
    @SneakyThrows
    public static String hash(String password) {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(password.getBytes("UTF-8"));
        BigInteger bigInt = new BigInteger(1,digest);
        return bigInt.toString(16);
    }
}
