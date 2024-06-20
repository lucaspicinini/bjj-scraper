package io.github.lucaspicinini.bjj_scraper.model.entity;

import io.github.lucaspicinini.bjj_scraper.model.dto.AchievementDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "conquistas")
@NoArgsConstructor
@Getter
@ToString
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Setter
    @ManyToOne
    @JoinColumn(name = "fighter_id")
    private Fighter fighter;

    public Achievement(AchievementDTO achievementDTO) {
        this.name = achievementDTO.name();
    }
}