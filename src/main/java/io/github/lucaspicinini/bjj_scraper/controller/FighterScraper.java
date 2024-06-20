package io.github.lucaspicinini.bjj_scraper.controller;

import io.github.lucaspicinini.bjj_scraper.model.dto.AchievementDTO;
import io.github.lucaspicinini.bjj_scraper.model.dto.FighterDTO;
import io.github.lucaspicinini.bjj_scraper.model.dto.LineageDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class FighterScraper {

    public static FighterDTO scrap(String fighterResponseBody, String fighterHref) {
        Document html = Jsoup.parse(fighterResponseBody);
        String urlId = fighterHref.substring(4);
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
            Element metaOgImage = html.selectFirst("meta[property=og:image]");
            imageUrl = metaOgImage != null ? metaOgImage.attr("content") : "";

            Element fullNameElement = html.selectFirst("p:contains(Full Name:) strong");
            fullName = fullNameElement != null ? fullNameElement.nextSibling().toString().trim() : "";

            Element nicknameElement = html.selectFirst("p:contains(Nickname:) strong");
            nickname = nicknameElement != null ? nicknameElement.nextSibling().toString()
                    .replaceAll("&nbsp;", "")
                    .replaceAll("N/A", "")
                    .replaceAll("n/a", "")
                    .trim() : "";

            Elements lineageElements = html.select("p:matches(Lineage) strong");
            for (Element lineageElement : lineageElements) {
                String lineage = lineageElement.parent().text().replace(lineageElement.text(), "").trim();
                lineages.add(lineage);
            }

            Element favoritePositionElement = null;
            if (html.selectFirst("p:contains(Favorite) strong") != null) {
                favoritePositionElement = html.selectFirst("p:contains(Favorite) strong");
            }
            if (html.selectFirst("p:contains(Favourite) strong") != null) {
                favoritePositionElement = html.selectFirst("p:contains(Favourite) strong");
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

            Element weightDivisionElement = html.selectFirst("p:contains(Weight Division:) strong");
            weightDivision = weightDivisionElement != null ? weightDivisionElement.nextSibling().toString()
                    .replaceAll("&nbsp;", "")
                    .trim() : "";

            Element teamElement = html.selectFirst("p:contains(ssociation) strong");
            if (teamElement != null) {

                if (teamElement.nextSibling().toString().equals(" ")) {
                    Element linkElement = teamElement.nextElementSibling();
                    team = linkElement.text().replaceAll("&nbsp;", "").trim();
                } else {
                    team = teamElement.nextSibling().toString().replaceAll("&nbsp;", "").trim();
                }

            }

            Element bioTitleElement = html.selectFirst("h3:contains(Biography)");
            if (bioTitleElement != null) {
                Element sibling = bioTitleElement.nextElementSibling();

                while (sibling != null && sibling.tagName().equals("p")) {
                    biography.append(sibling.text()).append("\n");
                    sibling = sibling.nextElementSibling();
                }

            }

            Elements achievementsElements = html.select("p:contains(Main Achievements) + ul li");
            for (Element achievementElement : achievementsElements) {
                achievements.add(achievementElement.text().trim());
            }

        } catch (Exception e) {
            System.out.println("Erro ao realizar scrapping de dados.");
        }

        return new FighterDTO(
                urlId,
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
