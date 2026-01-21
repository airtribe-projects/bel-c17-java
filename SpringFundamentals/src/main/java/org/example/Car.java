package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class Car {

  @Value("${car.color}")
  private String color;

  @Value("${car.carType}")
  private String carType;

  @Autowired
  private Engine engine;

  public Car(@Value("${car.color}") String color, @Value("${car.carType}") String carType, Engine engine) {
    this.color = color;
    this.carType = carType;
    this.engine = engine;
  }

  public Car() {

  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getCarType() {
    return carType;
  }

  public void setCarType(String carType) {
    this.carType = carType;
  }

  public void printCar() {
    System.out.println("Car Color: " + color + ", Car Type: " + carType + ", Engine Type: " + engine.getEngineType() + ", Engine Power: " + engine.getEnginePower());
  }

  public Engine getEngine() {
    return engine;
  }

  public void setEngine(PetrolEngine engine) {
    this.engine = engine;
  }
}
