package com.manonline.examples.proxy;

import com.manonline.examples.proxy.aspect.PerformanceMonitor;

/**
 * Created by davidqi on 2/13/17.
 */
public class StaticProxyForumServiceImpl implements ForumService {

    private ForumService rawForumService;

    public StaticProxyForumServiceImpl(ForumService rawForumService) {
        this.rawForumService = rawForumService;
    }

    @Override
    public void removeTopic(int topicId) {
        PerformanceMonitor.begin("com.manonline.examples.proxy.dynamic.ForumServiceImpl.removeTopic");

        // 调用原方法
        rawForumService.removeTopic(topicId);

        PerformanceMonitor.end();

    }

    @Override
    public void removeForum(int forumId) {
        PerformanceMonitor.begin("com.manonline.examples.proxy.dynamic.ForumServiceImpl.removeTopic");

        // 调用原方法
        rawForumService.removeTopic(forumId);

        PerformanceMonitor.end();
    }
}
