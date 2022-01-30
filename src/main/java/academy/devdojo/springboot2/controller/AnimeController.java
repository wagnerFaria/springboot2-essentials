package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.service.AnimeService;
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

    private AnimeService animeService;

    @GetMapping()
    public List<Anime> list() {
        return animeService.listAll();
    }

}
