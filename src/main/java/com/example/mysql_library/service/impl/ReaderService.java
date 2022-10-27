package com.example.mysql_library.service.impl;

import com.example.mysql_library.model.Reader;
import com.example.mysql_library.service.IReaderService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReaderService implements IReaderService {

    private static ReaderService readerService = new ReaderService();

    private ReaderService(){
    }

    public static ReaderService getInstance(){
        return readerService;
    }

    @Override
    public void newReader(String name,int type) {
        try{
            String tmp = "insert into reader(name,type) values ('%s',%d);";
            String sqlStr = String.format(tmp,name,type);
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://192.168.50.252/Library","root","123456");
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.execute();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Reader> readerList() {
        List<Reader> readerList = new ArrayList<>();
        try{
            String sqlStr = "select * from reader;";
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://192.168.50.252:3306/Library","root","123456");
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Reader reader = new Reader();
                reader.setId(id);
                reader.setName(name);
                readerList.add(reader);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return readerList;
    }
}
