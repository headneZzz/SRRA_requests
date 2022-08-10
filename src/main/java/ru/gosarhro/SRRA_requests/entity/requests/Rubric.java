package ru.gosarhro.SRRA_requests.entity.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rubrics")
public class Rubric {

    @Transient
    public static final Rubric EMPTY_RUBRIC = new Rubric(29, "", "");

    @Id
    @SequenceGenerator(name = "rubrics_rubric_id_seq", sequenceName = "requests.rubrics_rubric_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rubrics_rubric_id_seq")
    @Column(name = "rubric_id")
    private Integer id;

    @Column(name = "rubric_code")
    private String rubric;

    @Column(name = "rubric")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Rubric rubric = (Rubric) o;
        return id != null && Objects.equals(id, rubric.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
