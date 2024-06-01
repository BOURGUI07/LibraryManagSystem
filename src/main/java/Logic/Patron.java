/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;
import domain.*;
import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author lenovo
 */
public class Patron {
  private Management manag;
  private String name;
  private UUID id;
  private List<Borrow> borrows; //temporary list of current borrowed activities
  private List<Borrow> borrowHistory;
  private static final double FINE=1.5;
  private List<Book> notifications;
  private final int dayLimit = 3; //After notifying the patron of book availability for borrowing, if he didn't borrow the book after 3 days, the book gonna be removed from the notifications list
  
  public Patron(String name){
    this.name=name;
    this.id=UUID.randomUUID();
    this.manag=new Management();
    this.borrows=new ArrayList<>();
    this.notifications = new ArrayList<>();
    this.borrowHistory=new ArrayList<>();
  }
  
  public UUID getPatronID(){
    return this.id;
  }
  
  public String getPatronName(){
    return this.name;
  }
  
  public void borrowBook(String bookName){
    Book book = this.manag.getBookForName(bookName);
    if(book.canThisBookBeBorrowed()){
      book.borrow();
      Borrow borrow = new Borrow(book);
      book.addBorrowToBook(borrow);
      this.borrows.add(borrow);
      this.borrowHistory.add(borrow);
      this.manag.addPatronForBorrowStats(this);
      System.out.println(borrow);
    }
  }
  
  public void renewBook(String bookName){
      Book book = this.manag.getBookForName(bookName);
      Waitlist list = new Waitlist(book);
      if(list.waitlistIsEmpty()){
        Borrow borrow = new Borrow(book);
        if(borrows.contains(borrow)){
          borrow.setDuedate();
          borrow.renewloanPrice();
        }
      }

  }
  
  public Borrow getBorrowForBookName(String name){
    for(Borrow borrow:this.borrows){
      if(borrow.getBorrowedBookName().equals(name)){
        return borrow;
      }
    }  
    return null;
  }
 
  public void returnBook(String name){
    Book book = this.manag.getBookForName(name);
    Borrow borrow = this.getBorrowForBookName(name);
    book.removeBorrowFromBook(borrow);
    this.borrows.remove(borrow);
    LocalDate currentDate = LocalDate.now();
    borrow.getBorrowedBook().returnCopy();
    if(currentDate.isAfter(borrow.getDueDate())){
      int numberOfLateDays = (int) ChronoUnit.DAYS.between(currentDate,borrow.getDueDate());
      double price = numberOfLateDays*FINE;
      double globalPrice = price + borrow.getLoanPrice();
      System.out.println("You're " + numberOfLateDays + " days late, you have to pay an additional $" + price + "\nthe total price is $" + globalPrice);
    }else{
      System.out.println("You have to pay $" + borrow.getLoanPrice());
    }
  }
  
  public void reserveBook(String name){
    Book book = this.manag.getBookForName(name);
    if(book.canThisBookBeReserved()){
      book.reserve();
      Waitlist list = new Waitlist(book);
      list.addPatron(this);
      this.notify_Patron_If_Book_is_Ready_For_Borrowing(book, list);
    }
  }
  
  public void notify_Patron_If_Book_is_Ready_For_Borrowing(Book book, Waitlist list){
    if(book.nearestDate().equals(LocalDate.now())){
      list.removePatron(this);
      this.notifications.add(book);  
      if(this.didPassTheDayLimit(book.nearestDate())){
        this.notifications.remove(book);
      }
    }
  }
  
  public boolean didPassTheDayLimit(LocalDate date){
    return date.plusDays(dayLimit).equals(LocalDate.now());
  }
  
  public void showBorrowHistory(){
    for(Borrow borrow:this.borrowHistory){
      borrow.printHistory();
    }
  }
  
  @Override
  public String toString(){
    return this.name + ", ID = " + this.id;
  }
  
  @Override
  public boolean equals(Object obj){
    if(this==obj){
      return true;
    }
    if(!(obj instanceof Patron)){
      return false;
    }
    Patron patron = (Patron) obj;
    return this.name.equals(patron.getPatronName());
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 89 * hash + Objects.hashCode(this.name);
    return hash;
  }
  
}
