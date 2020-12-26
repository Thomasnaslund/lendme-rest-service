package com.tomstry.LendMeApi;

import com.tomstry.LendMeApi.entities.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LendMeApiApplicationTests {

    @Test
    void contextLoads() {

    }


   /* @Test
    void addPersonSuccews() {
        Person person = new Person();
        Assertions.assertDoesNotThrow();
    }
*/
    @Test
    void testAddSum() {
        Person person = new Person();
        Person person2 = new Person();
        person.setFullName("John");
        person.setEmail("danne@sten.com");
        person.setId(1);
        person2.setFullName("John");
        person2.setEmail("dane@sten.com");
        person2.setId(1);
        Assertions.assertNotEquals(person.getEmail(), person2.getEmail());
        Assertions.assertEquals(person.getId(), person2.getId());
    }

}
