package View;

import Controller.LivroController;
import Model.LivroModel;
import Repository.LivroRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class BuscarLivros extends JFrame{
    private LivroController livroController = new LivroController();
    private JPanel panel1;
    private JTable tableLivrosDisponiveis;
    private JScrollPane scrollLivros;
    private JButton buttonDeletar;
    private JButton buttonVoltar;

    public BuscarLivros(){
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
                Principal telainicial = new Principal();
                telainicial.setVisible(true);
                dispose();
            }
        });

        buttonDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableLivrosDisponiveis.getSelectedRow();
                if(linhaSelecionada != -1) {
                    Long idLivroSelecionado = Long.parseLong(tableLivrosDisponiveis.getValueAt(linhaSelecionada,0).toString());
                    try {
                        JOptionPane.showMessageDialog(null, livroController.deletarController(idLivroSelecionado));
                        TabelaLivros livroDisponiveis = new TabelaLivros();
                        tableLivrosDisponiveis.setModel(livroDisponiveis);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Selecione o registro que deseja remover");
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
