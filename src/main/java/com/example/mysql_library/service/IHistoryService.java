package com.example.mysql_library.service;

import com.example.mysql_library.model.History;

import java.util.List;

public interface IHistoryService {
    void history(int id,String bookName,int count,int type,String readerName);
    void borrowBook(int id,String name,int count, int type,String readerName);
    void returnBook(int id, String name,int count, int type,String readerName);
    List<History> historyList();
}
