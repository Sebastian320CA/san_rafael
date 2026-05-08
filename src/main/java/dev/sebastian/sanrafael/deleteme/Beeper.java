package dev.sebastian.sanrafael.deleteme;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Beeper {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startRoutine(){
        final Runnable beeper = new Runnable() {
            @Override
            public void run() {
                System.out.println("Beeper started");
            }
        };
        scheduler.scheduleAtFixedRate(beeper, 0,1, TimeUnit.SECONDS);
    }
}
