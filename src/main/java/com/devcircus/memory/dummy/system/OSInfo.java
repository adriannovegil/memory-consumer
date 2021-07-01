package com.devcircus.memory.dummy.system;

/**
 *
 * @author Adrian Novegil <adrian.novegil@gmail.com>
 */
public class OSInfo {

    private static final Runtime runtime = Runtime.getRuntime();

    public static String osName() {
        return System.getProperty("os.name");
    }

    public static String osVersion() {
        return System.getProperty("os.version");
    }

    public static String osArch() {
        return System.getProperty("os.arch");
    }

    public static String getAvailableProcessors() {
        return String.valueOf(runtime.availableProcessors());
    }

    public static double getTotalPhysicalMemorySize() {
        com.sun.management.OperatingSystemMXBean bean
                = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();

        return (double) bean.getTotalPhysicalMemorySize();
    }

}
