package com.tomkasp.customannotation;

import java.lang.annotation.*;

/**
 * Created by tomkasp on 9/6/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface ProfileExecution {
}
