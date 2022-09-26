package com.example.entity;

import com.example.converter.BirthdayConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "id")
@Builder
@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    @Convert(converter = BirthdayConverter.class)
    @Column(name = "age")
    private Birthday birthdate;

    @ManyToOne()
    private Movie movie;

}
