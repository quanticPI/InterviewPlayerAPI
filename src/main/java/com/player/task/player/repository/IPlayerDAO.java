package com.player.task.player.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.player.task.entities.Player;
import com.player.task.entities.PositionEnum;

public interface IPlayerDAO {

	public List<Player> allPlayers();
	
	public Player savePlayer(Player player);
	
	public Player updatePlayer(Player player, String id) throws Exception;
	
	public List<Player> getPlayersByCountry(String country);
	
	public List<Player> getPlayersByPosition(PositionEnum position);
}
