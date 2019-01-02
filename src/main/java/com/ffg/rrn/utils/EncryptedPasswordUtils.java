/**
 * 
 */
package com.ffg.rrn.utils;

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
        String password = "123";
        String encrytedPassword = encrytePassword(password);
 
        System.out.println("Encryted Password: " + encrytedPassword);
    }
 
}