/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ui;
import Logic.*;
import domain.*;
import java.util.*;

/**
 *
 * @author lenovo
 */
public class UserInterface {
  private Management manag;
  private UserManag userManag;
  private Scanner scan;
  
  public UserInterface(Scanner scanner){
    this.scan=scanner;
    this.manag = new Management();
    this.userManag = new UserManag();
  }
  
  public void start(){
    System.out.println("Welcome to our online libary!");
      System.out.println("[R]: Register");
      System.out.println("[L]: Login");
      String answer = this.scan.nextLine();
      if(answer.equalsIgnoreCase("R")){
        this.registerPage();
      }else{
        this.loginPage();
      }
  }
  
  public void registerPage(){
    System.out.println("Enter user name");
    String username = this.scan.nextLine();
    while(username.isBlank()){
      System.out.println("Nothing is written. Type again your name:");
      username = this.scan.nextLine();
    }
    System.out.println("Enter a password");
    String password = this.scan.nextLine();
    while(!this.userManag.isSecure(password)){
      System.out.println("The passowrd must contain at leasr 8 characters, at least one upperCase letter, at least one lowercase letter, and at least one special Character!");
      System.out.println("Type again your password:");
      password = this.scan.nextLine();
    }
    System.out.println("confirm password:");
    String confirmedPassword = this.scan.nextLine();
    while(!confirmedPassword.equals(password)){
      System.out.println("The confirmed password doesn't macth the first password. Try again:");
      confirmedPassword = this.scan.nextLine();
    }
    if(confirmedPassword.equals(password)){
      User user = new User(username, password, null);
      if(this.userManag.isUserRegistrated(user)){
        this.loginOrHomePage(); 
      }else{
        System.out.println("Enter your email");
        String email = this.scan.nextLine();
        while(!this.userManag.isEmailValid(email)){
          System.out.println("Invalid email. Try again");
          email = this.scan.nextLine();
        }
        if(this.userManag.isEmailValid(email)){
          user.setEmail(email);
          this.userManag.registerUser(user);
          if(this.userManag.isUserRegistrated(user)){
            System.out.println("successfully registrated!");
            this.loginOrHomePage();
          }
        }
      }
    }
    
  }
  
  public void loginOrHomePage(){
    System.out.println("You're already registrated, You may wanr to login");
    System.out.println("[L]: Login");
    System.out.println("[H]: Return to Home Page");
    String answer = this.scan.nextLine();
    if(answer.equalsIgnoreCase("L")){
      this.loginPage();
    }else{
      this.start();
    }
  }
  
  public void loginPage(){
    System.out.println("Enter your username:");
    String username = this.scan.nextLine();
    System.out.println("Enter your password:");
    String password = this.scan.nextLine();
    while(this.userManag.passwordExist(password) && !this.userManag.usernameExist(username)){
      System.out.println("User name is false, Try again");
      this.loginPage();
    }
    while(!this.userManag.passwordExist(password) && this.userManag.usernameExist(username)){
      System.out.println("password is false, Try again");
      this.loginPage();
    }
    if(!this.userManag.passwordExist(password) && !this.userManag.usernameExist(username)){
      System.out.println("You're not registrated!");
      this.registerPage();
    }
    if(password.equals(this.userManag.getPasswordFor(username)) && !password.startsWith("admin@")){
      Patron patron = new Patron(username);
      this.patronPage(patron);
    }else{
      this.adminPanelPage();
    }
  }
  
  public void patronPage(Patron patron){
    while(true){
      System.out.println("[B]: Browse Avialable Books");
      System.out.println("[H]: View Borrow History");
      System.out.println("[F]: Filter books");
      System.out.println("[W]: Borrow Book");
      System.out.println("[T]: Return Book");
      System.out.println("[N]: Renew book");
      System.out.println("[S]: Reserve book");
      System.out.println("[X]: Quit");
      String answer = this.scan.nextLine();
      if(answer.equalsIgnoreCase("X")){
        break;
      }
      if(answer.equalsIgnoreCase("H")){
        patron.showBorrowHistory();
      }else if(answer.equalsIgnoreCase("B")){
        this.manag.viewAvailableBooks();
      }else if(answer.equalsIgnoreCase("W")){
        String bookName = this.enterBookName();
        patron.borrowBook(bookName);
      }else if(answer.equalsIgnoreCase("T")){
        String bookname = this.enterBookName();
        patron.returnBook(bookname);
      }else if(answer.equalsIgnoreCase("N")){
        String bookname = this.enterBookName();
        patron.renewBook(bookname);
      }else if(answer.equalsIgnoreCase("F")){
        this.filterBooksPage();
      }else{
        String bookname = this.enterBookName();
        patron.reserveBook(bookname);
      }
    }
  }
  
  public void filterBooksPage(){
    while(true){
      System.out.println("[Y]: Filter books by year");
      System.out.println("[P]: Filter books by price");
      System.out.println("[C]: Filter books by category");
      System.out.println("[X]: Return to menu");
      String answer = this.scan.nextLine();
      if(answer.equalsIgnoreCase("X")){
        break;
      }
      if(answer.equalsIgnoreCase("P")){
        this.filterBooksByPricePage();
      }else if(answer.equalsIgnoreCase("C")){
        this.filterBooksByCategoryPage();
      }else{
        this.filterBooksByYearPage();
      }
    }
  }
  
