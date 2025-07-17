package com.example.tennisscoreboard.dao;

import com.example.tennisscoreboard.model.Player;
import com.example.tennisscoreboard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class PlayerDao {

    /**
     * Creates a new player in the database and returns the generated ID
     *
     * @param player the player to create
     * @return ID of the created player, or null if an error occurred
     */
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

    /**
     * Retrieves a player by name
     *
     * @param name the name of the player
     * @return Optional containing the player or empty if not found
     */
    public Optional<Player> getPlayerByName(String name) {
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

    /**
     * Retrieves a player by ID
     *
     * @param id the ID of the player
     * @return Optional containing the player or empty if not found
     */
    public Optional<Player> getPlayerById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Player player = session.get(Player.class, id);
            return Optional.ofNullable(player);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Retrieves all players from the database
     *
     * @return list of all players
     */
    public List<Player> getAllPlayers() {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            List<Player> players = session.createQuery("from Player", Player.class).list();
            tx.commit();

            return players;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to retrieve players", e);
        }
    }

    /**
     * Updates player data in the database
     *
     * @param player the player to update
     * @return true if update was successful, false otherwise
     */
    public boolean updatePlayer(Player player) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(player);
            tx.commit();

            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a player from the database
     *
     * @param player the player to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deletePlayer(Player player) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(player);
            tx.commit();

            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}
