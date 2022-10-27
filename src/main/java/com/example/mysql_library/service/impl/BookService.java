package com.example.mysql_library.service.impl;

import com.example.mysql_library.model.Book;
import com.example.mysql_library.service.IBookService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookService implements IBookService {

    private static BookService bookService = new BookService();

    private BookService(){
    }

    public static BookService getInstance(){
        return bookService;
    }

    @Override
    public void newBook(int num,String name, String author, double price,int count) {
        try{
            String tmp = "insert into book (num,name,author,price,count) values (%d,'%s','%s',%f,%d);";
            String sqlStr = String.format(tmp,num,name,author,price,count);
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://192.168.50.252:3306/Library","root","123456");
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.execute();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getBookList() {
        List<Book> bookList = new ArrayList<>();
        try{
            String sqlStr = "select * from book;";
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://192.168.50.252:3306/Library","root","123456");
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int num = resultSet.getInt("num");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                double price = resultSet.getDouble("price");
                int count = resultSet.getInt("count");
                Book book = new Book();
                book.setId(id);
                book.setNum(num);
                book.setName(name);
                book.setAuthor(author);
                book.setPrice(price);
                book.setCount(count);
                bookList.add(book);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return bookList;
    }
}
