package org.example;

public class Main {
  public static void main(String[] args) {

    ThreadExampleRunnable threadRunnable = new ThreadExampleRunnable();
    Thread thread1 = new Thread(threadRunnable);
    //ThreadExample thread1 = new ThreadExample();
    //thread1.setDaemon(true);
    thread1.start();

    for (int i=0;i<1000;i++) {
      System.out.println("My Main Code " + i);
    }
  }
}