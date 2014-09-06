package com.tomkasp.customannotation;

import org.springframework.stereotype.Component;

/**
 * Created by tomkasp on 9/6/14.
 */
@Component
public class BeanTomasz {

    @ProfileExecution
    public void foo() {
        System.out.println("Executing method 'foo'.");
    }
}
