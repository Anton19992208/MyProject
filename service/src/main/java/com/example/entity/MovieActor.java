package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;
import java.time.LocalDate;

@NamedEntityGraph(
        name = "MovieWithActor",
        attributeNodes = {
                @NamedAttributeNode(value = "movie", subgraph = "reviews"),
                @NamedAttributeNode("actor")
        },
        subgraphs = {
                @NamedSubgraph(name = "reviews", attributeNodes = @NamedAttributeNode("reviews"))
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"movie", "actor"})
@EqualsAndHashCode(of = "movie")
@Builder
@Entity
public class MovieActor implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    private Actor actor;

    private LocalDate createdAt;

    private String createdBy;

    public void setActor(Actor actor) {
        this.actor = actor;
        this.actor.getMovieActors().add(this);
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        this.movie.getMovieActors().add(this);
    }

}
