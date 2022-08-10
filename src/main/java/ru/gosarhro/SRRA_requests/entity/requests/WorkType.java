package ru.gosarhro.SRRA_requests.entity.requests;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
@Table(name = "prices")
public class WorkType {

    @Id
    @SequenceGenerator(name = "prices_price_id_seq", sequenceName = "requests.prices_price_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prices_price_id_seq")
    @Column(name = "price_id")
    private Integer id;

    @Column(name = "work_type")
    private String name;

    @Column(name = "unit")
    private String unit;

    @Column(name = "price")
    private BigDecimal price;

    public WorkType() {
    }

    public WorkType(int id, String name, String unit, String price) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.price = (new BigDecimal(price)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkType workType = (WorkType) o;
        return id.equals(workType.id) &&
                name.equals(workType.name) &&
                unit.equals(workType.unit) &&
                price.equals(workType.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, unit, price);
    }
}
