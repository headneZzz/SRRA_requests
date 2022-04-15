package ru.gosarhro.SRRA_requests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.gosarhro.SRRA_requests.TestData.RUBRIC_1;
import static ru.gosarhro.SRRA_requests.TestData.RUBRIC_2;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql")
class RubricServiceTest {

    @Autowired
    RubricService service;

    @Test
    void getById() {
        assertEquals(RUBRIC_2, service.getById(2));
    }

    @Test
    void getAll() {
        assertEquals(List.of(RUBRIC_1, RUBRIC_2), service.getAll());
    }
}
