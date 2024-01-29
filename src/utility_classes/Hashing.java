/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility_classes;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 *
 * @author dumid
 */
public class Hashing {

    public static String generateRandomPassword(int length) throws NoSuchAlgorithmException {

        //generate password from available characters below
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$&-_+";

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex)); //add a random character from characters to password
        }

        System.out.println("password " + password);

        return password.toString();
    }

    public static String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);//generate random salt

        //System.out.println("salt "+ Arrays.toString(salt));
        System.out.println("salt in Hex " + bytesToHex(salt));
        //System.out.println("salt in Bytes " + Arrays.toString(hexToBytes(bytesToHex(salt)))); //should be same as byte

        return bytesToHex(salt);//convert byte to string
    }

    //for existent users
    public static String SaltPassword(String password, String salt) throws NoSuchAlgorithmException {

        byte[] ByteSalt = hexToBytes(salt); //convert salt from db to byte
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(ByteSalt); //will have to send salt to database

        byte[] SaltedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        System.out.println("salted password " + bytesToHex(SaltedPassword));
        return bytesToHex(SaltedPassword);
    }

    public static String PepperPassword(String SaltedPassword) throws NoSuchAlgorithmException {
        String pepper = "nejgnaeojfiergsefjgonanlgreghijbcjasnMDPKGJEI";

        String PepperedPassword = SaltedPassword + pepper; //add pepper to salted password
        System.out.println("peppered " + PepperedPassword);
        return PepperedPassword; //will be stored in DB
    }

    //salt first, then pepper, then store salt and total salted and peppered password
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b));
        }
        return hexStringBuilder.toString();
    }

    public static byte[] hexToBytes(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid hex string");
        }

        int length = hexString.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }
}
