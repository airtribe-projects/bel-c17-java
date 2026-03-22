package org.example;

public class ThreadExampleRunnable implements Runnable{
  @Override
  public void run() {
    System.out.println("Hello from thread " + Thread.currentThread().getName());
    for (int i=0;i<10000;i++) {
      System.out.println(Thread.currentThread().getName() + " is running " + i);
    }
  }
}
