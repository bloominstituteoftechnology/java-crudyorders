package com.finalcrudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CrudyApplication
{


    public static void main(String[] args)
    {
        SpringApplication.run(OrdersApplication.class,
            args);
    }

}