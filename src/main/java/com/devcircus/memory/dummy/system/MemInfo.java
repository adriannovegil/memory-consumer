package com.devcircus.memory.dummy.system;

/**
 *
 * @author Adrian Novegil <adrian.novegil@gmail.com>
 */
public class MemInfo {

    private static final Runtime runtime = Runtime.getRuntime();

    public static long getTotalMemory() {
        return runtime.totalMemory();
    }

    public static long getMaxMemBytes() {
        return runtime.maxMemory();
    }

    public static long getUsedMemBytes() {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static long getFreeMemBytes() {
        return runtime.maxMemory() - getUsedMemBytes();
    }
}
