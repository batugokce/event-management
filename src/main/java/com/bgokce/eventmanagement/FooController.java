package com.bgokce.eventmanagement;

import com.bgokce.eventmanagement.usecases.sendNotification.MyRunnable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/foo")
@Slf4j
public class FooController {

    private TaskScheduler taskScheduler;

    Runnable newRunnable;

    @GetMapping("/log")
    public void logg(){
        log.info("mesaj");
    }

    @GetMapping("/{sayi}")
    public void firstTry(@PathVariable Long sayi){
        System.out.println("first");
        executeTaskT(5, 2152L);
    }

    @Async
    public void executeTaskT(int sec, Long sayi) {
        newRunnable = new MyRunnable(sayi);
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        taskScheduler = new ConcurrentTaskScheduler(localExecutor);

        Date date = new Date();
        date.setTime(date.getTime() + 1000*sec);

        taskScheduler.schedule(newRunnable,
                new Date(date.getTime()));
    }

    @Scheduled(cron="*/5 * * * * *", zone="Europe/Istanbul")
    public void task3() {
        //System.out.println(Thread.currentThread().getName()+" Task 3 executed at "+ new Date());
    }
}
