package org.example;

class SharedResource {
  public synchronized void useResource() {
    try {
      System.out.println(Thread.currentThread().getName() + " is using the resource.");
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
public class BlockedThreadExample {
  public static void main(String[] args) throws InterruptedException {
    SharedResource resuource = new SharedResource();
    Thread thread = new Thread(() -> {
      resuource.useResource();
    });

    Thread thread2 = new Thread(() -> {
      resuource.useResource();
    });

    thread.start();
    thread2.start();
    Thread.sleep(200);

    System.out.println("State of thread0: " + thread.getState());
    System.out.println("State of thread1: " + thread2.getState());
  }
}
