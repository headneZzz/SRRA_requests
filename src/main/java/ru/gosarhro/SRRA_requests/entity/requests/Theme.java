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
@Table(name = "themes")
public class Theme {

    @Transient
    public static final Theme EMPTY_THEME = new Theme(56, "");

    @Id
    @SequenceGenerator(name = "themes_theme_id_seq", sequenceName = "requests.themes_theme_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "themes_theme_id_seq")
    @Column(name = "theme_id")
    private Integer id;

    @Column(name = "theme")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Theme theme = (Theme) o;
        return id != null && Objects.equals(id, theme.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
