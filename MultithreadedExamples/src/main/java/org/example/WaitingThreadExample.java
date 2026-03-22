package org.example;

import java.util.stream.StreamSupport;


public class WaitingThreadExample {
  public static void main(String[] args) {
    Thread longerThread = new Thread(() -> {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println("Longer sleeping thread has awakened.");
      System.out.println("State of the thread: " + Thread.currentThread().getState());
    });

    System.out.println("State of longerThread before starting: " + longerThread.getState());

    Thread shorterThread = new Thread(() -> {
      try {
        Thread.sleep(2000);
        longerThread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }

      System.out.println("Shorter sleeping thread has awakened and will now notify the longer sleeping thread.");
      System.out.println("State of the thread: " + Thread.currentThread().getState());
    });

    System.out.println("State of shorterThread before starting: " + shorterThread.getState());

    Thread monitorThread = createMonitorThread(shorterThread, Thread.State.WAITING);
    Thread longerMonitorThread = createMonitorThread(longerThread, Thread.State.TIMED_WAITING);

    longerMonitorThread.start();
    longerThread.start();
    shorterThread.start();
    monitorThread.start();
  }

  private static Thread createMonitorThread(Thread shorterThread, Thread.State state) {
    return new Thread(() -> {
      while(shorterThread.isAlive()) {
        if (shorterThread.getState() == state || shorterThread.getState() == Thread.State.TIMED_WAITING) {
          System.out.println(">>> " + shorterThread.getName() + " is in " + state + " <<<");
        }
      }
    });
  }
}
