package com.manonline.examples.proxy.aspect;

/**
 * Created by davidqi on 2/13/17.
 */
public class PerformanceMonitor {
    // 通过一个ThreadLocal保存调用线程相关的性能监视信息
    private static ThreadLocal<MethodPerformance> performanceRecord = new ThreadLocal<MethodPerformance>();

    // 启动对某一目标方法的性能监视
    public static void begin(String method) {
        System.out.println("begin monitor...");
        performanceRecord.set(new MethodPerformance(method));
    }

    // 终止监控，并打印结果
    public static void end() {
        System.out.println("end monitor...");
        MethodPerformance mp = performanceRecord.get();
        // 打印出方法性能监视的结果信息。
        mp.printPerformance();
    }
}