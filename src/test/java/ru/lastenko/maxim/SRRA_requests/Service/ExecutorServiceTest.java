package ru.lastenko.maxim.SRRA_requests.Service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lastenko.maxim.SRRA_requests.entity.Executor;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql")
public class ExecutorServiceTest {

    public static final Executor EXECUTOR_A = new Executor(2, "ИсполнительА", true, "ДолжностьА", "111-11-11", "executorA@gosarhro.ru");
    public static final Executor EXECUTOR_B = new Executor(1, "ИсполнительБ", true, "ДолжностьБ", "222-22-22", "executorB@gosarhro.ru");
    public static final Executor EXECUTOR_V = new Executor(3, "ИсполнительВ", false, "ДолжностьВ", "333-33-33", "executorV@gosarhro.ru");

    @Autowired
    private ExecutorService service;

    @Test
    public void save() {
        Executor newExecutor = new Executor(null, "Тест", false, "Тест", "000-00-00", "test@gosarhro.ru");
        Executor addedExecutor = service.save(newExecutor);
        newExecutor.setId(addedExecutor.getId());
        Assert.assertEquals(List.of(EXECUTOR_A, EXECUTOR_B, EXECUTOR_V, newExecutor), service.getAll());
    }

    @Test
    public void getById() {
        Assert.assertEquals(EXECUTOR_A, service.getById(2));
    }

    @Test
    public void getAll() {
        Assert.assertEquals(List.of(EXECUTOR_A, EXECUTOR_B, EXECUTOR_V), service.getAll());
    }

    @Test
    public void deleteById() {
        service.deleteById(2);
        Assert.assertEquals(null, service.getById(2));
        Assert.assertEquals(List.of(EXECUTOR_B, EXECUTOR_V), service.getAll());
    }
}