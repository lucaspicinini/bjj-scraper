package io.github.lucaspicinini.bjj_scraper.repository;

import io.github.lucaspicinini.bjj_scraper.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Repository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);
}