package academy.devdojo.springboot2essentials.repository;

import academy.devdojo.springboot2essentials.domain.Anime;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AnimeRepository {
    List<Anime> listAll();
}
