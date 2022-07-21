package com.player.task.controllers;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.player.task.entities.Player;
import com.player.task.entities.PositionEnum;
import com.player.task.player.repository.IPlayerDAO;

@RestController
public class PlayerController {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerController.class);
	@Autowired
	private IPlayerDAO playerDAO;
	
	PlayerController(){
		
	}
	
	@GetMapping("/players")
	public ResponseEntity<List<Player>> all() {
		HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/json");
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok().headers(headers);
        List<Player> players = playerDAO.allPlayers();
        
        return responseBuilder.body(players);
    }
	
	@GetMapping("/players/country/{country}")
	public List<Player> getPlayersByCountry(@PathVariable String country) {
		return playerDAO.getPlayersByCountry(country);
	}
	
	@GetMapping("/players/position/{position}")
	public ResponseEntity<List<Player>> getPlayersByPosition(@PathVariable String position) {
		List<Player> players = null;
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity.BodyBuilder responseBuilder;
        headers.set("Content-type", "application/json");
		try {
			PositionEnum positionEnum = PositionEnum.valueOf(position);
			players = playerDAO.getPlayersByPosition(positionEnum);
			responseBuilder = ResponseEntity.ok().headers(headers);
		} catch (IllegalArgumentException ex) {
			log.error(String.format("Illegal argument exception: %s is not a valid player position", position));			
			responseBuilder = ResponseEntity.badRequest().headers(headers);			
		}
		return responseBuilder.body(players);
	}
	
	@PostMapping("/players")
	public ResponseEntity<Player> createPlayer(@RequestBody Player newPlayer) {
		ResponseEntity.BodyBuilder responseBuilder;		
		if(newPlayer.getName().isEmpty() || newPlayer.getCountry().isEmpty()) {
			responseBuilder = ResponseEntity.badRequest();
			return responseBuilder.build();
		}
		newPlayer.setCreated_at(LocalDateTime.now());
		Player returnPlayer = playerDAO.savePlayer(newPlayer);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");
		URI newPlayerURI = URI.create("localhost:8080/player/"+returnPlayer.getId());
		responseBuilder = ResponseEntity.created(newPlayerURI).headers(headers);
		return responseBuilder.body(returnPlayer);
	}
	
	@PutMapping("/players/{id}")
	public ResponseEntity<Player> updatePlayer(@RequestBody Player newPlayer, @PathVariable String id) {
		ResponseEntity.BodyBuilder responseBuilder;
		HttpHeaders headers = new HttpHeaders();
		Player updatedPlayer = null;
		try {
			updatedPlayer = playerDAO.updatePlayer(newPlayer, id);
			headers.set("Content-type", "application/json");
			if(updatedPlayer == null) {
				responseBuilder = ResponseEntity.badRequest();
				return responseBuilder.build();
			}
			URI newPlayerURI = URI.create("localhost:8080/player/"+updatedPlayer.getId());
			responseBuilder = ResponseEntity.created(newPlayerURI).headers(headers);			
		} catch (Exception e) {
			log.error(String.format("Illegal argument exception: %s is not a valid player position", newPlayer.getPosition()));
			responseBuilder = ResponseEntity.badRequest().headers(headers);
		}
		return responseBuilder.body(updatedPlayer);
	}

}
