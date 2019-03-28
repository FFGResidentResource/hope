/**
 * 
 */
package com.ffg.rrn.utils;

import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author mycomputer
 *
 */
public class EncryptedPasswordUtils {
	 
    // Encryte Password with BCryptPasswordEncoder
    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
 
    public static void main(String[] args) {
        String password = createRandomPassword();
        String encrytedPassword = encrytePassword(password);
        System.out.println("Password: " + password);
        System.out.println("Encryted Password: " + encrytedPassword);
        String password1 = createRandomPassword();
        String encrytedPassword1 = encrytePassword(password1);
        System.out.println("Password: " + password1);
        System.out.println("Encryted Password: " + encrytedPassword1);
    }
    
    //I do not this this actually works...
    public static String createRandomPassword() {
    	int len = 10;
    	String values = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*_=+-/.?<>():,;";
    	Random rndm_method = new Random(); 
    	  
        char[] password = new char[len];
        for (int i = 0; i < len; i++) {
        	password[i] = values.charAt(rndm_method.nextInt(values.length())); 
        }
    	return password.toString();
    }
 
}