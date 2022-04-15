package ru.gosarhro.SRRA_requests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.gosarhro.SRRA_requests.TestData.DIG;
import static ru.gosarhro.SRRA_requests.TestData.THROW;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql")
class WorkTypeServiceTest {

    @Autowired
    private WorkTypeService service;

    @Test
    void getById() {
        assertEquals(THROW, service.getById(2));
    }

    @Test
    void getAll() {
        assertEquals(List.of(DIG, THROW), service.getAll());
    }
}
