package io.github.lucaspicinini.bjj_scraper.model.entity;

import io.github.lucaspicinini.bjj_scraper.model.dto.TeamDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipes")
@NoArgsConstructor
@Getter
@ToString
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Fighter> fighters = new ArrayList<>();

    public Team(TeamDTO team) {
        this.name = team.name();
    }

    public void setFighters(Fighter fighter) {
        this.fighters.add(fighter);
    }
}
