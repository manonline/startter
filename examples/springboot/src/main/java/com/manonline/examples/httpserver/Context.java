package com.manonline.examples.httpserver;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.manonline.examples.httpserver.core.impl.HttpHandler;
import com.manonline.examples.httpserver.utils.XmlUtils;

/**
 * Created by davidqi on 1/23/17.
 */
public class Context {

    private static Map<String, HttpHandler> contextMap = new HashMap<String, HttpHandler>();
    public static String contextPath = "";

    public static void load() {
        try {
            Document doc = XmlUtils.load(Context.class.getResource("/").getPath() + "/httpserver/context.xml");
            Element root = doc.getDocumentElement();
            contextPath = XmlUtils.getAttribute(root, "context");
            Element[] handlers = XmlUtils.getChildrenByName(root, "handler");

            for (Element ele : handlers) {
                String handle_class = XmlUtils.getChildText(ele, "handler-class");
                String url_pattern = XmlUtils.getChildText(ele, "url-pattern");
                /**
                 * Note : create handler objects that is specified in context.xml
                 */
                // load class object based on class name
                Class<?> cls = Class.forName(handle_class);
                // create instance based on object class
                Object newInstance = cls.newInstance();
                // put the instance into contextMap, so that the instance can be retrieved when uri matches
                if (newInstance instanceof HttpHandler) {
                    contextMap.put(contextPath + url_pattern, (HttpHandler) newInstance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpHandler getHandler(String key) {
        return contextMap.get(key);
    }

}