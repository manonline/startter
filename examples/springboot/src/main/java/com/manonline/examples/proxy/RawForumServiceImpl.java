package com.manonline.examples.proxy;

/**
 * Created by davidqi on 2/13/17.
 */
public class RawForumServiceImpl implements ForumService {
    public void removeTopic(int topicId) {
        System.out.println("模拟删除Topic记录:" + topicId);
        try {
            Thread.currentThread().sleep(20);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeForum(int forumId) {
        System.out.println("模拟删除Forum记录:" + forumId);
        try {
            Thread.currentThread().sleep(40);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
