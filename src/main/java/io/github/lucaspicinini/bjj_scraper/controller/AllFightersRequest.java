package io.github.lucaspicinini.bjj_scraper.controller;

import io.github.lucaspicinini.bjj_scraper.model.dto.FighterDTO;
import io.github.lucaspicinini.bjj_scraper.repository.Repository;
import io.github.lucaspicinini.bjj_scraper.service.FighterRequest;
import io.github.lucaspicinini.bjj_scraper.service.HomeRequest;

import java.util.List;

public class AllFightersRequest {

    public static void getAllFighters(Repository repository) {
        String homeResponseBody = HomeRequest.getResponseBody();
        List<String> fightersHrefs = FightersListToScrap.getList(homeResponseBody);

        for (var fighterHref : fightersHrefs) {
            String fighterResponseBody = FighterRequest.getResponseBody(fighterHref);
            FighterDTO fighterDTO = FighterScraper.scrap(fighterResponseBody, fighterHref);
        }
    }
}
