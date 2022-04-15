package ru.gosarhro.SRRA_requests.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.gosarhro.SRRA_requests.dto.RequestDto;
import ru.gosarhro.SRRA_requests.entity.requests.*;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
@RequiredArgsConstructor
public class RequestMapper {

    private final ModelMapper modelMapper;

    public RequestDto convertToDto(Request request) {
        RequestDto requestDto = modelMapper.map(request, RequestDto.class);
        if (request.getRubric() == null) {
            requestDto.setRubric(Rubric.EMPTY_RUBRIC);
        }
        if (request.getTheme() == null) {
            requestDto.setTheme(Theme.EMPTY_THEME);
        }
        if (request.getSource() == null) {
            requestDto.setSource(Source.EMPTY_SOURCE);
        }
        if (request.getExecutor() == null) {
            requestDto.setExecutor(Executor.EMPTY_EXECUTOR);
        }
        if (request.getPayment() == null) {
            requestDto.setPayment(Payment.EMPTY_PAYMENT);
        }
        requestDto.setChangeable(true);
        if (request.getEndDate() != null
                && LocalDate.now().isAfter(request.getEndDate().plusDays(10))) {
            requestDto.setChangeable(false);
        }
        if (request.getReceiptDate() != null) {
            Long daysLeft = DAYS.between(LocalDate.now(), request.getReceiptDate()) + 30;
            requestDto.setDaysLeft(daysLeft);
            requestDto.setExpired(daysLeft <= 0);
        }
        return requestDto;
    }
}
