package com.soen343.shs.dal.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, name = "ROOM_NAME")
    private String name;

    private double temperature;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Set<Door> doors;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Light> lights;

    @ElementCollection
    private Set<Long> userIds;
    
    private Long houseId;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<HouseWindow> houseWindows;

}
