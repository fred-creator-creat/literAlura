package com.literatura.challenge_2_back_literatura.models;
import com.literatura.challenge_2_back_literatura.models.records.DadosLivro;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer dataNascimento;

    private Integer dataFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@Transient
    private List<Livro> livros;


    public Autor() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getDataNascimento() {
        return dataNascimento;
    }

    public Integer getDataFalecimento() {
        return dataFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public Autor(com.literatura.challenge_2_back_literatura.models.records.Autor autor) {
        this.nome = autor.nome();
        this.dataNascimento = autor.dataNascimento();
        this.dataFalecimento = autor.dataFalecimento();
    }

    @Override
    public String toString() {
        return
                "nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", dataFalecimento=" + dataFalecimento;
    }
}
