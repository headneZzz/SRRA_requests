package ru.gosarhro.SRRA_requests.entity.personal_data;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "personal_data", schema = "public")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class PersonalData {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "initiator")
    private String requestInitiator;

    @Column(name = "shipment")
    private String shipment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PersonalData that = (PersonalData) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
