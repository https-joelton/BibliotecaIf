package Controller;

import Model.EmprestimoModel;
import Model.LivroModel;
import Repository.EmprestimoRepository;
import Repository.LivroRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EmprestimoController {
    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;

    public EmprestimoController() {
        this.emprestimoRepository = new EmprestimoRepository();
        this.livroRepository = new LivroRepository();
    }
    public String realizarEmpresitmoController(EmprestimoModel emprestimo) throws SQLException {
        LivroModel livro = emprestimo.getLivro();
        livro.setQuantidade(livro.getQuantidade() - 1);
        livroRepository.atualizarRepository(livro);

        emprestimo.setDataEmprestimo(LocalDate.now());
        return emprestimoRepository.realizarEmprestimoRepository(emprestimo);
    }
    public int quantidadeEmprestimos(Long idUsuario) throws SQLException {
        return Math.toIntExact(emprestimoRepository.quantidadeRepository(idUsuario));
    }

    public EmprestimoModel buscarEmprestimoPorId(Long idEmprestimoSelecionado) {
        EmprestimoRepository emprestimo = new EmprestimoRepository();
        return emprestimo.buscarPorId(idEmprestimoSelecionado);
    }

    public String confirmarDevolucao(EmprestimoModel emprestimo) throws SQLException {
        return emprestimoRepository.atualizarRepository(emprestimo);
    }

    public double calcularMulta(EmprestimoModel emprestimo) {
        if (emprestimo.getDataDevolucao() == null) {
            LocalDate dia = LocalDate.now();
            if (dia.isAfter(emprestimo.getDataPrevisaoDevolucao())) {
                long diasAtraso = ChronoUnit.DAYS.between(emprestimo.getDataPrevisaoDevolucao(), dia);
                return diasAtraso * 3.0;
            }
        }
        return 0.0;
    }

}
