package com.example.mysql_library.service;

import com.example.mysql_library.model.Book;

import java.util.List;

public interface IBookService {
    void newBook(int num,String name,String author,double price,int count);

    List<Book> getBookList();

}
