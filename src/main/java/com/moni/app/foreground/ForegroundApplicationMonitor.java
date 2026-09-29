package com.moni.app.foreground;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/** Daemon polling monitor that reports foreground application changes every 500 ms. */
public final class ForegroundApplicationMonitor implements AutoCloseable {
    private static final long POLL_INTERVAL_MILLIS = 500;

    private final ForegroundApplicationProvider provider;
    private final Consumer<ForegroundApplication> onApplicationChanged;
    private final ForegroundApplicationTracker tracker = new ForegroundApplicationTracker();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(runnable -> {
        Thread thread = new Thread(runnable, "moni-foreground-monitor");
        thread.setDaemon(true);
        return thread;
    });

    public ForegroundApplicationMonitor(
            ForegroundApplicationProvider provider,
            Consumer<ForegroundApplication> onApplicationChanged
    ) {
        this.provider = Objects.requireNonNull(provider, "provider must not be null");
        this.onApplicationChanged = Objects.requireNonNull(onApplicationChanged, "onApplicationChanged must not be null");
    }

    public void start() {
        executor.scheduleAtFixedRate(this::poll, 0, POLL_INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void close() {
        executor.shutdownNow();
    }

    private void poll() {
        try {
            tracker.track(provider.foregroundApplication()).ifPresent(onApplicationChanged);
        } catch (RuntimeException ignored) {
            // Foreground processes can disappear or become inaccessible between native calls.
        }
    }
}
