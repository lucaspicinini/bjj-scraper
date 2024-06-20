package io.github.lucaspicinini.bjj_scraper.controller;

import io.github.lucaspicinini.bjj_scraper.model.dto.FighterDTO;
import io.github.lucaspicinini.bjj_scraper.model.entity.Fighter;
import io.github.lucaspicinini.bjj_scraper.model.entity.Team;
import io.github.lucaspicinini.bjj_scraper.repository.Repository;
import io.github.lucaspicinini.bjj_scraper.service.FighterRequest;
import io.github.lucaspicinini.bjj_scraper.service.HomeRequest;
import io.github.lucaspicinini.bjj_scraper.utils.AiApiConfigs;
import io.github.lucaspicinini.bjj_scraper.utils.FighterTranslator;

import java.util.List;
import java.util.Optional;

public class AllFightersRequest {

    public static void getAllFighters(Repository repository) {
        String homeResponseBody = HomeRequest.getResponseBody();
        List<String> fightersHrefs = FightersListToScrap.getList(homeResponseBody);

        for (var fighterHref : fightersHrefs) {
            String fighterResponseBody = FighterRequest.getResponseBody(fighterHref);
            FighterDTO fighterDTO = FighterScraper.scrap(fighterResponseBody, fighterHref);
            Fighter fighter = new Fighter(fighterDTO);
            Team team;

            try {
                Optional<Team> teamSearch = repository.findByName(fighterDTO.team().name());

                if (teamSearch.isPresent()) {
                    fighter.setTeam(teamSearch.get());
                    teamSearch.get().setFighters(fighter);
                    team = teamSearch.get();
                } else {
                    Team newTeam = new Team(fighterDTO.team());
                    newTeam.setFighters(fighter);
                    fighter.setTeam(newTeam);
                    team = newTeam;
                }

                if (AiApiConfigs.USE_AI_API) {
                    FighterTranslator.translateWithAi(fighter);
                }

                repository.save(team);
                System.out.println("Atleta " + fighter.getFullName() + " criado com sucesso!");
            } catch (Exception e) {
                System.out.println("O atleta " + fighter.getFullName() + " já foi armazenado anteriormente");
            }
        }
    }
}
