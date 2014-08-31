package com.tomkasp;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by tomkasp on 8/31/14.
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}