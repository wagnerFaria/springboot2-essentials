package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate()
                .getForEntity("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(entity);
        Anime object = new RestTemplate()
                .getForObject("http://localhost:8080/animes/2", Anime.class);
        log.info(object);

//        Anime[] animesArr = new RestTemplate()
//                .getForObject("http://localhost:8080/animes/all", Anime[].class);
//        log.info(Arrays.toString(animesArr));

        log.info("LISTA");
        ResponseEntity<List<Anime>> listAnime = new RestTemplate()
                .exchange("http://localhost:8080/animes/all", HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        });
        log.info(listAnime.getBody());
//        Anime kingdom = Anime.builder().name("Kingdom").build();
//        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes", kingdom, Anime.class);
//        log.info("Saved anime: {}", kingdomSaved);
        Anime samuraiChamploo = Anime.builder().name("Samurai Champloo").build();
        ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate()
                .exchange("http://localhost:8080/animes",
                        HttpMethod.POST,
                        new HttpEntity<>(samuraiChamploo, createJsonHeader()), Anime.class);
        Anime samurai = samuraiChamplooSaved.getBody();
        log.info("Saved anime: {}", samurai);
        samurai.setName("Samurai Champloo 2");

        ResponseEntity<Void> samuraiChamplooUpdated = new RestTemplate()
                .exchange("http://localhost:8080/animes",
                        HttpMethod.PUT,
                        new HttpEntity<>(samurai, createJsonHeader()), Void.class);
        log.info("Alterado com sucesso: {}", samuraiChamplooUpdated);

        ResponseEntity<Void> samuraiChamplooDeleted = new RestTemplate()
                .exchange("http://localhost:8080/animes/{id}",
                        HttpMethod.DELETE,
                        null,
                        Void.class,
                        samurai.getId());
        log.info("Deletado com sucesso: {}", samuraiChamplooDeleted);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.setBearerAuth(algo);
        return httpHeaders;
    }
}
