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
@Table(name = "sources")
public class Source {

    @Transient
    public static final Source EMPTY_SOURCE = new Source(27, "");

    @Id
    @SequenceGenerator(name = "sources_source_id_seq", sequenceName = "requests.sources_source_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sources_source_id_seq")
    @Column(name = "source_id")
    private Integer id;

    @Column(name = "sources")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Source source = (Source) o;
        return id != null && Objects.equals(id, source.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
