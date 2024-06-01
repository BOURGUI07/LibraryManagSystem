/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;
import domain.User;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author lenovo
 */
public class UserManag {
  private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
  private List<User> list;
  
  public UserManag(){
    this.list=new ArrayList<>();
  }
  
  public void registerUser(String name, String code, String email){
    User user = new User(name, code, email);
    if(this.isEmailValid(email) && this.isSecure(code) && !this.list.contains(user)){
      this.list.add(user);
    }
  }
  
  public void registerUser(User user){
    if(!this.list.contains(user)){
      this.list.add(user);
    }
  }
  
  public boolean isUserRegistrated(User user){
    return this.list.contains(user);
  }
  
  public boolean isEmailValid(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
  }
  
  public  boolean isSecure(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        
        // Define regex patterns
        String digitRegex = ".\\d.";
        String uppercaseRegex = ".[A-Z].";
        String lowercaseRegex = ".[a-z].";
        String specialCharRegex = ".[@#$%^&+=].";
        
        // Check password against each pattern
        if (!Pattern.matches(digitRegex, password)) {
            return false;
        }
        if (!Pattern.matches(uppercaseRegex, password)) {
            return false;
        }
        if (!Pattern.matches(lowercaseRegex, password)) {
            return false;
        }
        if (!Pattern.matches(specialCharRegex, password)) {
            return false;
        }
        
        // Check password length
        if (password.length() < 8) {
            return false;
        }
        
        return true;
    }
  
  public String getPasswordFor(String username){
    for(User user:this.list){
      if(user.getUserName().equals(username)){
        return user.getPassword();
      }
    }
    return null;
  }
  
  public boolean usernameExist(String username){
    for(User user:this.list){
      if(user.getUserName().equals(username)){
        return true;
      }
    }
    return false;
  }
  
  public boolean passwordExist(String code){
    for(User user:this.list){
      if(user.getPassword().equals(code)){
        return true;
      }
    }
    return false;
  }
}
