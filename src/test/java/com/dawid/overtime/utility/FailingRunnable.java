package com.dawid.overtime.utility;

@FunctionalInterface
public interface FailingRunnable {
    void run() throws Exception;
}
