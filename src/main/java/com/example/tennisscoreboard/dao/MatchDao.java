package com.example.tennisscoreboard.dao;

import com.example.tennisscoreboard.model.Match;
import com.example.tennisscoreboard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class MatchDao {

    /**
     * Saves a new match to the database
     *
     * @param match the match to save
     */
    public void save(Match match) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(match);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new RuntimeException("Failed to save match", e);
        }
    }

    /**
     * Retrieves a match by ID
     *
     * @param id match identifier
     * @return Optional containing Match or empty if not found
     */
    public Optional<Match> getMatchById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Match.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Retrieves all matches from the database
     *
     * @return list of all matches
     */
    public List<Match> getAllMatches() {
        List<Match> matches = null;
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            matches = session.createQuery("from Match", Match.class).list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }

        return matches;
    }

    /**
     * Updates a match in the database
     *
     * @param match the match to update
     */
    public void updateMatch(Match match) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(match);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Deletes a match from the database
     *
     * @param match the match to delete
     */
    public void deleteMatch(Match match) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(match);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Searches matches where the player's name matches the given string
     *
     * @param name player name to search for
     * @return list of matching matches
     */
    public List<Match> findMatchesByPlayerName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
            from Match m
            where lower(m.player1.name) like lower(:name)
               or lower(m.player2.name) like lower(:name)
               or lower(m.winner.name) like lower(:name)
            """, Match.class)
                    .setParameter("name", "%" + name + "%")
                    .list();
        }
    }

    /**
     * Retrieves a paginated list of matches
     *
     * @param pageNumber the current page number (starting from 1)
     * @param pageSize the number of items per page
     * @return list of matches for the given page
     */
    public List<Match> findMatchesPaged(int pageNumber, int pageSize) {
        int firstResult = (pageNumber - 1) * pageSize;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Match ORDER BY id DESC", Match.class)
                    .setFirstResult(firstResult)
                    .setMaxResults(pageSize)
                    .list();
        }
    }

    /**
     * Returns the total number of matches in the database
     *
     * @return match count
     */
    public long countAllMatches() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select count(m) from Match m", Long.class)
                    .uniqueResult();
        }
    }

    /**
     * Returns the total number of matches that match the given player name
     *
     * @param playerName name of the player to filter by
     * @return count of matching matches
     */
    public long countMatchesByPlayerName(String playerName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
            SELECT COUNT(m) FROM Match m
            WHERE LOWER(m.player1.name) LIKE LOWER(CONCAT('%', :name, '%'))
               OR LOWER(m.player2.name) LIKE LOWER(CONCAT('%', :name, '%'))
        """;
            return session.createQuery(hql, Long.class)
                    .setParameter("name", playerName)
                    .uniqueResult();
        }
    }

    /**
     * Retrieves a paginated list of matches filtered by player name
     *
     * @param playerName player name to filter by
     * @param page current page number (starting from 1)
     * @param pageSize number of matches per page
     * @return list of matching matches for the current page
     */
    public List<Match> findMatchesByPlayerNamePaged(String playerName, int page, int pageSize) {
        int firstResult = (page - 1) * pageSize;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
            FROM Match m
            WHERE LOWER(m.player1.name) LIKE LOWER(CONCAT('%', :name, '%'))
               OR LOWER(m.player2.name) LIKE LOWER(CONCAT('%', :name, '%'))
            ORDER BY m.id DESC
        """;
            return session.createQuery(hql, Match.class)
                    .setParameter("name", playerName)
                    .setFirstResult(firstResult)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
    }
}
