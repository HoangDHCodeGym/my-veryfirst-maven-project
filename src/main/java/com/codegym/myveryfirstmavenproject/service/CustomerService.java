package com.codegym.myveryfirstmavenproject.service;

import com.codegym.myveryfirstmavenproject.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();
    Customer findById(int id);
    List<Customer> findByAge(String age);
    Page<Customer> findAll(Pageable pageable);
    Page<Customer> findByName(String name, Pageable pageable);
}
