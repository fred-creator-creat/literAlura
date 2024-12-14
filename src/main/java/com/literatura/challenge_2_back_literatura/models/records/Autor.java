package com.literatura.challenge_2_back_literatura.models.records;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Autor(
    @JsonAlias("name") String nome,
    @JsonAlias("birth_year") Integer dataNascimento,
    @JsonAlias("death_year") Integer dataFalecimento
) {
}
