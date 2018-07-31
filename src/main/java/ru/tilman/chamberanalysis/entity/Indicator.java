package ru.tilman.chamberanalysis.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "indicators")
public class Indicator implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "indicator_id_generator")
    @SequenceGenerator(name = "indicator_id_generator", initialValue = 63)
    private Long id;


    @Column(name = "name")
    private String name;

    @Column(name = "other_name")
    private String otherName;

    @ManyToOne
    @JoinColumn(name = "indicators_type_id")
    private IndicatorType type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public IndicatorType getType() {
        return type;
    }

    public void setType(IndicatorType type) {
        this.type = type;
    }


}
