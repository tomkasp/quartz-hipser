package com.tomkasp;


import org.apache.tomcat.jdbc.pool.DataSource;
import org.hibernate.type.descriptor.java.JdbcTimestampTypeDescriptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {






    public static void main(String[] args) {


        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        CustomerRepository repository = context.getBean(CustomerRepository.class);

        DataSource dataSource = (DataSource) context.getBean("dataSource");
        System.out.println("!!!!DATA SOURCE VALUE:" + dataSource);


        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List list = jdbcTemplate.queryForList("select * from QRTZ_CALENDARS");

        System.out.println("!!!!LIST" + list);
        // save a couple of customers
        repository.save(new Customer("Jack", "Bauer"));
        repository.save(new Customer("Chloe", "O'Brian"));
        repository.save(new Customer("Kim", "Bauer"));
        repository.save(new Customer("David", "Palmer"));
        repository.save(new Customer("Michelle", "Dessler"));

        // fetch all customers
        Iterable<Customer> customers = repository.findAll();
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer by ID
        Customer customer = repository.findOne(1L);
        System.out.println("Customer found with findOne(1L):");
        System.out.println("--------------------------------");
        System.out.println(customer);
        System.out.println();

        // fetch customers by last name
        List<Customer> bauers = repository.findByLastName("Bauer");
        System.out.println("Customer found with findByLastName('Bauer'):");
        System.out.println("--------------------------------------------");
        for (Customer bauer : bauers) {
            System.out.println(bauer);
        }

        context.close();
    }
}
