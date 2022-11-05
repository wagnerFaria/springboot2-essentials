package academy.devdojo.springboot2essentials.service;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.repository.AnimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {
    //    private final AnimeRepository animeRepository;
    public List<Anime> listAll() {
        return List.of(Anime.builder().id(1L).name("DBZ").build(), Anime.builder().id(2L).name("One Piece").build());
    }
}
