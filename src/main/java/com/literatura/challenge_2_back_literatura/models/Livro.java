package com.literatura.challenge_2_back_literatura.models;
import com.literatura.challenge_2_back_literatura.dtos.Genero;
import com.literatura.challenge_2_back_literatura.models.records.DadosLivro;
import com.literatura.challenge_2_back_literatura.models.records.Midia;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long livroId;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")
    //@Transient
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    private String idioma;
    private String imagem;
    private Long quantidadeDownloads;

    public Livro() {
    }

    public Livro(DadosLivro dadosLivro) {
        this.livroId = dadosLivro.livroId();
        this.titulo = dadosLivro.titulo();
        
        if (dadosLivro.autor() != null && !dadosLivro.autor().isEmpty()) {
            this.autor = new Autor(dadosLivro.autor().get(0));
        } else {
            this.autor = null;
        }
        this.genero =  generoModificado(dadosLivro.genero());
        this.idioma = idiomaModificado(dadosLivro.idioma());
        this.imagem = imagemModificada(dadosLivro.imagem());
        this.quantidadeDownloads = dadosLivro.quantidadeDownloads();
    }

    public Livro(Livro livro) {
    }

    private Genero generoModificado(List<String> generos) {
        if (generos == null || generos.isEmpty()) {
            return Genero.DESCONHECIDO;
        }
        Optional<String> firstGenero = generos.stream()
                .map(g -> {
                    int index = g.indexOf("--");
                    return index != -1 ? g.substring(index + 2).trim() : null;
                })
                .filter(Objects::nonNull)
                .findFirst();
        return firstGenero.map(Genero::fromString).orElse(Genero.DESCONHECIDO);
    }

    private String idiomaModificado(List<String> idiomas) {
        if (idiomas == null || idiomas.isEmpty()) {
            return "Desconhecido";
        }
        return idiomas.get(0);
    }

    private String imagemModificada(Midia midia) {
        if (midia == null || midia.imagem().isEmpty()) {
            return "Sim imagem";
        }
        return midia.imagem();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Long getLivroId() {
        return livroId;
    }

    public void setLivroId(Long livroId) {
        this.livroId = livroId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }


    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }



    public Long getQuantidadeDownloads() {
        return quantidadeDownloads;
    }

    public void setQuantidadeDownloads(Long quantidadeDownloads) {
        this.quantidadeDownloads = quantidadeDownloads;
    }

    @Override
    public String toString() {
        return
                "  \nid=" + id +
                "  \nLivro id=" + livroId +
                ", \ntitulo='" + titulo + '\'' +
                ", \nautor=" + (autor != null ? autor.getNome() : "N/A")+
                ", \ngenero=" + genero +
                ", \nidioma=" + idioma +
                ", \nimagem=" + imagem +
                ", \nquantidadeDownloads=" + quantidadeDownloads;
    }
}