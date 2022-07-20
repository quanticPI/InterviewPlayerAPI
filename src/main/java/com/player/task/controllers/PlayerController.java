package com.player.task.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.player.task.entities.Player;
import com.player.task.player.repository.IPlayerDAO;

@RestController
public class PlayerController {
	
	@Autowired
	private IPlayerDAO playerDAO;
	
	PlayerController(){
		
	}
	
	@GetMapping("/players")
	public List<Player> getAllPlayers() {
		return playerDAO.allPlayers();
	}
	
	@GetMapping("/players/country/{country}")
	public List<Player> getPlayersByCountry(@PathVariable String country) {
		return playerDAO.getPlayersByCountry(country);
	}
	
	@PostMapping("/createPlayer")
	public Player createPlayer(@RequestBody Player newPlayer) {
		newPlayer.setCreated_at(LocalDateTime.now());
		return playerDAO.savePlayer(newPlayer);
	}
	
	@PostMapping("/updatePlayer/{id}")
	public Player createPlayer(@RequestBody Player newPlayer, @PathVariable String id) {
		return playerDAO.updatePlayer(newPlayer, id);
	}
}
