package promotions.utils;

import java.util.Random;

public class Randoms {

    public static String generateRandomString(){
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static Long generateRandomLong(long min, long max){
        Random r = new Random();
        long number = min +((long)(r.nextDouble()*(max-min)));
        return number;
    }
}