  public void filterBooksByYearPage(){
    System.out.println("Enter the min year");
    var minYear = Integer.valueOf(this.scan.nextLine());
    System.out.println("Enter the max year");
    var maxyear = Integer.valueOf(this.scan.nextLine());
    this.manag.filterBooksBetweenTwoYears(minYear, maxyear);
  }
  
  public void filterBooksByCategoryPage(){
    while(true){
      System.out.println("[H]: History");
      System.out.println("[B]: Biography");
      System.out.println("[E]: Educational");
      System.out.println("[R]: Romance");
      System.out.println("[F]: Fantasy");
      System.out.println("[P]: Professional");
      System.out.println("[X]: Return to filter menu page");
      String answer = this.scan.nextLine();
      if(answer.equalsIgnoreCase("X")){
        break;
      }
      if(answer.equalsIgnoreCase("H")){
        this.manag.filterBooksByCategory(Category.HISTORY);
      }else if(answer.equalsIgnoreCase("B")){
        this.manag.filterBooksByCategory(Category.BIOGRAPHY);
      }else if(answer.equalsIgnoreCase("e")){
        this.manag.filterBooksByCategory(Category.EDUCATIONAL);
      }else if(answer.equalsIgnoreCase("P")){
        this.manag.filterBooksByCategory(Category.PROFESSIONAL);
      }else if(answer.equalsIgnoreCase("R")){
        this.manag.filterBooksByCategory(Category.ROMANCE);
      }else{
        this.manag.filterBooksByCategory(Category.FANTASY);
      }

    }
  }
  
  public void filterBooksByPricePage(){
    System.out.println("Enter the minimum price");
    var minPrice = Double.valueOf(this.scan.nextLine());
    System.out.println("Enter the maximum price");
    var maxPrice = Double.valueOf(this.scan.nextLine());
    this.manag.filterBooksByPrice(minPrice, maxPrice);
  }
  
  public String enterBookName(){
    System.out.println("Enter the book name:");
    String bookname = this.scan.nextLine();
    return bookname;
  }
  
  public void adminPanelPage(){
    while(true){
      System.out.println("[A]: Add book");
      System.out.println("[R]: Remove book");
      System.out.println("[S]: Get the number of borrows of a Patron");
      System.out.println("[X] Quit");
      String answer = this.scan.nextLine();
      if(answer.equalsIgnoreCase("X")){
        break;
      }
      if(answer.equalsIgnoreCase("A")){
        this.addBookPage();
      }else if(answer.equalsIgnoreCase("R")){
        this.removeBookPage();
      }else{
        this.questionAboutPatronPage();
      }
    }
  }
  
  public void addBookPage(){
    System.out.println("Enter the book name:");
    String name = this.scan.nextLine();
    System.out.println("Enter the book author:");
    String author = this.scan.nextLine();
    System.out.println("Enter the number of available copies");
    int copies = Integer.parseInt(this.scan.nextLine());
    System.out.println("Enter publication year");
    int year = Integer.parseInt(this.scan.nextLine());
    Category category = this.pickCategory();
    System.out.println("Enter the loan price per day:");
    double price = Double.parseDouble(this.scan.nextLine());
    this.manag.addBook(name, author, copies, year, category, price);
  }
  
  public void removeBookPage(){
    System.out.println("Enter the book id: ");
    String id = this.scan.nextLine();
    this.returnToHomeOrMenue(id);
    if(!id.equalsIgnoreCase("M") && !id.equalsIgnoreCase("H")){
      this.manag.removeBookByName(id);
    }
  }
  
  public void questionAboutPatronPage(){
    System.out.println("Enter the patron name: ");
    String name = this.scan.nextLine();
    this.returnToHomeOrMenue(name);
    if(!name.equalsIgnoreCase("M") && !name.equalsIgnoreCase("H")){
      Patron patron = this.manag.getPatronForName(name);
      int borrowNumbers = this.manag.getNumberOfBorrowsForPatron(patron);
      System.out.println("The number of borrows for " + name + " is: " + borrowNumbers);
    }
    
  }
  
  public void returnToHomeOrMenue(String string){
    System.out.println("[M]: return to Admin Panel");
    System.out.println("[H]: return to Home Page");
    if(string.equalsIgnoreCase("M")){
      this.adminPanelPage();
    }else if(string.equalsIgnoreCase("H")){
      this.start();
    }
  }
  
  public Category pickCategory(){
    System.out.println("Enter Category");
    System.out.println("[H]: History");
    System.out.println("[B]: Biography");
    System.out.println("[E]: Educational");
    System.out.println("[R]: Romance");
    System.out.println("[F]: Fantasy");
    System.out.println("[P]: Professional");
    Category category;
    String answer = this.scan.nextLine();
    if(answer.equalsIgnoreCase("H")){
        category = Category.HISTORY;
      }else if(answer.equalsIgnoreCase("B")){
        category= Category.BIOGRAPHY;
      }else if(answer.equalsIgnoreCase("e")){
        category= Category.EDUCATIONAL;
      }else if(answer.equalsIgnoreCase("P")){
        category = Category.PROFESSIONAL;
      }else if(answer.equalsIgnoreCase("R")){
        category= Category.ROMANCE;
      }else{
        category = Category.FANTASY;
      }
    return category;
  }
}

 


