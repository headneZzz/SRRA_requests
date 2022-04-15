package ru.gosarhro.SRRA_requests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gosarhro.SRRA_requests.entity.requests.Request;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.gosarhro.SRRA_requests.TestData.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql")
class RequestServiceTest {


    @Autowired
    private RequestService service;

    @Test
    void getById() {
        assertEquals(REQUEST4, service.getById(4));
    }

    @Test
    void getAll() {
        List<Request> expectedRequests = List.of(
                REQUEST1,
                REQUEST2,
                REQUEST3,
                REQUEST4,
                REQUEST5,
                REQUEST6,
                REQUEST7,
                REQUEST8
        );
        assertEquals(expectedRequests, service.getAll());
    }

//    @Test
//    void getAllOrderedByIdDesc() {
//        List<Request> expectedRequests = List.of(
//                REQUEST8,
//                REQUEST7,
//                REQUEST6,
//                REQUEST5,
//                REQUEST4,
//                REQUEST3,
//                REQUEST2,
//                REQUEST1
//        );
//
//        assertEquals(expectedRequests, service.getAllOrderedByIdDesc());
//    }
}
