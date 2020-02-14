package ru.lastenko.maxim.SRRA_requests.dto;

import lombok.*;
import ru.lastenko.maxim.SRRA_requests.entity.executor.Executor;
import ru.lastenko.maxim.SRRA_requests.entity.payment.Payment;
import ru.lastenko.maxim.SRRA_requests.entity.rubric.Rubric;
import ru.lastenko.maxim.SRRA_requests.entity.source.Source;
import ru.lastenko.maxim.SRRA_requests.entity.theme.Theme;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestDto {
    private Integer id;
    private Rubric rubric;
    private Theme theme;
    private String subject;
    private String shortRequest;
    private String shortAnswer;
    private Source source;
    private boolean urgent;
    private boolean GPW;
    private boolean entity;
    private boolean consular;
    private Executor receiver;
    private LocalDate receiptDate;
    private LocalDate startDate;
    private Executor executor;
    private LocalDate endDate;
    private Integer smav;
    private Integer outNumber;
    private String inNumFromOrg;
    private LocalDate inDate;
    private Integer copyNumber;
    private Payment payment;
    private boolean changeable;
    private boolean expired;
    private boolean executed;
    private Long daysLeft;
}
