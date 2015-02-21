package br.com.supercloud.cms.test.model;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.supercloud.cms.model.Person;


public class PersonTest {
    @Test
    public void canConstructAPersonWithAName() {
        Person person = new Person("Larry");
        assertEquals("Larry", person.getName());
    }
}
