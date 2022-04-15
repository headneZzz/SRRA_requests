package ru.gosarhro.SRRA_requests.entity.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rubric_id")
    private Rubric rubric;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @Column(name = "subject")
    private String subject;

    @Column(name = "short_request")
    private String shortRequest;

    @Column(name = "short_answer")
    private String shortAnswer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id")
    private Source source;

    @Column(name = "is_urgent")
    private boolean urgent;

    @Column(name = "is_gpw")
    private boolean GPW;

    @Column(name = "is_entity")
    private boolean entity;

    @Column(name = "is_consular")
    private boolean consular;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver")
    private Executor receiver;

    @Column(name = "receipt_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate receiptDate;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "executor_id")
    private Executor executor;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "smav")
    private Integer smav;

    @Column(name = "out_number")
    private Integer outNumber;

    @Column(name = "in_num_from_org")
    private String inNumFromOrg;

    @Column(name = "in_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inDate;

    @Column(name = "copy_number")
    private Integer copyNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public Request(
            Integer id,
            Rubric rubric,
            Theme theme,
            String subject,
            String shortRequest,
            String shortAnswer,
            Source source,
            boolean urgent,
            boolean GPW,
            boolean entity,
            boolean consular,
            Executor receiver,
            LocalDate receiptDate,
            Executor workingBy,
            LocalDate startDate,
            Executor executor,
            LocalDate endDate,
            Integer smav,
            Integer outNumber,
            String regNumber,
            String inNumFromOrg,
            LocalDate inDate,
            Integer copyNumber,
            Payment payment
    ) {
        this.id = id;
        this.rubric = rubric;
        this.theme = theme;
        this.subject = subject;
        this.shortRequest = shortRequest;
        this.shortAnswer = shortAnswer;
        this.source = source;
        this.urgent = urgent;
        this.GPW = GPW;
        this.entity = entity;
        this.consular = consular;
        this.receiver = receiver;
        this.receiptDate = receiptDate;
        this.startDate = startDate;
        this.executor = executor;
        this.endDate = endDate;
        this.smav = smav;
        this.outNumber = outNumber;
        this.inNumFromOrg = inNumFromOrg;
        this.inDate = inDate;
        this.copyNumber = copyNumber;
        this.payment = payment;
    }

    public Executor getReceiver() {
        return receiver != null ? receiver : Executor.EMPTY_EXECUTOR;
    }

    public Executor getExecutor() {
        return executor != null ? executor : Executor.EMPTY_EXECUTOR;
    }


    public String getEndDateFormated() {
        return endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    public LocalDate getInDate() {
        return inDate;
    }

    public String getInDateFormated() {
        return inDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public Payment getPayment() {
        return payment != null ? payment : Payment.EMPTY_PAYMENT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Request request = (Request) o;
        return urgent == request.urgent &&
                GPW == request.GPW &&
                entity == request.entity &&
                consular == request.consular &&
                id.equals(request.id) &&
                Objects.equals(rubric, request.rubric) &&
                Objects.equals(theme, request.theme) &&
                Objects.equals(subject, request.subject) &&
                Objects.equals(shortRequest, request.shortRequest) &&
                Objects.equals(shortAnswer, request.shortAnswer) &&
                Objects.equals(source, request.source) &&
                Objects.equals(receiver, request.receiver) &&
                Objects.equals(receiptDate, request.receiptDate) &&
                Objects.equals(startDate, request.startDate) &&
                Objects.equals(executor, request.executor) &&
                Objects.equals(endDate, request.endDate) &&
                Objects.equals(smav, request.smav) &&
                Objects.equals(outNumber, request.outNumber) &&
                Objects.equals(inNumFromOrg, request.inNumFromOrg) &&
                Objects.equals(inDate, request.inDate) &&
                Objects.equals(copyNumber, request.copyNumber) &&
                Objects.equals(payment, request.payment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rubric, theme, subject, shortRequest, shortAnswer, source, urgent, GPW, entity, consular, receiver, receiptDate, startDate, executor, endDate, smav, outNumber, inNumFromOrg, inDate, copyNumber, payment);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", rubric=" + rubric +
                ", theme=" + theme +
                ", subject='" + subject + '\'' +
                ", shortRequest='" + shortRequest + '\'' +
                ", shortAnswer='" + shortAnswer + '\'' +
                ", source=" + source +
                ", urgent=" + urgent +
                ", GPW=" + GPW +
                ", entity=" + entity +
                ", consular=" + consular +
                ", receiver=" + receiver +
                ", receiptDate=" + receiptDate +
                ", startDate=" + startDate +
                ", executor=" + executor +
                ", endDate=" + endDate +
                ", smav=" + smav +
                ", outNumber=" + outNumber +
                ", inNumFromOrg='" + inNumFromOrg + '\'' +
                ", inDate=" + inDate +
                ", copyNumber=" + copyNumber +
                ", payment=" + payment +
                '}';
    }
}
