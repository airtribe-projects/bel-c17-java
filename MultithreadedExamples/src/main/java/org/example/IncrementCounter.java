package org.example;

public class IncrementCounter {
  private int counter = 0;

  public IncrementCounter(int counter) {
    this.counter = counter;
  }

  public synchronized void increment() {
    int currentCounter = counter;
    System.out.println(Thread.currentThread().getName() + " read counter: " + currentCounter);
    counter = currentCounter + 1;
    System.out.println(Thread.currentThread().getName() + " incremented counter to: " + counter);
  }


}
