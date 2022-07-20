package com.player.task.player.repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.player.task.entities.Player;

@Repository
public class PlayerDAO implements IPlayerDAO {
	
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Player> allPlayers() {
		List<Player> list = em.createQuery("from Player").getResultList();
		if(!list.isEmpty()) {
			Collections.sort(list, Comparator.comparing((Player p) -> p.getCreated_at()).reversed());
			return list;
		}
		else 
			return null;
	}

	@Override
	@Transactional
	public Player savePlayer(Player player) {		
		em.persist(player);
		return player;
	}

	@Override
	@Transactional
	public Player updatePlayer(Player newPlayer, String id) {
		Player p = em.find(Player.class, id);
		if (p != null) {
			p.setName(newPlayer.getName());
			p.setCountry(newPlayer.getCountry());
			p.setPosition(newPlayer.getPosition());
			p.setBirth_date(newPlayer.getBirth_date());
		}
		return p;
	}
	
	@Override
	public List<Player> getPlayersByCountry(String country) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Player> query = cb.createQuery(Player.class);
		Root<Player> root = query.from(Player.class);
		query.where(cb.equal(root.get("country"), country));
		
		TypedQuery<Player> q = em.createQuery(query);
		return q.getResultList();
	}

}
