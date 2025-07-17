package com.example.tennisscoreboard.model;

import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "player1")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2")
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "winner")
    private Player winner;

    public Match() {
    }

    public Match(Player player1, Player player2, Player winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }



    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", player1=" + (player1 != null ? player1.getName() : null) +
                ", player2=" + (player2 != null ? player2.getName() : null) +
                ", winner=" + (winner != null ? winner.getName() : null) +
                '}';
    }
}
