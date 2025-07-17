package com.example.tennisscoreboard.model;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {


    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
@Column(name = "player_name", nullable = false,unique = true)
    private String name;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
