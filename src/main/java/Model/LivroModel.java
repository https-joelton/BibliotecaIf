package Model;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name="idLivro")
@Table(name = "livro")
public class LivroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivro;
    private String titulo;
    private String tema;
    private String autor;
    private String isbn;
    @Column(name =  "datapublicacao")
    private LocalDate datapublicacao;
    private int quantidade;

    public Long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getDatapublicacao() {
        return datapublicacao;
    }

    public void setDatapublicacao(LocalDate datapublicacao) {
        this.datapublicacao = datapublicacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
