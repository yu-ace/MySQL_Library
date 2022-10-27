package com.example.mysql_library;

import com.example.mysql_library.model.Book;
import com.example.mysql_library.model.History;
import com.example.mysql_library.service.IBookService;
import com.example.mysql_library.service.IHistoryService;
import com.example.mysql_library.service.IReaderService;
import com.example.mysql_library.service.impl.BookService;
import com.example.mysql_library.service.impl.HistoryService;
import com.example.mysql_library.service.impl.ReaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class MySqlLibraryApplication implements CommandLineRunner {

    private Scanner scanner = new Scanner(System.in);
    private IBookService bookService = BookService.getInstance();
    private IHistoryService historyService = HistoryService.getInstance();
    private IReaderService readerService = ReaderService.getInstance();

    public static void main(String[] args) {
        SpringApplication.run(MySqlLibraryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        while(true){
            printHelp();
            String str = scanner.next();
            if(str.equals("1")){
                for(Book book : bookService.getBookList()){
                    System.out.println(book.getId() +"\t" + book.getName() + "\t" + book.getAuthor() + "\t"
                            +  "\t" + book.getPrice() + "\t" + book.getNum() + "\t" + book.getCount());
                }
            }else if(str.equals("2")){
                System.out.println("输入你的姓名");
                String readerName = scanner.next();
                System.out.println("输入书的id");
                int id = scanner.nextInt();
                for(Book book : bookService.getBookList()){
                    if(book.getNum() == id && book.getCount() > 0){
                        historyService.borrowBook(id,book.getName(),1,1,readerName);
                        readerService.newReader(readerName,1);
                    }else if(book.getNum() == id && book.getCount() < 1){
                        System.out.println("对不起，该书库存不足，请借其他书籍");
                    }
                }
            }else if(str.equals("3")){
                System.out.println("输入你的的姓名");
                String readerName = scanner.next();
                System.out.println("输入书的id");
                int id = scanner.nextInt();
                for(Book book : bookService.getBookList()){
                    if(book.getNum() == id){
                        historyService.returnBook(id,book.getName(),1,2,readerName);
                        readerService.newReader(readerName,2);
                    }
                }
            }else if(str.equals("s")){
                admin();
            }
        }
    }

    public void admin(){
        while(true){
            adminHelp();
            String str = scanner.next();
            if(str.equals("1")){
                System.out.println("请输入书的编号");
                int num = scanner.nextInt();
                System.out.println("请输入书的名字");
                String name = scanner.next();
                System.out.println("请输入书的作者");
                String author = scanner.next();
                System.out.println("请输入书的数量");
                int count = scanner.nextInt();
                System.out.println("请输入书的价格");
                double price = scanner.nextDouble();
                bookService.newBook(num,name,author,price,count);
                historyService.history(num,name,count,0,"管理员");
            }else if(str.equals("2")){
                for(History history : historyService.historyList()){
                    String t = "归还";
                    if(history.getType() == 0){
                        t = "上架";
                    }else if(history.getType() == 1){
                        t = "借取";
                    }
                    System.out.println(history.getReaderName() +  t + "\t" + history.getBookName()
                            + "\t" + history.getCount());
                }
            }else if(str.equals("q")){
                break;
            }
        }
    }

    public void printHelp(){
        System.out.println("欢迎来到图书馆图书系统");
        System.out.println("输入1 查看图书列表");
        System.out.println("输入2 借书");
        System.out.println("输入3 还书");
    }

    public void adminHelp(){
        System.out.println("欢迎来到图书馆管理系统");
        System.out.println("输入1 上架图书");
        System.out.println("输入2 查看历史记录");
        System.out.println("输入q 退出");
    }
}
