package com.example.mysql_library.service.impl;

import com.example.mysql_library.model.History;
import com.example.mysql_library.service.IHistoryService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HistoryService implements IHistoryService {

    private static HistoryService historyService = new HistoryService();

    private HistoryService(){
    }

    public static HistoryService getInstance(){
        return historyService;
    }

    @Override
    public void history(int id, String name,int count, int type,String readerName) {
        try{
            String tmp = "insert into history (book_id,book_name,type,count,reader_name) values (%d,'%s',%d,%d,'%s');";
            String sqlStr = String.format(tmp,id,name,type,count,readerName);
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
    public void borrowBook(int id, String name,int count, int type,String readerName) {
        try{
            String tmp1 = "update book set count = count - 1 where num = %d;";
            String tmp2 = "insert into history (book_id,book_name,type,count,reader_name) values (%d,'%s',%d,%d,'%s');";
            String sqlStr = String.format(tmp1,id);
            String sqlStr2 = String.format(tmp2,id,name,type,count,readerName);
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://192.168.50.252:3306/Library","root","123456");
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlStr2);
            preparedStatement.execute();
            preparedStatement1.execute();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void returnBook(int id, String name,int count, int type,String readerName) {
        try{
            String tmp1 = "update book set count = count + 1 where num = %d;";
            String tmp2 = "insert into history (book_id,book_name,type,count,reader_name) values (%d,'%s',%d,%d,'%s');";
            String sqlStr = String.format(tmp1,id);
            String sqlStr2 = String.format(tmp2,id,name,type,count,readerName);
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://192.168.50.252:3306/Library","root","123456");
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlStr2);
            preparedStatement.execute();
            preparedStatement1.execute();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<History> historyList() {
        List<History> historyList = new ArrayList<>();
        try{
            String sqlStr = "select * from history;";
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://192.168.50.252:3306/Library","root","123456");
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                int bookId = resultSet.getInt("book_id");
                String bookName = resultSet.getString("book_name");
                int type = resultSet.getInt("type");
                int count = resultSet.getInt("count");
                String readerName = resultSet.getString("reader_name");
                History history = new History();
                history.setId(id);
                history.setBookId(bookId);
                history.setBookName(bookName);
                history.setType(type);
                history.setCount(count);
                history.setReaderName(readerName);
                historyList.add(history);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return historyList;
    }


}
