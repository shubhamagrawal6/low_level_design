import java.io.*;
import java.util.*;

enum Category{
    FICTION, SCI_FI, MYSTERY, FABLE, MYTHOLOGY;
}

class Book{
    private String name;
	private String author;
	private String publisher;
	private int publish_year;
	private Category category;
	private double price;
	private int count;

	public Book(String name, String author, String publisher, int publish_year, Category category, double price, int count){
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.publish_year = publish_year;
        this.category = category;
        this.price = price;
        this.count = count;
    }

    public String getName(){
        return this.name;
    }

	public String getAuthor(){
        return this.author;
    }

	public Category getCategory(){
        return this.category;
    }

	public int getCount(){
        return this.count;
    }
}

class Author{
    private String name;
    private TreeMap<Integer, Book> mostSold = new TreeMap<Integer, Book>(Comparator.reverseOrder());

    public Author(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public TreeMap<Integer, Book> getMostSold(){
        return this.mostSold;
    }

    public void addBook(Book book){
        if(book.getAuthor() != this.name){
            System.out.println("This book does not belong to the author");
            return;
        }

        mostSold.put(book.getCount(), book);
    }
}

class CategorySales{
    private Category category;
    private TreeMap<Integer, Book> mostSold = new TreeMap<Integer, Book>(Comparator.reverseOrder());

    public CategorySales(Category category){
        this.category = category;
    }

    public Category getCategory(){
        return this.category;
    }

    public TreeMap<Integer, Book> getMostSold(){
        return this.mostSold;
    }

    public void addBook(Book book){
        if(book.getCategory() != this.category){
            System.out.println("This book does not belong to the category");
            return;
        }

        mostSold.put(book.getCount(), book);
    }
}

class Catalog{
    private LinkedList<Book> books = new LinkedList<Book>();
	private HashMap<String, Author> authorMap = new HashMap<String, Author>();
	private HashMap<Category, CategorySales> categoryMap = new HashMap<Category, CategorySales>();

	public Catalog() {}

	public void addBookToCatalog(Book book){
        this.books.add(book);

        if(!this.authorMap.containsKey(book.getAuthor())){
            Author tempAuthor = new Author(book.getAuthor());
            this.authorMap.put(tempAuthor.getName(), tempAuthor);
        }
        this.authorMap.get(book.getAuthor()).addBook(book);

        if(!this.categoryMap.containsKey(book.getCategory())){
            CategorySales tempCategorSales = new CategorySales(book.getCategory());
            this.categoryMap.put(tempCategorSales.getCategory(), tempCategorSales);
        }
        this.categoryMap.get(book.getCategory()).addBook(book);
    }

	public ArrayList<Book> searchBookByName(String prefix){
        ArrayList<Book> bookList = new ArrayList<Book>();
        
        for(Book book: this.books){
            if(book.getName().startsWith(prefix)){
                bookList.add(book);
            }
        }


        return bookList;
    }

    public ArrayList<Book> searchBookByAuthor(String authorName){
        ArrayList<Book> bookList = new ArrayList<Book>();
        
        var map = this.authorMap.get(authorName).getMostSold();
        for(Book book: map.values()){
            bookList.add(book);
        }
        
        return bookList;
    }

    public ArrayList<Book> getMostSoldBooksByAuthor(String authorName, int limit){
        ArrayList<Book> bookList = new ArrayList<Book>();
        
        var map = this.authorMap.get(authorName).getMostSold();
        for(Book book: map.values()){
            if(limit == 0){
                return bookList;
            }
            bookList.add(book);
            limit--;
        }
        
        return bookList;
    }

    public ArrayList<Book> getMostSoldBooksByCategory(Category category, int limit){
        ArrayList<Book> bookList = new ArrayList<Book>();

        var map = this.categoryMap.get(category).getMostSold();
        for(Book book: map.values()){
            if(limit == 0){
                return bookList;
            }
            bookList.add(book);
            limit--;
        }

        return bookList;
    }
}

public class book_catalog{
    public static void main(String[] args){
        Book book0 = new Book("HP & The PS", "J K Rowling", "Bloomsbury", 1997, Category.FICTION, 200, 80);
        Book book1 = new Book("HP & The COS", "J K Rowling", "Bloomsbury", 1998, Category.FICTION, 1000, 100);
        Book book2 = new Book("HP & The POA", "J K Rowling", "Bloomsbury", 1999, Category.FICTION, 2000, 500);
        Book book3 = new Book("HP & The HBP", "J K Rowling", "Bloomsbury", 2005, Category.FICTION, 3000, 700);
        Book book4 = new Book("The Immortals of Meluha", "Amish", "Westland", 2010, Category.MYTHOLOGY, 1500, 600);
        Book book5 = new Book("The Secret of the Nagas", "Amish", "Westland", 2011, Category.MYTHOLOGY, 2500, 400);
        Book book6 = new Book("The Oath of the Vayuputras", "Amish", "Westland", 2013, Category.MYTHOLOGY, 3500, 200);
        Book book7 = new Book("Do Androids dream of Electric Sheep", "Philip K Dick", "DoubleDay", 1968, Category.SCI_FI, 30, 20);

        Catalog catalog = new Catalog();
        catalog.addBookToCatalog(book0);
        catalog.addBookToCatalog(book1);
        catalog.addBookToCatalog(book2);
        catalog.addBookToCatalog(book3);
        catalog.addBookToCatalog(book4);
        catalog.addBookToCatalog(book5);
        catalog.addBookToCatalog(book6);
        catalog.addBookToCatalog(book7);

        ArrayList<Book> list = catalog.getMostSoldBooksByAuthor("Amish", 2);
        for(Book book: list){
            System.out.println(book.getName() + " " + book.getCount());
        }
        System.out.println("**************************************************************************************");

        list = catalog.getMostSoldBooksByCategory(Category.FICTION, 2);
        for(Book book: list){
            System.out.println(book.getName() + " " + book.getCount());
        }
        System.out.println("**************************************************************************************");

        list = catalog.searchBookByAuthor("Amish");
        for(Book book: list){
            System.out.println(book.getName() + " " + book.getCount());
        }
        System.out.println("**************************************************************************************");

        list = catalog.searchBookByName("Do");
        for(Book book: list){
            System.out.println(book.getName() + " " + book.getCount());
        }
    }
}