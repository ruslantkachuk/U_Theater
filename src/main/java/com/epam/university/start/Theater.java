package com.epam.university.start;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Theater {

    public static void main(final String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
    }
}
