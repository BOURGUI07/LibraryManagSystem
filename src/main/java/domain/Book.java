/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package domain;
import Logic.Borrow;
import java.util.*;
import java.time.*;

/**
 *
 * @author lenovo
 */
public class Book {

    private final String bookName;
    private final String bookAuthor;
    private final UUID ISBN;
    private final int publicationYear;
    private final int numberOfCopies;
    private final Category category;
    private int borrowedCopies;
    private double loanPricePerDay;
    private int reservations;
    private List<Borrow> list;
    
    public Book(String name, String author, int copies, int publicationYear, Category category, double price){
      this.bookName=name;
      this.bookAuthor=author;
      this.numberOfCopies=copies;
      this.category=category;
      this.publicationYear=publicationYear;
      this.loanPricePerDay=price;
      this.ISBN=UUID.randomUUID();
      this.borrowedCopies=0;
      this.reservations=0;
      this.list=new ArrayList<>();
    }
    
    public void addBorrowToBook(Borrow borrow){
      this.list.add(borrow);
    }
    
    public void removeBorrowFromBook(Borrow borrow){
      this.list.remove(borrow);
    }
    
    public List<Borrow> getBorrowList(){
      return this.list;
    }
    
    public List<LocalDate> listOfDueDates(){
      List<LocalDate> dateList = new ArrayList<>();
      for(Borrow borrow:this.list){
        dateList.add(borrow.getDueDate());
      }
      return dateList;
    }
    
    public LocalDate nearestDate(){
      LocalDate nearest = this.listOfDueDates().get(0);
      for(int i=1;i<this.listOfDueDates().size();i++){
        if(this.listOfDueDates().get(i).isBefore(nearest)){
          nearest = this.listOfDueDates().get(i);
        }
      }
      return nearest;
    }
    
    public String getBookNamae(){
      return this.bookName;
    }
    
    public String getBookAuthor(){
      return this.bookAuthor;
    }
    
    public Category getCategory(){
      return this.category;
    }
    
    public int getPublicationYear(){
      return this.publicationYear;
    }
    
    public int getNumberOfCopies(){
      return this.numberOfCopies;
    }
    
    public int getNumberOfBorrowedCopies(){
      return this.borrowedCopies;
    }
    
    public UUID getBookISBN(){
      return this.ISBN;
    }
    
    public int getReservations(){
      return this.reservations;
    }
    
    public void reserve(){
        this.reservations++;
    }
    
    public void cancelReservation(){
      this.reservations--;
    }
    
    public boolean canThisBookBeReserved(){
      return (this.reservations<this.borrowedCopies) && (!this.canThisBookBeBorrowed());
    }
    
    public boolean canThisBookBeBorrowed(){
      return this.availableCopies()>0;
    }
    
    public double bookPrice(){
      return this.loanPricePerDay;
    }
    
    
    public void borrow(){
      if(this.canThisBookBeBorrowed()){
        this.borrowedCopies++;
      }else{
        System.out.println("No available copies to borrow.");
      }
    }
    
   public void returnCopy(){
     this.borrowedCopies--;
   }
    
   public int availableCopies(){
      return this.numberOfCopies-this.borrowedCopies;
   }
    
   public void setLoanPrice(double price){
     if(price<0){
       throw new IllegalArgumentException();
     }
     this.loanPricePerDay=price;
   }
   
   @Override
   public String toString(){
     String a= "The book '"+ this.bookName+"' by " + this.bookAuthor + ", published in " + this.publicationYear+", ";
     String b= "falls under the category of\n" + this.category + ". It has an ID of'" + this.ISBN + "', ";
     String c = "and it's loan price per day is $" + this.loanPricePerDay + ". Currently,\n";
     String d = "there are " + this.numberOfCopies + " copies available for loan.";
     return a+b+c+d;
   } 
   
   @Override
   public boolean equals(Object obj){
     if(this==obj){
       return true;
     }
     if(!(obj instanceof Book)){
       return false;
     }
     Book book = (Book) obj;
     return this.ISBN.equals(book.getBookISBN()) && this.bookName.equals(book.getBookNamae());
   }

  @Override
  public int hashCode() {
    return this.ISBN.hashCode();
  }
}
