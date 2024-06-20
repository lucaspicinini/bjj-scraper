package io.github.lucaspicinini.bjj_scraper;

import io.github.lucaspicinini.bjj_scraper.controller.AllFightersRequest;
import io.github.lucaspicinini.bjj_scraper.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BjjScraperApplication implements CommandLineRunner {
	@Autowired
	private Repository repository;

	public static void main(String[] args) {
		SpringApplication.run(BjjScraperApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		AllFightersRequest.getAllFighters(repository);
		System.out.println("Scrap done!");
	}

}
