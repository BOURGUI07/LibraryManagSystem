/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;
import domain.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;





/**
 *
 * @author lenovo
 */
public class Management {
  private List<Book> list;
  private Map<Patron, Integer> map;
  
  public Management(){
    this.list=new ArrayList<>();
    this.map= new HashMap<>();
  }
  
  public void addBook(String name, String author, int copies, int year, Category category, double price){
    Book book = new Book(name, author, copies, year, category, price);
    this.list.add(book);
    if(this.list.contains(book)){
      System.out.println("Successfully Added Book!");
    }
  }
  
  public String getNameForID(UUID id){
    if(id==null){
      throw new IllegalArgumentException();
    }
    if(this.list.isEmpty()){
      return null;
    }
    for(Book book:this.list){
      if(book.getBookISBN().equals(id)){
        return book.getBookNamae();
      }
    }
    return null;
  }
  
  public Book getBookForID(UUID id){
    if(id==null){
      throw new IllegalArgumentException();
    }
    if(this.list.isEmpty()){
      return null;
    }
    for(Book book:this.list){
      if(book.getBookISBN().equals(id)){
        return book;
      }
    }
    return null;
  }
  
  public Book getBookForName(String name){
    if(name==null){
      throw new IllegalArgumentException();
    }
    if(this.list.isEmpty()){
      return null;
    }
    for(Book book:this.list){
      if(book.getBookNamae().equals(name)){
        return book;
      }
    }
    return null;
  }
  
  public void removeBook(UUID id){
    Book book = this.getBookForID(id);
    if(book.getBorrowList().isEmpty() && book.getReservations()==0){
      this.list.remove(book);
    }
  }
  
  public void viewAvailableBooks(){
    List<Book> availableBooks = this.list.stream().filter(book -> book.canThisBookBeBorrowed()==true).collect(Collectors.toCollection(ArrayList::new));
    for(Book book:availableBooks){
      System.out.println(book);
    }
  }
  
  public void filterBooksByYear(int year){
    for(Book book:this.list){
      if(book.getPublicationYear()==year){
        System.out.println(book);
      }
    }
  }
  
  public void filterBooksBeforeYear(int year){
    for(Book book:this.list){
      if(book.getPublicationYear()<=year){
        System.out.println(book);
      }
    }
  }
  
  public void filterBooksAfterYear(int year){
    for(Book book:this.list){
      if(book.getPublicationYear()>=year){
        System.out.println(book);
      }
    }
  }
  
  public void filterBooksBetweenTwoYears(int smallYear, int bigYear){
    for(Book book:this.list){
      if(book.getPublicationYear()>=smallYear && book.getPublicationYear()<=bigYear){
        System.out.println(book);
      }
    }
  }
  
  public void filterBooksByCategory(Category category){
    for(Book book:this.list){
      if(book.getCategory()==category){
        System.out.println(book);
      }
    }
  }
  
  public void filterBooksByPrice(double minPrice, double maxPrice){
    for(Book book:this.list){
      if(book.bookPrice()>=minPrice && book.bookPrice()<=maxPrice){
        System.out.println(book);
      }
    }
  }
  
  public void addPatronForBorrowStats(Patron patron){
    if(this.map.containsKey(patron)){
      int count = this.map.get(patron);
      this.map.put(patron, count++);
    }else{
      this.map.putIfAbsent(patron, 1);
    }
  }
  
  
  public int getNumberOfBorrowsForPatron(Patron patron){
    if(this.map.containsKey(patron)){
      return this.map.get(patron);
    }
    return -1;
  }
  
  public Patron getPatronForName(String name){
    Set<Patron> set = this.map.keySet();
    Iterator<Patron> itr = set.iterator();
    Patron patron;
    while(itr.hasNext()){
      patron = itr.next();
      if(patron.getPatronName().equals(name)){
        return patron;
      }
    }
    return null;
  }
  
  public boolean isID_Valid(String id){
    String uuidPattern = 
            "^\\h*\\b[0-9a-fA-F]{8}\\b-\\b[0-9a-fA-F]{4}\\b-\\b[0-9a-fA-F]{4}\\b-\\b[0-9a-fA-F]{4}\\b-\\b[0-9a-fA-F]{12}\\b\\h*$";
        return Pattern.matches(uuidPattern, id);
  }
  
  public void removeBookByName(String id){
    if(this.isID_Valid(id)){
      UUID bookID = UUID.fromString(id);
      this.removeBook(bookID);
    }
  }
  
  
  
}
