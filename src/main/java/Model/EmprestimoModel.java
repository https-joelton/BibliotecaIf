package Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "emprestimo")
public class EmprestimoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmprestimo;
    @ManyToOne
    @JoinColumn(name = "idLivroEmprestimo", nullable = false)
    private LivroModel livro;
    @ManyToOne
    @JoinColumn(name = "idUsuarioEmprestimo", nullable = false)
    private UsuarioModel usuario;
    @Column(name = "dataEmprestimo", nullable = false)
    private LocalDate dataEmprestimo;
    @Column(name = "dataDevolucao")
    private LocalDate dataDevolucao;
    @Column(name = "dataPrevisaoDevoulacao")
    private LocalDate dataPrevisaoDevolucao;

    public Long getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(Long idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public LivroModel getLivro() {
        return livro;
    }

    public void setLivro(LivroModel livro) {
        this.livro = livro;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public LocalDate getDataPrevisaoDevolucao() {
        return dataPrevisaoDevolucao;
    }

    public void setDataPrevisaoDevolucao(LocalDate dataPrevisaoDevolucao) {
        this.dataPrevisaoDevolucao = dataPrevisaoDevolucao;
    }
}