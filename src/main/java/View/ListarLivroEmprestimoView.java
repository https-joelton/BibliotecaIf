package View;

import Controller.EmprestimoController;
import Controller.LivroController;
import Model.EmprestimoModel;
import Model.LivroModel;
import Model.UsuarioModel;
import Repository.LivroRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ListarLivroEmprestimoView extends JFrame{
    private LivroController livroController = new LivroController();
    private JPanel panel1;
    private JTable tableLivrosDisponiveis;
    private JButton emprestarButton;
    private JButton buttonVoltar;


    public ListarLivroEmprestimoView(){
        this.setTitle("Sistema de Gestão de Biblioteca - CRUD Livros");
        TabelaLivros livrosDisponiveisTabela = new TabelaLivros();
        tableLivrosDisponiveis.setModel(livrosDisponiveisTabela);
        tableLivrosDisponiveis.setAutoCreateRowSorter(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.setSize(640, 480);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        buttonVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuEmprestimoView telainicial = new MenuEmprestimoView();
                telainicial.setVisible(true);
                dispose();
            }
        });

        emprestarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmprestimoController emprestimoController = new EmprestimoController();
                int linhaSelecionada = tableLivrosDisponiveis.getSelectedRow();
                if(linhaSelecionada != -1) {
                    Long idLivroSelecionado = Long.parseLong(tableLivrosDisponiveis.getValueAt(linhaSelecionada,0).toString());
                    LivroModel livroSelecionado = livroController.buscarLivroPorId(idLivroSelecionado);
                    if (livroSelecionado.getQuantidade() > 0){
                        JOptionPane.showMessageDialog(null, "Selecione o Usuário que deseja Emprestar:");
                        ListarUsuariosEmprestimoView usuario = new ListarUsuariosEmprestimoView();
                        usuario.setVisible(true);

                        usuario.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                UsuarioModel usuarioSelecionado = usuario.getUsuarioSelecionado();
                                if (usuarioSelecionado != null) {
                                    int livrosEmprestados = 0;
                                    try {
                                        livrosEmprestados = emprestimoController.quantidadeEmprestimos(usuarioSelecionado.getIdUsuario());
                                    } catch (SQLException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    if (livrosEmprestados >= 5) {
                                        JOptionPane.showMessageDialog(null, "Limite de 5 livros atingido!");
                                        return;
                                    }

                                    EmprestimoModel emprestimo = new EmprestimoModel();
                                    emprestimo.setLivro(livroSelecionado);
                                    emprestimo.setUsuario(usuarioSelecionado);
                                    emprestimo.setDataEmprestimo(LocalDate.now());
                                    emprestimo.setDataPrevisaoDevolucao(LocalDate.now().plusDays(-1));

                                    try {
                                        emprestimoController.realizarEmpresitmoController(emprestimo);
                                        livroController.atualizarController(livroSelecionado);
                                        JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso!");
                                        tableLivrosDisponiveis.setModel(new TabelaLivros());

                                    } catch (SQLException ex) {
                                        JOptionPane.showMessageDialog(null, "Erro ao registrar empréstimo: " + ex.getMessage());
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Selecione um Usuário!");
                                }
                            }
                        });
                    }else {
                        JOptionPane.showMessageDialog(null, "Livro Indisponível no momento!");
                    }

                }else{
                    JOptionPane.showMessageDialog(null, "Selecione o registro que deseja editar");
                }

            }
        });

    }

    private static class TabelaLivros extends AbstractTableModel {
        private LivroRepository livroRepository = new LivroRepository();
        private final String[] colunas = new String[] {"ID", "Título", "Autor", "Tema", "ISBN", "Quantidade", "Data de Publicação"};
        private List<LivroModel> listaLivros = livroRepository.buscarTodos();

        @Override
        public int getRowCount() {
            return listaLivros.size();
        }

        @Override
        public int getColumnCount() {
            return colunas.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            LivroModel livro = listaLivros.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> livro.getIdLivro();
                case 1 -> livro.getTitulo();
                case 2 -> livro.getAutor();
                case 3 -> livro.getTema();
                case 4 -> livro.getIsbn();
                case 5 -> livro.getQuantidade();
                case 6 -> livro.getDatapublicacao();
                default -> "-";
            };
        }


        @Override
        public String getColumnName(int columnIndex){
            return colunas[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if(getValueAt(0,columnIndex) != null){
                return getValueAt(0, columnIndex).getClass();
            }else {
                return Object.class;
            }
        }

    }
}
