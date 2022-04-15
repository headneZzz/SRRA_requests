package ru.gosarhro.SRRA_requests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.gosarhro.SRRA_requests.TestData.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql")
class PaymentServiceTest {

    @Autowired
    private PaymentService service;

    @Test
    void getById() {
        assertEquals(FREE_PAYMENT, service.getById(3));
    }

    @Test
    void getAll() {
        assertEquals(List.of(PAID_PAYMENT, PARTIALLY_PAID_PAYMENT, FREE_PAYMENT), service.getAll());
    }
}
