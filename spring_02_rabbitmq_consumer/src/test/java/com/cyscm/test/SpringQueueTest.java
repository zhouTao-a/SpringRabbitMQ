package com.cyscm.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-consumer.xml")
public class SpringQueueTest {

    @Test
    public void test1(){
        testDriver();
        boolean flag = true;
        while (flag){

        }
    }

    @Test
    public void testDriver(){
        assertFalse(false);
    }
}
