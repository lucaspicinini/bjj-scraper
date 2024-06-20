package io.github.lucaspicinini.bjj_scraper.model.dto;

import java.util.List;

public record FighterDTO(
        String urlId,
        String imageUrl,
        String fullName,
        String nickname,
        String favoritePosition,
        String weightDivision,
        TeamDTO team,
        String biography,
        List<LineageDTO> lineages,
        List<AchievementDTO> achievements
) {
}
