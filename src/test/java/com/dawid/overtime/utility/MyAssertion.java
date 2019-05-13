package com.dawid.overtime.utility;

public class MyAssertion {
    public static void assertDoesNotThrow(FailingRunnable action) {
        try {
            action.run();
        } catch (Exception ex) {
            throw new Error("expected action not to throw, but it did!", ex);
        }
    }

    public static void assertDoesThrow(FailingRunnable action) {
        try {
            action.run();
            throw new Error("expected action to throw, but id didn't!");
        } catch (Exception ex) {

        }
    }
}
