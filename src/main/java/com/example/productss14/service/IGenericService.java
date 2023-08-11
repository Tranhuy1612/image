package com.example.productss14.service;

import java.util.List;

public interface IGenericService<T , E> {
   List<T> findAll();
   void save(T t);
   void delete (E e);
   T findById(E e);
}
