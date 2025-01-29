package Controller;

import Model.LivroModel;
import Repository.LivroRepository;

import java.sql.SQLException;

public class LivroController {
    private LivroRepository livroRepository = new LivroRepository();

    public String salvarController (LivroModel livro) throws SQLException {
        return livroRepository.salvarRepository(livro);
    }

    public String deletarController(Long idLivroSelecionado) throws SQLException {
        LivroModel livro = livroRepository.buscarPorId(idLivroSelecionado);
        return livroRepository.deletarRepository(livro);
    }
}
