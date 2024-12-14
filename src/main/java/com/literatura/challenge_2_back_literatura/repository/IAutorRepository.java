package com.literatura.challenge_2_back_literatura.repository;
import com.literatura.challenge_2_back_literatura.models.Autor;
import com.literatura.challenge_2_back_literatura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAutorRepository extends JpaRepository<Autor,Long> {

    @Override
    List<Autor> findAll();


    List<Autor> findByDataNascimentoLessThanOrDataFalecimentoGreaterThanEqual(int anoBuscado, int anoBuscado1);

    Optional<Autor> findFirstByNomeContainsIgnoreCase(String escritor);
}
