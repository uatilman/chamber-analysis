package ru.tilman.chamberanalysis.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "indicators_type")
public class IndicatorType implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "indicators_type_id_generator")
    @SequenceGenerator(name = "indicators_type_id_generator", initialValue = 3)
    private Long id;

    @Column(name = "type")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
