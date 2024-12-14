package com.literatura.challenge_2_back_literatura.Livraria; 
import com.literatura.challenge_2_back_literatura.config.ConsumoApiGutendex;
import com.literatura.challenge_2_back_literatura.config.ConverterDados;
import com.literatura.challenge_2_back_literatura.models.Autor;
import com.literatura.challenge_2_back_literatura.models.Livro;
import com.literatura.challenge_2_back_literatura.models.LivrosRespostaApi;
import com.literatura.challenge_2_back_literatura.models.records.DadosLivro;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.literatura.challenge_2_back_literatura.repository.IAutorRepository;
import com.literatura.challenge_2_back_literatura.repository.ILivroRepository;

public class Livraria {

    private Scanner sc = new Scanner(System.in);
    private ConsumoApiGutendex consumoApi = new ConsumoApiGutendex();
    private ConverterDados converter = new ConverterDados();
    private static String API_BASE = "https://gutendex.com/books/?search=";
    private List<Livro> dadosLivro = new ArrayList<>();
    private ILivroRepository livroRepository;
    private IAutorRepository autorRepository;
    public Livraria(ILivroRepository livroRepository, IAutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void consumo(){
        var opcao = -1;
        while (opcao != 0){
            var menu = """
                    
                    |***************************************************|
                    |*****       BEM-VINDO À LIVRARIA GOOD       ******|
                    |***************************************************|
                    
                    1 - Adicionar Livro por Nome
                    2 - Livros buscados
                    3 - Buscar livro por Nome
                    4 - Buscar todos os Autores de livros buscados
                    5 - Buscar Autores por ano
                    6 - Buscar Livros por Idioma
                    7 - Top 10 Livros mais Baixados
                    8 - Buscar Autor por Nome
                   
                    
               
                    0 - Sair
                    
                    |***************************************************|
                    |*****            INSIRA UMA OPÇÃO            ******|
                    |***************************************************|
                    """;

            try {
                System.out.println(menu);
                opcao = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {

                System.out.println("|****************************************|");
                System.out.println("|  Por favor, insira um número válido.   |");
                System.out.println("|****************************************|\n");
                sc.nextLine();
                continue;
            }

            switch (opcao){
                case 1:
                    buscarLivroNaWeb();
                    break;
                case 2:
                    livrosBuscados();
                    break;
                case 3:
                    buscarLivroPorNome();
                    break;
                case 4:
                    BuscarAutores();
                    break;
                case 5:
                    buscarAutoresPorAno();
                    break;
                case 6:
                    buscarLivrosPorIdioma();
                    break;
                case 7:
                    top10LivrosMaisBaixados();
                    break;
                case 8:
                    buscarAutorPorNome();
                    break;
                case 0:
                    opcao = 0;
                    System.out.println("|********************************|");
                    System.out.println("|    Aplicação fechada. Tchau!   |");
                    System.out.println("|********************************|\n");
                    break;
                default:
                    System.out.println("|*********************|");
                    System.out.println("|  Opção Incorreta.   |");
                    System.out.println("|*********************|\n");
                    System.out.println("Tente uma nova Opção");
                    consumo();
                    break;
            }
        }
    }

    private Livro getDadosLivro(){
        System.out.println("Insira o nome do livro: ");
        var nomeLivro = sc.nextLine().toLowerCase();
        var json = consumoApi.obterDados(API_BASE + nomeLivro.replace(" ", "%20"));
        LivrosRespostaApi dados = converter.converterDadosJsonParaJava(json, LivrosRespostaApi.class);

            if (dados != null && dados.getResultadoLivros() != null && !dados.getResultadoLivros().isEmpty()) {
                DadosLivro primeiroLivro = dados.getResultadoLivros().get(0); // Obter o primeiro livro da lista
                return new Livro(primeiroLivro);
            } else {
                System.out.println("Nenhum resultado encontrado.");
                return null;
            }
    }


    private void buscarLivroNaWeb() {
        Livro livro = getDadosLivro();

        if (livro == null){
            System.out.println("Livro não encontrado. O valor é null");
            return;
        }

        try{
            boolean livroExiste = livroRepository.existsByTitulo(livro.getTitulo());
            if (livroExiste){
                System.out.println("O livro já existe no banco de dados!");
            }else {
                livroRepository.save(livro);
                System.out.println(livro.toString());
            }
        }catch (InvalidDataAccessApiUsageException e){
            System.out.println("Não é possível persistir o livro buscado!");
        }
    }

    @Transactional(readOnly = true)
    private void livrosBuscados(){
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no banco de dados.");
        } else {
            System.out.println("Livros encontrados no banco de dados:");
            for (Livro livro : livros) {
                System.out.println(livro.toString());
            }
        }
    }

    private void buscarLivroPorNome() {
        System.out.println("Insira o título do livro que deseja buscar: ");
        var titulo = sc.nextLine();
        Livro livroBuscado = livroRepository.findByTituloContainsIgnoreCase(titulo);
        if (livroBuscado != null) {
            System.out.println("O livro encontrado foi: " + livroBuscado);
        } else {
            System.out.println("O livro com o título '" + titulo + "' não foi encontrado.");
        }
    }

    private  void BuscarAutores(){
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado no banco de dados. \n");
        } else {
            System.out.println("Autores encontrados no banco de dados: \n");
            Set<String> autoresUnicos = new HashSet<>();
            for (Autor autor : autores) {
                if (autoresUnicos.add(autor.getNome())){
                    System.out.println(autor.getNome() + '\n');
                }
            }
        }
    }

