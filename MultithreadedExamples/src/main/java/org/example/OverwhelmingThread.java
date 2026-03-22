package org.example;

public class OverwhelmingThread {
  public static void main(String[] args) {
    for (int i=0;i<10000;i++) {
      Thread thread = new Thread(() -> {
        System.out.println(Thread.currentThread().getName() + " is running");
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      });
      thread.start();
    }
  }
}
