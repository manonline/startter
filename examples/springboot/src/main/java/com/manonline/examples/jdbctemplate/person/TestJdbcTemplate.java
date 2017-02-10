package com.manonline.examples.jdbctemplate.person;

/**
 * Created by davidqi on 2/10/17.
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJdbcTemplate {
    public static void main(String[] args) {
        // Create Spring Context
        ApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");
        // Get Bean - Spring Bean
        PersonService ps = (PersonService) ctx.getBean("personService");

        // Create Person
        ps.addPerson(new PersonBean("royzhou"));

        // Display Person
        PersonBean pb = ps.queryPerson(1);
        System.out.println(pb);

        // Update Person
        pb.setName("haha");
        ps.updatePerson(pb);
        pb = ps.queryPerson(1);
        System.out.println(pb);

        // Remove Person
        ps.deletePerson(1);
        pb = ps.queryPerson(1);
        System.out.println(pb);
    }
}