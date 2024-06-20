package io.github.lucaspicinini.bjj_scraper.controller;

import io.github.lucaspicinini.bjj_scraper.repository.Repository;
import io.github.lucaspicinini.bjj_scraper.service.FighterRequest;
import io.github.lucaspicinini.bjj_scraper.service.HomeRequest;

public class AllFightersRequest {

    public static void getAllFighters(Repository repository) {
        var homeResponseBody = HomeRequest.getResponseBody();
        var fightersHrefs = FightersListToScrap.getList(homeResponseBody);

        for (var fighterHref : fightersHrefs) {
            var fighterResponseBody = FighterRequest.getResponseBody(fighterHref);
            var fighterDTO = FighterScrapper.scrap(fighterResponseBody, fighterHref);
        }
    }
}
