package com.manonline.examples.javabasic.override;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by davidqi on 2/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OverrideTest {

    @Test
    public void staticPublicOverride() {
        Child child = new Child();
        Parent parent = child;

        System.out.println("=============Calling from Child ...");
        System.out.println(child.publicStaticMethod());
        System.out.println(child.method1());
        System.out.println("Final Method Cannot be Override" + child.publicStaticFinalMethod());
        System.out.println("Static Value : " + child.publicStaticValue);
        System.out.println("Static Final Value : " + child.publicStaticFinalValue);

        System.out.println("=============Calling from Parent ...");
        System.out.println(parent.publicStaticMethod());
        // doesn't work ....
        // System.out.println((Child)parent.method1());
        System.out.println("Final Method Cannot be Override" + parent.publicStaticFinalMethod());
        System.out.println("Static Value : " + parent.publicStaticValue);
        System.out.println("Static Final Value : " + parent.publicStaticFinalValue);
    }


    @Test
    public void classTest() {
        ParentClass p = new ParentClass();
        ChildClass c = new ChildClass();

        System.out.println("ParentClass object outputs:");
        System.out.println(p.getMsg());
        System.out.println("");
        System.out.println("ChildrenClass object outputs:");
        System.out.println(c.getMsg());
    }

    @Test
    public void privateOverride() {
    }
}