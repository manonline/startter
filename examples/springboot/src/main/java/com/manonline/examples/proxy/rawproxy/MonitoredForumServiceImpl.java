package com.manonline.examples.proxy.rawproxy;

import com.manonline.examples.proxy.rawproxy.aspect.PerformanceMonitor;

/**
 * Created by davidqi on 2/13/17.
 * 当某个方法需要进行性能监视，就必须调整方法代码，在方法体前后分别添加上开启性能监视和结束性能监视的代码。这些非业务逻辑的性能监视代码破坏
 * 了ForumServiceImpl业务逻辑的纯粹性。我们希望通过代理的方式，将业务类方法中开启和结束性能监视的这些横切代码从业务类中完全移除。
 */
public class MonitoredForumServiceImpl implements ForumService {
    public void removeTopic(int topicId) {

        // 开始对该方法进行性能监视
        PerformanceMonitor.begin("com.manonline.examples.proxy.dynamic.ForumServiceImpl.removeTopic");
        System.out.println("模拟删除Topic记录:" + topicId);
        try {
            Thread.currentThread().sleep(20);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 结束对该方法进行性能监视
        PerformanceMonitor.end();
    }

    public void removeForum(int forumId) {
        //②-1开始对该方法进行性能监视
        PerformanceMonitor.begin("com.manonline.examples.proxy.dynamic.ForumServiceImpl.removeForum");
        System.out.println("模拟删除Forum记录:" + forumId);
        try {
            Thread.currentThread().sleep(40);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 结束对该方法进行性能监视
        PerformanceMonitor.end();
    }
}
