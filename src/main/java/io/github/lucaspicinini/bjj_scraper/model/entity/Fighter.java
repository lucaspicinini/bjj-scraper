package io.github.lucaspicinini.bjj_scraper.model.entity;

import io.github.lucaspicinini.bjj_scraper.model.dto.FighterDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lutadores")
@NoArgsConstructor
@Getter
@ToString
public class Fighter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String urlId;
    private String imageUrl;
    private String fullName;
    @Setter
    @Column(length = 4096)
    private String nickname;
    private String favoritePosition;
    private String weightDivision;
    @Setter
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @Setter
    @Column(columnDefinition = "TEXT")
    private String biography;
    @OneToMany(mappedBy = "fighter", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Lineage> lineages = new ArrayList<>();
    @OneToMany(mappedBy = "fighter", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Achievement> achievements;

    public Fighter(FighterDTO fighterDTO) {
        this.urlId = fighterDTO.urlId();
        this.imageUrl = fighterDTO.imageUrl();
        this.fullName = fighterDTO.fullName();
        this.nickname = fighterDTO.nickname();
        this.favoritePosition = fighterDTO.favoritePosition();
        this.weightDivision = fighterDTO.weightDivision();
        this.biography = fighterDTO.biography();
        this.lineages = fighterDTO.lineages().stream().map(Lineage::new).toList();
        this.lineages.forEach(lineage -> lineage.setFighter(this));
        this.achievements = fighterDTO.achievements().stream().map(Achievement::new).toList();
        this.achievements.forEach(achievement -> achievement.setFighter(this));
    }
}
