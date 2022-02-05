package javalib.utils;

/**
 *
 * @author leo
 */
public class SystemUtil {

    public static long getPID() {
        String processName =
                java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        return Long.parseLong(processName.split("@")[0]);
    }
}
