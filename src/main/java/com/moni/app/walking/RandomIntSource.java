package com.moni.app.walking;

/** Injectable random source so walk planning can be deterministic in tests. */
@FunctionalInterface
public interface RandomIntSource {
    int nextInt(int bound);
}
