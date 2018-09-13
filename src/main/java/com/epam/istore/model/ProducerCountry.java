package com.epam.istore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "producer_country")
@Setter
@Getter
public class ProducerCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "country_name")
    private String countryName;

    @OneToMany(mappedBy = "producerCountry")
    private Set<Product> products;
}
