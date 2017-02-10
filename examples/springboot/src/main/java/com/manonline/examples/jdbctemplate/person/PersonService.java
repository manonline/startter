package com.manonline.examples.jdbctemplate.person;

/**
 * Created by davidqi on 2/10/17.
 */

import java.util.List;

public interface PersonService {

    public void addPerson(PersonBean person);

    public void updatePerson(PersonBean person);

    public void deletePerson(int id);

    public PersonBean queryPerson(int id);

    public List<PersonBean> queryPersons();
}
