package com.example.entity;

import com.example.converter.ReleaseConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Actor> actors = new ArrayList<>();

    private String producer;

    @Convert(converter = ReleaseConverter.class)
    @Column(name = "release_date")
    private ReleaseDate releaseDate;

    private String country;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews;

    public void addActor(Actor actor){
        actors.add(actor);
        actor.setMovie(this);
    }
}
