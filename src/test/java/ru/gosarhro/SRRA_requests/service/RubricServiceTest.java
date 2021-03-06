package ru.gosarhro.SRRA_requests.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static ru.gosarhro.SRRA_requests.TestData.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql")
public class RubricServiceTest {

    @Autowired
    RubricService service;

    @Test
    public void getById() {
        Assert.assertEquals(RUBRIC_2, service.getById(2));
    }

    @Test
    public void getAll() {
        Assert.assertEquals(List.of(RUBRIC_1, RUBRIC_2), service.getAll());
    }
}