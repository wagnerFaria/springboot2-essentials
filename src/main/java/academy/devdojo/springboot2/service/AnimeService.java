package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.AnimeRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AnimeService {

    // private final AnimeRepository animeRepository;
    
    public List<Anime> listAll() {
        return Arrays.asList(
                Anime.builder().id(1L).name("Berserk").build(),
                Anime.builder().id(2L).name("Boku no Hero").build()
        );
    }
}
