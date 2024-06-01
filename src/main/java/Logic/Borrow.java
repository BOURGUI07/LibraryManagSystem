/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;
import domain.*;
import java.time.*;
import java.util.Objects;

/**
 *
 * @author lenovo
 */
public class Borrow {
    private static final int PERIOD=14;
    private final Book book;
    private LocalDate dueDate;
    private double loanPrice;
    
    public Borrow(Book book){
      this.book = book;
      this.dueDate = LocalDate.now().plusDays(PERIOD);
      this.loanPrice = book.bookPrice()*PERIOD;
    }
    
    public Book getBorrowedBook(){
      return this.book;
    }
    
    public LocalDate getDueDate(){
      return this.dueDate;
    }
    
    public double getLoanPrice(){
      return this.loanPrice;
    }
    
    public void setDuedate(){
      this.dueDate= this.dueDate.plusDays(PERIOD);
    }
    
    public void renewloanPrice(){
      this.loanPrice+=this.loanPrice;
    }
    
    public String getBorrowedBookName(){
      return this.book.getBookNamae();
    }
    
    @Override
    public boolean equals(Object obj){
      if(this==obj){
        return true;
      }
      if(!(obj instanceof Borrow)){
        return false;
      }
      Borrow b = (Borrow) obj;
      return this.book.equals(b.getBorrowedBook()) && this.dueDate.equals(b.getDueDate());
    }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 61 * hash + Objects.hashCode(this.book);
    return hash;
  }
    
    
    
    @Override
    public String toString(){
    return "You have to pay: $" + this.loanPrice + " and you must return it at: " + this.dueDate;
  }
    
    public void printHistory(){
      System.out.println("Book borrowed is:\n" + this.book + "\nDue Date was: " + this.dueDate);
    }
}
