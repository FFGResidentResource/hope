/**
 * 
 */
package com.ffg.rrn.model;

import lombok.Data;

/**
 * @author mycomputer
 *
 */
@Data
public class AppUser {
	 
    private Long userId;
    private String userName;
    private String encrytedPassword;
 
    public AppUser() {
 
    }
 
    public AppUser(Long userId, String userName, String encrytedPassword) {
        this.userId = userId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
    }
    
    @Override
    public String toString() {
        return this.userName + "/" + this.encrytedPassword;
    }
}
