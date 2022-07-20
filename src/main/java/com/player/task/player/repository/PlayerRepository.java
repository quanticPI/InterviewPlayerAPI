package com.player.task.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.player.task.entities.Player;

public interface PlayerRepository extends JpaRepository<Player, String> {

}
