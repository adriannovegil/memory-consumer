package com.devcircus.memory.dummy;

import com.devcircus.memory.dummy.system.MemInfo;
import com.devcircus.memory.dummy.system.OSInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.Vector;

/**
 *
 * @author Adrian Novegil <adrian.novegil@gmail.com>
 */
public class Main {

    private static final int NUM_EXPERIMENTS = 10;
    private static final float CAP = 0.8f;  // 80% 
    private static final int ONE_MB = 1024 * 1024;
    private static final int ONE_GB = ONE_MB * 1024;
    private static final boolean FORCE_SYSTEM_GC = true;

    private static final Vector cache = new Vector<>();

    public static void main(String[] args) {

        printJVMMemInfo();

        long freeMemBytes = MemInfo.getFreeMemBytes();

        int allocBytes = Math.round(freeMemBytes * CAP);
        System.out.println(" Reserved memory: " + allocBytes / ONE_MB + "MB");

        System.out.println(" Start execution ... ");
        for (int i = 0; i < NUM_EXPERIMENTS; i++) {
            System.out.println("  Expetiment: " + i);
            for (int j = 0; j < allocBytes / ONE_MB; j++) {
                cache.add(new byte[ONE_MB]);
            }
            cache.clear();
            if (FORCE_SYSTEM_GC) {
                System.gc();
            }
            System.out.println("  Free memory..: " + MemInfo.getFreeMemBytes() / ONE_MB + "MB");
        }
        System.out.println(" Execution finished without errors ;-)");

        printJVMMemInfo();

    }

    private static void printJVMMemInfo() {
        System.out.println("-----------------------------------");
        System.out.println(" Memory Status");
        System.out.println("-----------------------------------");
        System.out.println(" Max memory...: " + MemInfo.getMaxMemBytes() / ONE_MB + "MB");
        System.out.println(" Used memory..: " + MemInfo.getUsedMemBytes() / ONE_MB + "MB");
        System.out.println(" Free memory..: " + MemInfo.getFreeMemBytes() / ONE_MB + "MB");
        System.out.println();
    }

    private static void printMXBeanJVMMemInfo() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        System.out.println();
        System.out.println(String.format("Initial memory: %.2f GB",
                (double) memoryMXBean.getHeapMemoryUsage().getInit() / ONE_GB));
        System.out.println(String.format("Used heap memory: %.2f GB",
                (double) memoryMXBean.getHeapMemoryUsage().getUsed() / ONE_GB));
        System.out.println(String.format("Max heap memory: %.2f GB",
                (double) memoryMXBean.getHeapMemoryUsage().getMax() / ONE_GB));
        System.out.println(String.format("Committed memory: %.2f GB",
                (double) memoryMXBean.getHeapMemoryUsage().getCommitted() / ONE_GB));
    }

    private static void printOSInfo() {
        System.out.println();
        System.out.println(String.format("OS memory: %.2f GB",
                OSInfo.getTotalPhysicalMemorySize() / ONE_GB));
    }

}
