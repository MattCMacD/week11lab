/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.naming.NamingException;

/**
 *
 * @author awarsyle
 */
public class AccountService {
    
    public boolean forgotPassword(String email, String path) {
        UserDB userDB = new UserDB();
        User user = userDB.getUserFromEmail(email);
        if (user != null) {
            
            
            
            try {
                    // WebMailService.sendMail(email, "NotesKeepr Login", "Big brother is watching you!  Hi " + user.getFirstname(), false);
                    
                    HashMap<String, String> contents = new HashMap<>();
                    contents.put("firstname", user.getFirstname());
                    contents.put("lastname", user.getLastname());
                    
                    HashMap<String,String> contents2 = new HashMap<>();
                    contents.put("username", user.getUsername());
                    contents.put("password", user.getPassword());
                    
                    try {
                        WebMailService.sendMail(email, "NotesKeepr Login", path + "/emailtemplates/forgotPass.html", contents, contents2);
                    } catch (IOException ex) {
                        Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                } catch (MessagingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NamingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
            
            return true;
        }
        else return false;
    }
    
    public User checkLogin(String username, String password, String path) {
        User user;
        
        UserDB userDB = new UserDB();
        try {
            user = userDB.getUser(username);
            
            if (user.getPassword().equals(password)) {
                // successful login
                Logger.getLogger(AccountService.class.getName())
                        .log(Level.INFO,
                                "A user logged in: {0}", username);
                String email = user.getEmail();
                try {
                    // WebMailService.sendMail(email, "NotesKeepr Login", "Big brother is watching you!  Hi " + user.getFirstname(), false);
                    
                    HashMap<String, String> contents = new HashMap<>();
                    contents.put("firstname", user.getFirstname());
                    contents.put("date", ((new java.util.Date()).toString()));
                    
                    try {
                        WebMailService.sendMail(email, "NotesKeepr Login", path + "/emailtemplates/login.html", contents);
                    } catch (IOException ex) {
                        Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                } catch (MessagingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NamingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return user;
            }
            
        } catch (NotesDBException ex) {
        }
        
        return null;
    }
    
}