    private void  buscarLivrosPorIdioma(){
        System.out.println("Insira o idioma em que deseja buscar: \n");
        System.out.println("|***********************************|");
        System.out.println("|  Opção - es : Livros em espanhol. |");
        System.out.println("|  Opção - en : Livros em inglês.   |");
        System.out.println("|***********************************|\n");

        var idioma = sc.nextLine();
        List<Livro> livrosPorIdioma = livroRepository.findByIdioma(idioma);

        if (livrosPorIdioma.isEmpty()) {
            System.out.println("Nenhum livro encontrado no banco de dados.");
        } else {
            System.out.println("Livros encontrados no banco de dados segundo o idioma:");
            for (Livro livro : livrosPorIdioma) {
                System.out.println(livro.toString());
            }
        }

    }

    private void buscarAutoresPorAno() {
        System.out.println("Indique o ano para consultar quais autores estavam vivos: \n");
        var anoBuscado = sc.nextInt();
        sc.nextLine();

        List<Autor> autoresVivos = autorRepository.findByDataNascimentoLessThanOrDataFalecimentoGreaterThanEqual(anoBuscado, anoBuscado);

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor encontrado que estivesse vivo no ano " + anoBuscado + ".");
        } else {
            System.out.println("Os autores que estavam vivos no ano " + anoBuscado + " são:");
            Set<String> autoresUnicos = new HashSet<>();

            for (Autor autor : autoresVivos) {
                if (autor.getDataNascimento() != null && autor.getDataFalecimento() != null) {
                    if (autor.getDataNascimento() <= anoBuscado && autor.getDataFalecimento() >= anoBuscado) {
                        if (autoresUnicos.add(autor.getNome())) {
                            System.out.println("Autor: " + autor.getNome());
                        }
                    }
                }
            }
        }
    }

    private void top10LivrosMaisBaixados(){
        List<Livro> top10Livros = livroRepository.findTop10ByTituloByQuantidadeDownloads();
        if (!top10Livros.isEmpty()){
            int index = 1;
            for (Livro livro : top10Livros){
                System.out.printf("Livro %d: %s Autor: %s Downloads: %d\n",
                        index, livro.getTitulo(), livro.getAutor().getNome(), livro.getQuantidadeDownloads());
                index++;
            }
        }
    }

    private void buscarAutorPorNome() {
        System.out.println("Insira o nome do autor que deseja buscar: ");
        var escritor = sc.nextLine();
        Optional<Autor> escritorBuscado = autorRepository.findFirstByNomeContainsIgnoreCase(escritor);
        if (escritorBuscado.isPresent()) {
            System.out.println("O autor encontrado foi: " + escritorBuscado.get());
        } else {
            System.out.println("O autor com o nome '" + escritor + "' não foi encontrado.");
        }
    }
}