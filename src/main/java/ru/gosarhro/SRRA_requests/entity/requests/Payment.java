package ru.gosarhro.SRRA_requests.entity.requests;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Transient
    public static final Payment EMPTY_PAYMENT = new Payment(4, "");

    @Id
    @SequenceGenerator(name = "payments_payment_id_seq", sequenceName = "requests.payments_payment_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payments_payment_id_seq")
    @Column(name = "payment_id")
    private Integer id;

    @Column(name = "payment")
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id.equals(payment.id) &&
                type.equals(payment.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
