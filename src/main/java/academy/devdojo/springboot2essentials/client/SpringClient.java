package academy.devdojo.springboot2essentials.client;

import academy.devdojo.springboot2essentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate()
                .getForEntity("http://localhost:8080/animes/{id}", Anime.class, 1);
        log.info(entity);

        Anime anime = new RestTemplate()
                .getForObject("http://localhost:8080/animes/{id}", Anime.class, 1);
        log.info(anime);

        ResponseEntity<List<Anime>> exchange = new RestTemplate()
                .exchange(
                        "http://localhost:8080/animes/all",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Anime>>() {
                        });
        log.info(exchange.getBody());

//        Anime kingdom = Anime.builder().name("Kingdom").build();
//        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes", kingdom, Anime.class);
//        log.info("Saved Anime {}", kingdomSaved);

        Anime samuraiChamploo = Anime.builder().name("Samurai Champloo").build();
        ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate()
                .exchange(
                        "http://localhost:8080/animes",
                        HttpMethod.POST,
                        new HttpEntity<>(samuraiChamploo, createHttpHeaders()), Anime.class);
        log.info("Saved Anime {}", samuraiChamplooSaved.getBody());

        Anime animeToBeUpdated = samuraiChamplooSaved.getBody();
        animeToBeUpdated.setName("Samurai Champloo 2");

        ResponseEntity<Void> samuraiChamplooUpdated = new RestTemplate()
                .exchange(
                        "http://localhost:8080/animes",
                        HttpMethod.PUT,
                        new HttpEntity<>(animeToBeUpdated, createHttpHeaders()), Void.class);
        log.info("Updated Anime {}", samuraiChamplooUpdated.getBody());

        ResponseEntity<Void> samuraiChamplooDeleted = new RestTemplate()
                .exchange(
                        "http://localhost:8080/animes/{id}",
                        HttpMethod.DELETE,
                        null,
                        Void.class,
                        animeToBeUpdated.getId()
                );

        log.info("Deleted: {}", samuraiChamplooUpdated);


    }

    private static HttpHeaders createHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
