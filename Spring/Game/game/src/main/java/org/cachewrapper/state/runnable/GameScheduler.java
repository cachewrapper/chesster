package org.cachewrapper.state.runnable;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public interface GameScheduler {

    ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();

    void schedule(@NotNull Duration duration);
}