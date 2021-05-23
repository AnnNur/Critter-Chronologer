package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Nationalized
    private String name;
    private String phoneNumber;

    @Column(length = 512)
    private String notes;

    @OneToMany(targetEntity = Pet.class, mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> pets;
}

