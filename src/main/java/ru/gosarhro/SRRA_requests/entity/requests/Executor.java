package ru.gosarhro.SRRA_requests.entity.requests;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "executors")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Executor {

    @Transient
    public static final Executor EMPTY_EXECUTOR = new Executor(35,"",false,"","","");

    @Id
    @SequenceGenerator(name = "executors_executor_id_seq", sequenceName = "requests.executors_executor_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "executors_executor_id_seq")
    @Column(name = "executor_id")
    private Integer id;

    @Column(name = "executor")
    private String name;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "job")
    private String job;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "`e-mail`")
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Executor executor = (Executor) o;
        return id != null && Objects.equals(id, executor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
