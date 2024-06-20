package io.github.lucaspicinini.bjj_scraper.utils;

import io.github.lucaspicinini.bjj_scraper.model.entity.Fighter;
import io.github.lucaspicinini.bjj_scraper.service.AiApiChat;

public class FighterTranslator {
    public static void translateWithAi(Fighter fighter) {
        if (fighter.getBiography() != null) {

            if (!fighter.getBiography().isEmpty()) {
                String translatedBio = AiApiChat.getResponse(fighter.getBiography());
                fighter.setBiography(translatedBio);
            }

        }

        if (fighter.getNickname() != null) {

            if (!fighter.getNickname().isEmpty()) {
                String translatedNickname = AiApiChat.getResponse(fighter.getNickname());
                fighter.setNickname(translatedNickname);
            }

        }
    }
}
