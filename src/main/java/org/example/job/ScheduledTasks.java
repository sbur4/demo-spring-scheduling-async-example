package org.example.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ScheduledTasks {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // You can schedule a method to be executed at a fixed interval by using
    @Scheduled(fixedRate = 15000) // 15 sec
    public void scheduleTaskWithFixedRate() {
        String taskTime = dateFormat.format(new Date());
        log.info("Fixed Rate Task :: Execution Time - {}", taskTime);
        System.out.println("Fixed Rate Task :: Execution Time - " + taskTime);
    }

    // You can execute a task with a fixed delay between the completion of
    // the last invocation and the start of the next, using fixedDelay parameter.
    @Scheduled(fixedDelay = 30000)
//    @Scheduled(fixedDelay = 24, timeUnit = TimeUnit.HOURS)
    public void scheduleTaskWithFixedDelay() {
        log.info("Fixed Delay Task :: Execution Time - {}", dateFormat.format(new Date()));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            log.error("Ran into an error {}", ex);
            throw new IllegalStateException(ex);
        }
    }

    // You can use initialDelay parameter with fixedRate and fixedDelay to delay the first
    // execution of the task with the specified number of milliseconds.
    @Scheduled(fixedRate = 50000, initialDelay = 5000)
    public void scheduleTaskWithInitialDelay() {
        log.info("Fixed Rate Task with Initial Delay :: Execution Time - {}", dateFormat.format(new Date()));
    }

//    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "*/10 * * * * *") // Execute every 1 second
    public void scheduleTaskWithCronExpression() {
        log.info("Cron Task :: Execution Time - {}", dateFormat.format(new Date()));
    }

//
//    @Scheduled(initialDelay = 10000)
//    public void doSomething() {
//        log.info("Schedule a One-time Task :: Execution Time - {}", dateFormat.format(new Date()));
//    }
}