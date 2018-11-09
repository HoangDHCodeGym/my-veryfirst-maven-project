package com.codegym.myveryfirstmavenproject.repository;

import com.codegym.myveryfirstmavenproject.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {
    List<Customer> findByAge(String age);
    Page<Customer> findAllByNameContainsOrderByAgeAsc(String name, Pageable pageable);
}
