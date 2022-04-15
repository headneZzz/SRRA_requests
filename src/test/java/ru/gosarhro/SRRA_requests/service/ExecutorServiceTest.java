package ru.gosarhro.SRRA_requests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gosarhro.SRRA_requests.entity.requests.Executor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.gosarhro.SRRA_requests.TestData.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql")
class ExecutorServiceTest {

    @Autowired
    private ExecutorService service;

    @Test
    void save() {
        Executor newExecutor = new Executor(null, "Тест", false, "Тест", "000-00-00", "test@gosarhro.ru");
        Executor addedExecutor = service.save(newExecutor);
        newExecutor.setId(addedExecutor.getId());
        assertEquals(List.of(EXECUTOR_A, EXECUTOR_B, EXECUTOR_V, newExecutor), service.getAll());
    }

    @Test
    void getById() {
        assertEquals(EXECUTOR_A, service.getById(2));
    }

    @Test
    void getAll() {
        assertEquals(List.of(EXECUTOR_A, EXECUTOR_B, EXECUTOR_V), service.getAll());
    }
}
