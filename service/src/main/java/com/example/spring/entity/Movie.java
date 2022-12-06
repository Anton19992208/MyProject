package com.example.spring.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"movie", "review", "actor"})
@EqualsAndHashCode(of = "name")
@Entity
public class Movie implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "movie")
    private List<MovieActor> movieActors = new ArrayList<>();

    private String producer;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private String country;

    private String image;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Builder.Default
    @OneToMany(mappedBy = "movie")
    private List<Review> reviews = new ArrayList<>();

    public String name() {
        return name;
    }


}
