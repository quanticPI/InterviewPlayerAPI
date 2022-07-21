package com.player.task.player.repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.player.task.entities.Player;
import com.player.task.entities.PositionEnum;

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
	public Player updatePlayer(Player newPlayer, String id) throws Exception {
		Player p = null;
		try {
			p = em.find(Player.class, id);
			if (p != null) {
				String name = newPlayer.getName().isEmpty() ? p.getName() : newPlayer.getName();
				String country = newPlayer.getCountry().isEmpty() ? p.getCountry() : newPlayer.getCountry();
				p.setName(name);
				p.setCountry(country);
				p.setPosition(newPlayer.getPosition());
				p.setBirth_date(newPlayer.getBirth_date());
			}
		} catch (Exception e) {
			throw e;
		}
		return p;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Player> getPlayersByCountry(String country) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Player> query = cb.createQuery(Player.class);
		Root<Player> root = query.from(Player.class);
		query.where(cb.equal(root.get("country"), country));
		
		TypedQuery<Player> q = em.createQuery(query);
		return q.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Player> getPlayersByPosition(PositionEnum position) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Player> cq = cb.createQuery(Player.class);
		Root<Player> root = cq.from(Player.class);
		cq.where(cb.equal(root.get("position"), position));
		TypedQuery<Player> query = em.createQuery(cq);
		return query.getResultList();
	}

}
