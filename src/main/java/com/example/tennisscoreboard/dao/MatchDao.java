package com.example.tennisscoreboard.dao;

import com.example.tennisscoreboard.model.Match;
import com.example.tennisscoreboard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class MatchDao {

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


    public List<Match> findMatchesPaged(int pageNumber, int pageSize) {
        int firstResult = (pageNumber - 1) * pageSize;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Match ORDER BY id DESC", Match.class)
                    .setFirstResult(firstResult)
                    .setMaxResults(pageSize)
                    .list();
        }
    }

    public long countAllMatches() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select count(m) from Match m", Long.class)
                    .uniqueResult();
        }
    }

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
