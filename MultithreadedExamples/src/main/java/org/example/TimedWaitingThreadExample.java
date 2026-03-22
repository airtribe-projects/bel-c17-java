package org.example;

import javax.swing.text.Style;


public class TimedWaitingThreadExample {
  public static void main(String[] args) throws InterruptedException {
    Thread thread1 = new Thread(() -> {
      try {
        Thread.sleep(5000);
        System.out.println("Timed waiting thread has awakened.");
      } catch (InterruptedException e) {
        System.out.println("Thread was interrupted.");
      }
    });

    System.out.println("State of thread1 before starting: " + thread1.getState());
    thread1.start();
    //Thread.sleep(200);
    System.out.println("State of thread1 after starting: " + thread1.getState());
    System.out.println("Main thread is running.");
    System.out.println("State of the main thread is : " + Thread.currentThread().getState());

  }
}
