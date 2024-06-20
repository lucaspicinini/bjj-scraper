package io.github.lucaspicinini.bjj_scraper.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class FightersListToScrap {
    private static final List<String> FIGHTERS_HREFS = new ArrayList<>();

    public static List<String> getList(String homeResponseBody) {
        Document html = Jsoup.parse(homeResponseBody);
        Elements fightersTableRows = html.select("table#tablepress-8 tbody tr");

        for (Element row : fightersTableRows) {
            Element firstLinkInRow = row.selectFirst("td.column-1 a[href]");

            if (firstLinkInRow != null) {
                String fighterHref = firstLinkInRow.attr("href");
                FIGHTERS_HREFS.add(fighterHref);
            }

        }

        return FIGHTERS_HREFS;
    }
}
