# Fifth-Assignment-Multithread-Basics

## 🔴 Question 1

1. **What will be printed after interrupting the thread?**

```java
public static class SleepThread extends Thread {
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted!");
            } finally {
                System.out.println("Thread will be finished here!!!");
            }
        }
    }

    public static void main(String[] args) {
        SleepThread thread = new SleepThread();
        thread.start();
        thread.interrupt();
    }
```

**Answer**
you’re starting a new thread (SleepThread) which is programmed to sleep for 10,000 milliseconds (or 10 seconds). However, immediately after starting the thread, you’re interrupting it with thread.interrupt().
When a thread is sleeping or waiting, and interrupt() is called on that thread, it throws an InterruptedException. In your run() method, you’ve included a catch block for this exception. So, when the thread is interrupted during its sleep, it will catch the InterruptedException and execute the code in the catch block.

After the catch block, the finally block will be executed regardless of whether an exception was thrown or not.
    Thread was interrupted!
    Thread will be finished here!!!


## 🔴 Question 2

2. In Java, what would be the outcome if the `run()` method of a `Runnable` object is invoked directly, without initiating it inside a `Thread` object?
```java
public class DirectRunnable implements Runnable {
    public void run() {
        System.out.println("Running in: " + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        DirectRunnable runnable = new DirectRunnable();
        runnable.run();
    }
}
```

**Answer**
If you invoke the run() method directly without initiating it inside a Thread object, the code inside the run() method will be executed in the current thread rather than in a new thread.

The run() method of the DirectRunnable object is called directly in the main method. Therefore, the run() method will be executed in the main thread, not in a new thread. The output of your program will be:

    Running in: main

This is because the Thread.currentThread().getName() method returns the name of the currently executing thread, which is the main thread in this case.

## 🔴 Question 3

3. Elaborate on the sequence of events that occur when the `join()` method of a thread (let's call it `Thread_0`) is invoked within the `Main()` method of a Java program.
```java
public class JoinThread extends Thread {
    public void run() {
        System.out.println("Running in: " + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        JoinThread thread = new JoinThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Back to: " + Thread.currentThread().getName());
    }
}
```

**Answer**
When the join() method of a thread (in this case, Thread_0) is invoked within the main() method of a Java program, the following sequence of events occurs:

1. **Thread Creation**: A new thread (Thread_0) is created by instantiating the JoinThread class, which extends the Thread class.
```java
JoinThread thread = new JoinThread();
```
2. **Thread Start**: The start() method is called on Thread_0. This causes Thread_0 to begin execution and the run() method of Thread_0 is invoked. The JVM calls the run() method of Thread_0 on a separate call stack.
```java
thread.start();
```
3. **Thread Execution**: Thread_0 executes its run() method. It prints the name of the current thread, which is Thread_0.
```java
public void run() {
    System.out.println("Running in: " + Thread.currentThread().getName());
}
```
4. **Main Thread Waits for Thread_0**: The join() method is called on Thread_0 from the main thread. This causes the main thread to pause and wait until Thread_0 finishes its execution. If Thread_0 is interrupted, an InterruptedException is thrown.
```java
try {
    thread.join();
} catch (InterruptedException e) {
    e.printStackTrace();
}
```
5. **Thread_0 Finishes**: After Thread_0 has completed its execution, it terminates by returning from its run() method.
6. **Main Thread Resumes**: Once Thread_0 has finished, the main thread resumes and continues with its subsequent instructions. In this case, it prints the name of the current thread, which is main.
```java
System.out.println("Back to: " + Thread.currentThread().getName());
```
So, the output of the program will be something like:

    Running in: Thread-0
    Back to: main

This sequence of events ensures that the main thread waits for Thread_0 to complete before it continues, which is the purpose of the join() method. This can be useful in scenarios where you want to ensure that a thread has finished its tasks before you continue with the rest of the program. For example, you might be creating multiple threads to perform calculations and you need to wait until all calculations are complete before you can aggregate the results. In such cases, you would use the join() method to make the main thread wait until the worker threads have completed their tasks.
