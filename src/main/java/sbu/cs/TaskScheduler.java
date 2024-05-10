package sbu.cs;

import java.util.ArrayList;
import java.util.List;

public class TaskScheduler
{
    public static class Task implements Runnable
    {
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */
        String taskName;
        int processingTime;

        public Task(String taskName, int processingTime) {
            this.taskName = taskName;
            this.processingTime = processingTime;
        }
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */

        @Override
        public void run() {
            try {
                Thread.sleep(processingTime);
            }catch (InterruptedException e) {
                System.out.println("exception");
            }
        }

        public int getTime() {
            return processingTime;
        }
        public String getName() {
            return taskName;
        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        ArrayList<String> finishedTasks = new ArrayList<>();


        // Sort tasks based on their estimated time
        tasks.sort((t1, t2) -> t2.getTime() - t1.getTime());


        for(Task task : tasks) {

            // Create a thread for the task
            Thread thread = new Thread(() -> {
                task.run();
            });

            // Start the thread
            thread.start();

            // Wait for the thread to finish
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Add finished task name to list
            finishedTasks.add(task.getName());

        }

        return finishedTasks;
    }

    public static void main(String[] args) {
        // Test your code here
    }
}
