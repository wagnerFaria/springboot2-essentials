package academy.devdojo.springboot2essentials.controller;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot2essentials.requests.AnimePutRequestBody;
import academy.devdojo.springboot2essentials.service.AnimeService;
import academy.devdojo.springboot2essentials.util.AnimeCreator;
import academy.devdojo.springboot2essentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2essentials.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;

    @Mock
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito
                .when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito
                .when(animeServiceMock.listAllNonPageable())
                .thenReturn(new ArrayList<>(List.of(AnimeCreator.createValidAnime())));

        BDDMockito
                .when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito
                .when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito
                .when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito
                .doNothing()
                .when(animeServiceMock)
                .replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito
                .doNothing()
                .when(animeServiceMock)
                .delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("List returns a list of animes inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.list(null).getBody();
        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty();
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("List all returns list of anime when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful() {
        List<Anime> animes = animeController.listAll().getBody();

        Assertions
                .assertThat(animes)
                .hasSize(1)
                .isNotEmpty();
    }

    @Test
    @DisplayName("Find By Id return Anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeController.findById(expectedId).getBody();

        Assertions
                .assertThat(anime)
                .isNotNull();

        Assertions
                .assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Find By Name return List of animes when successful")
    void findByName_ReturnsListOfAnimes_WhenSuccessful() {
        String name = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeController.findByName(name).getBody();

        Assertions
                .assertThat(animes)
                .isNotEmpty()
                .hasSize(1);

        Assertions
                .assertThat(animes.get(0).getName())
                .isEqualTo(name);
    }

    @Test
    @DisplayName("Find By Name return an empty List when anime is not found")
    void findByName_ReturnsAnEmptyList_WhenAnimeNotFound() {

        BDDMockito
                .when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeController.findByName("teste").getBody();

        Assertions
                .assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions
                .assertThat(anime)
                .isNotNull()
                .isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("Replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful() {

        ResponseEntity<Void> response = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions
                .assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();

        Assertions
                .assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
        Assertions
                .assertThat(response)
                .isNotNull();
    }

    @Test
    @DisplayName("Delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {

        ResponseEntity<Void> response = animeController.delete(1L);

        Assertions
                .assertThatCode(() -> animeController.delete(1L))
                .doesNotThrowAnyException();

        Assertions
                .assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
        Assertions
                .assertThat(response)
                .isNotNull();
    }
}