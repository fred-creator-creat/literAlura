package com.literatura.challenge_2_back_literatura.repository;
import com.literatura.challenge_2_back_literatura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ILivroRepository extends JpaRepository<Livro, Long> {

    boolean existsByTitulo(String titulo);

    Livro findByTituloContainsIgnoreCase(String titulo);

    List<Livro> findByIdioma(String idioma);



    @Query("SELECT l FROM Livro l ORDER BY l.quantidadeDownloads DESC LIMIT 10")
    List<Livro> findTop10ByTituloByQuantidadeDownloads();


}
