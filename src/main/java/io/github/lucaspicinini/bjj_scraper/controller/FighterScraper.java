package io.github.lucaspicinini.bjj_scraper.controller;


import io.github.lucaspicinini.bjj_scraper.model.dto.AchievementDTO;
import io.github.lucaspicinini.bjj_scraper.model.dto.FighterDTO;
import io.github.lucaspicinini.bjj_scraper.model.dto.LineageDTO;
import io.github.lucaspicinini.bjj_scraper.model.dto.TeamDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class FighterScraper {

    public static FighterDTO scrap(String fighterResponseBody, String fighterHref) {
        final Document HTML = Jsoup.parse(fighterResponseBody);
        final String URL_ID = fighterHref.substring(4);
        String imageUrl = "";
        String fullName = "";
        String nickname = "";
        String favoritePosition = "";
        String weightDivision = "";
        String team = "";
        StringBuilder biography = new StringBuilder();
        List<String> lineages = new ArrayList<>();
        List<String> achievements = new ArrayList<>();

        try {
            // image URL scraping
            Element metaOgImage = HTML.selectFirst("meta[property=og:image]");
            imageUrl = metaOgImage != null ? metaOgImage.attr("content") : "";

            // full name scraping
            Element fullNameElement = HTML.selectFirst("p:contains(Full Name:) strong");
            fullName = fullNameElement != null ? fullNameElement.nextSibling().toString()
                    .replaceAll("&nbsp;", "")
                    .replaceAll("&amp;", "and")
                    .replaceAll("N/A", "")
                    .replaceAll("n/a", "")
                    .trim() : "";

            // nickname scraping
            Element nicknameElement = HTML.selectFirst("p:contains(Nickname:) strong");
            nickname = nicknameElement != null ? nicknameElement.nextSibling().toString()
                    .replaceAll("&nbsp;", "")
                    .replaceAll("&amp;", "and")
                    .replaceAll("N/A", "")
                    .replaceAll("n/a", "")
                    .trim() : "";

            // lineages scraping
            Elements lineageElements = HTML.select("p:matches(Lineage) strong");
            for (Element lineageElement : lineageElements) {
                String lineage = lineageElement.parent().text().replace(lineageElement.text(), "").trim();
                lineages.add(lineage);
            }

            // favorite position scraping
            Element favoritePositionElement = null;
            if (HTML.selectFirst("p:contains(Favorite) strong") != null) {
                favoritePositionElement = HTML.selectFirst("p:contains(Favorite) strong");
            }
            if (HTML.selectFirst("p:contains(Favourite) strong") != null) {
                favoritePositionElement = HTML.selectFirst("p:contains(Favourite) strong");
            }
            if (favoritePositionElement != null) {

                if (favoritePositionElement.nextSibling().toString().equals(" ")) {
                    Element linkElement = favoritePositionElement.nextElementSibling();
                    favoritePosition = linkElement.text()
                            .replaceAll("&nbsp;", "")
                            .replaceAll("&amp;", "and")
                            .replaceAll("N/A", "")
                            .replaceAll("n/a", "")
                            .trim();
                } else {
                    favoritePosition = favoritePositionElement.nextSibling().toString()
                            .replaceAll("&nbsp;", "")
                            .replaceAll("&amp;", "and")
                            .replaceAll("N/A", "")
                            .replaceAll("n/a", "")
                            .trim();
                }

            }

            // weight division scraping
            Element weightDivisionElement = HTML.selectFirst("p:contains(Weight Division:) strong");
            weightDivision = weightDivisionElement != null ? weightDivisionElement.nextSibling().toString()
                    .replaceAll("&nbsp;", "")
                    .replaceAll("&amp;", "and")
                    .replaceAll("N/A", "")
                    .replaceAll("n/a", "")
                    .trim() : "";

            // team element
            Element teamElement = HTML.selectFirst("p:contains(ssociation) strong");
            if (teamElement != null) {

                if (teamElement.nextSibling().toString().equals(" ")) {
                    Element linkElement = teamElement.nextElementSibling();
                    team = linkElement.text().replaceAll("&nbsp;", "").trim();
                } else {
                    team = teamElement.nextSibling().toString().replaceAll("&nbsp;", "").trim();
                }

            }

            // bio scraping
            Element bioTitleElement = HTML.selectFirst("h3:contains(Biography)");
            if (bioTitleElement != null) {
                Element sibling = bioTitleElement.nextElementSibling();

                while (sibling != null && sibling.tagName().equals("p")) {
                    biography.append(sibling.text()).append("\n");
                    sibling = sibling.nextElementSibling();
                }

            }

            // achievements scraping
            Elements achievementsElements = HTML.select("p:contains(Main Achievements) + ul li");
            for (Element achievementElement : achievementsElements) {
                achievements.add(achievementElement.text().trim());
            }

        } catch (Exception e) {
            System.out.println("Erro ao realizar scrapping de dados: " + e.getMessage());
        }

        return new FighterDTO(
                URL_ID,
                imageUrl,
                fullName,
                nickname,
                favoritePosition,
                weightDivision,
                new TeamDTO(team),
                biography.toString().trim(),
                lineages.stream().map(LineageDTO::new).toList(),
                achievements.stream().map(AchievementDTO::new).toList()
        );
    }
}
