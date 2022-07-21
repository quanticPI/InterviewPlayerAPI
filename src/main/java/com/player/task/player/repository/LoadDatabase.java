package com.player.task.player.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.player.task.entities.Player;
import com.player.task.entities.PositionEnum;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(PlayerRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Player("David Beckham", "England", PositionEnum.defender, LocalDateTime.now().toLocalDate())));
      log.info("Preloading " + repository.save(new Player("Hristo Stoichkov", "Bulgaria", PositionEnum.forward,LocalDate.of(2021, 2, 2))));
      log.info("Preloading " + repository.save(new Player("Raul", "Spain", PositionEnum.goalkeeper ,LocalDate.of(1986,1, 3))));
    };
  }
}