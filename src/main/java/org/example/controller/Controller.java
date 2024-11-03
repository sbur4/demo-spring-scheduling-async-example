package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {
    private final  TaskScheduler taskScheduler;
    private final  Map<String, ScheduledFuture<?>> scheduledTasks = new HashMap<>();

    @GetMapping("/hello/{cron}")
    public ResponseEntity<Map<String, ScheduledFuture<?>>> readYml(@PathVariable String cron) {
        String taskId = String.valueOf(scheduledTasks.size() + 1);

        Runnable task = () -> {
            try {
                System.out.println("Run task:" + taskId);
                log.info("Running task: {}", taskId);
            } catch (Exception e) {
                log.error("Error while executing task {}: {}", taskId, e.getMessage(), e);
            }
        };

        ScheduledFuture<?> future = null;
        try {
            future = taskScheduler.schedule(task, new CronTrigger(cron));
        } catch (IllegalArgumentException e) {
            log.error("Invalid cron expression: {}", cron);
        }

        scheduledTasks.put(taskId, future);
        log.info("Scheduled task with ID: {}", taskId);

        return new ResponseEntity<>(scheduledTasks, HttpStatus.OK);
    }
}