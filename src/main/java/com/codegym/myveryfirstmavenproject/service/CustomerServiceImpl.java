package com.codegym.myveryfirstmavenproject.service;

import com.codegym.myveryfirstmavenproject.model.Customer;
import com.codegym.myveryfirstmavenproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public Customer findById(int id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public List<Customer> findByAge(String age) {
        return customerRepository.findByAge(age);
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Page<Customer> findByName(String name, Pageable pageable) {
        return customerRepository.findAllByNameContainsOrderByAgeAsc(name, pageable);
    }

    @Override
    public Page<Customer> findByProductName(String name, Pageable pageable) {
        return customerRepository.findByProductName(name, pageable);
    }
}
