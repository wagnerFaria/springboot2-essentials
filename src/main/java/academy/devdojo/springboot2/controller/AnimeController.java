package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("animes")
@RestController
@Log4j2
@AllArgsConstructor
public class AnimeController {

    @GetMapping()
    public List<Anime> list() {
        return Arrays.asList(
                Anime.builder().name("Berserk").build(),
                Anime.builder().name("Boku no Hero").build()
        );
    }

}
