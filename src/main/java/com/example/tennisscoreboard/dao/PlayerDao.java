package com.example.tennisscoreboard.dao;

import com.example.tennisscoreboard.model.Player;
import com.example.tennisscoreboard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class PlayerDao {


    public Integer createPlayer(Player player) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(player);
            tx.commit();

            return player.getId();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to create player", e);
        }
    }


    public Optional<Player> findPlayerByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Player player = session.createQuery("from Player where name = :name", Player.class)
                    .setParameter("name", name)
                    .uniqueResult();
            return Optional.ofNullable(player);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


}
