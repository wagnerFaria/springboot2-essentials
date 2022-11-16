package academy.devdojo.springboot2essentials.integration;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.repository.AnimeRepository;
import academy.devdojo.springboot2essentials.util.AnimeCreator;
import academy.devdojo.springboot2essentials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class AnimeControlerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
//    caso precise pegar o valor da porta
//    @LocalServerPort
//    private int port;

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("List returns a list of animes inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        PageableResponse<Anime> animePage = testRestTemplate.exchange("/animes", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Anime>>() {
        }).getBody();

        Assertions
                .assertThat(animePage)
                .isNotNull();
        Assertions
                .assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions
                .assertThat(animePage.toList().get(0).getName())
                .isEqualTo(expectedName);
    }
}
