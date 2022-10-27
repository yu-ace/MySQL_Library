package com.example.mysql_library.service;

import com.example.mysql_library.model.Reader;

import java.util.List;

public interface IReaderService {
    void newReader(String name,int type);
    List<Reader> readerList();
}
