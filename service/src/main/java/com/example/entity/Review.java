package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String text;

    private Integer grade;

    @ManyToOne(cascade = CascadeType.ALL)
    private Movie movie;

    @OneToOne
    private User user;

    public void setUser(User user){
        user.setReview(this);
        this.user = user;
    }
}
