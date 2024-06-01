/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.Objects;

/**
 *
 * @author lenovo
 */
public class User {
  private String username;
  private String password;
  private String email;
  
  public User(String name, String code, String email){
    if(name==null){
      throw new IllegalArgumentException();
    }
    if(code==null){
      throw new IllegalArgumentException();
    }
    this.email=email;
    this.password=code;
    this.username=name;
  }
  
  public String getUserName(){
    return this.username;
  }
  
  public String getPassword(){
    return this.password;
  }
  
  public String getEmail(){
    return this.email;
  }
  
  public void setEmail(String email){
    if(email==null){
      throw new IllegalArgumentException();
    }
    this.email=email;
  }
  
  @Override
  public boolean equals(Object obj){
    if(this==obj){
      return true;
    }
    if(!(obj instanceof User)){
      return false;
    }
    User user = (User) obj;
    return this.username.equals(user.getUserName()) && this.password.equals(user.getPassword());
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 83 * hash + Objects.hashCode(this.username);
    hash = 83 * hash + Objects.hashCode(this.password);
    return hash;
  }
}
