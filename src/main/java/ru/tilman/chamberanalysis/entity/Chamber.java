package ru.tilman.chamberanalysis.entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "chambers")
public class Chamber implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "chamber_id_generator")
    @SequenceGenerator(name = "chamber_id_generator", initialValue = 204)
    private Long id;


    @Size(min = 4, max = 255, message = "{validation.name.size}")
    @Column(name = "name")
    private String name;


    @ManyToOne
    @JoinColumn(name = "region_id")
    @Valid
    private Region region;

    @Column(name = "address")
    private String address;

    @Column(name = "urlPortal")
    private String urlPortal;

    // TODO: 12.07.18
 /*   @OneToMany
    private List<Indicator> indicators;
    private List<Reports> reportsList;*/


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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chamber chamber = (Chamber) o;

        if (!id.equals(chamber.id)) return false;
        if (!name.equals(chamber.name)) return false;
        if (!region.equals(chamber.region)) return false;
        if (!address.equals(chamber.address)) return false;
        return urlPortal != null ? urlPortal.equals(chamber.urlPortal) : chamber.urlPortal == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + region.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + (urlPortal != null ? urlPortal.hashCode() : 0);
        return result;
    }
}
