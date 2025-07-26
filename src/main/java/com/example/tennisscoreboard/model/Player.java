package com.example.tennisscoreboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "players")
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "player_name", nullable = false, unique = true)
    private String name;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }


}
