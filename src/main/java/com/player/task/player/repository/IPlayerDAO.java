package com.player.task.player.repository;

import java.util.List;

import com.player.task.entities.Player;

public interface IPlayerDAO {

	public List<Player> allPlayers();
	
	public Player savePlayer(Player player);
	
	public Player updatePlayer(Player player, String id);
	
	public List<Player> getPlayersByCountry(String country);
}
