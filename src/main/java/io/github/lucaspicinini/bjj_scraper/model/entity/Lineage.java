package io.github.lucaspicinini.bjj_scraper.model.entity;

import io.github.lucaspicinini.bjj_scraper.model.dto.LineageDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "linhagens")
@NoArgsConstructor
@Getter
@ToString
public class Lineage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Setter
    @ManyToOne
    @JoinColumn(name = "fighter_id")
    private Fighter fighter;

    public Lineage(LineageDTO lineage) {
        this.name = lineage.name();
    }
}