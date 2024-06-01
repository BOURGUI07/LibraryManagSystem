/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;
import domain.*;
import java.util.*;

/**
 *
 * @author lenovo
 */
public class Waitlist {
  private List<Patron> list;
  private Book book;
  
  public Waitlist(Book book){
    this.book = book;
    this.list = new ArrayList<>();
  }
  
  public void addPatron(Patron patron){
    if(!this.isTheWaitlistFull()){
      this.list.add(patron);
    }
  }
  
  public void removePatron(Patron patron){
    this.list.remove(patron);
  }
  
  public boolean isTheWaitlistFull(){
    return this.list.size()==this.book.getNumberOfBorrowedCopies();
  }
  
  public boolean waitlistIsEmpty(){
    return this.list.isEmpty();
  }
  
  
  
  
  
  
}
