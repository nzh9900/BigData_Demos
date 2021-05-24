package org.llc.flink.batch.schedule;

import it.sauronsoftware.cron4j.Scheduler;

public class Test {

        public static void main(String[] args) {
            // Creates a Scheduler instance.
            Scheduler s = new Scheduler();
            // Schedule a once-a-minute task.
            s.schedule("*/1 * * * *", () -> System.out.println("Another minute ticked away..."));
            // Starts the scheduler.
            s.start();
            // Will run for ten minutes.
//            try {
//                Thread.sleep(1000L * 60L * 10L);
//            } catch (InterruptedException e) {
//                ;
//            }
//            // Stops the scheduler.
//            s.stop();
        }


}
