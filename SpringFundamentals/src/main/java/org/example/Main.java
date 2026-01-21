package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
  public static void main(String[] args) {

//    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    Car car = applicationContext.getBean(Car.class);
    Car car2 = applicationContext.getBean(Car.class);
    car.printCar();
    System.out.println(car.hashCode());
    System.out.println(car2.hashCode());
  }
}

// Beans definition
// XML based configuration
// Java based configuration
// Annotation based configuration