package com.devcircus.memory.dummy;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.Vector;

/**
 *
 * @author Adrian Novegil <adrian.novegil@gmail.com>
 */
public class Main {

    private static float CAP = 0.8f;  // 80%
    private static int ONE_MB = 1024 * 1024;

    private static Vector cache = new Vector<>();

    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();

        long maxMemBytes = rt.maxMemory();
        long usedMemBytes = rt.totalMemory() - rt.freeMemory();
        long freeMemBytes = rt.maxMemory() - usedMemBytes;

        int allocBytes = Math.round(freeMemBytes * CAP);

        System.out.println("Initial free memory: " + freeMemBytes / ONE_MB + "MB");
        System.out.println("Max memory: " + maxMemBytes / ONE_MB + "MB");
        System.out.println("Reserve: " + allocBytes / ONE_MB + "MB");

        for (int i = 0; i < allocBytes / ONE_MB; i++) {
            cache.add(new byte[ONE_MB]);
        }

        usedMemBytes = rt.totalMemory() - rt.freeMemory();
        freeMemBytes = rt.maxMemory() - usedMemBytes;

        System.out.println("Free memory: " + freeMemBytes / ONE_MB + "MB");

        // =====================================================================
        // From JVM using MXBean
        // =====================================================================
        
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        System.out.println();
        System.out.println(String.format("Initial memory: %.2f GB",
                (double) memoryMXBean.getHeapMemoryUsage().getInit() / 1073741824));
        System.out.println(String.format("Used heap memory: %.2f GB",
                (double) memoryMXBean.getHeapMemoryUsage().getUsed() / 1073741824));
        System.out.println(String.format("Max heap memory: %.2f GB",
                (double) memoryMXBean.getHeapMemoryUsage().getMax() / 1073741824));
        System.out.println(String.format("Committed memory: %.2f GB",
                (double) memoryMXBean.getHeapMemoryUsage().getCommitted() / 1073741824));

        // =====================================================================        
        // From OS
        // =====================================================================
        
        com.sun.management.OperatingSystemMXBean bean
                = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();
        
        long max = bean.getTotalPhysicalMemorySize();
        
        System.out.println();
        System.out.println(String.format("OS memory: %.2f GB",
                (double) max / 1073741824));
        
    }

}
