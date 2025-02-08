package View;

import Controller.EmprestimoController;
import Controller.LivroController;
import Model.EmprestimoModel;
import Model.LivroModel;
import Repository.EmprestimoRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ListarEmprestimos extends JFrame{
    private JPanel panel1;
    private JTable tableEmprestimos;
    private JButton devolucaoButton;
    private JButton voltarButton;

    public ListarEmprestimos(){
        EmprestimoController emprestimoController = new EmprestimoController();
        LivroController livroController = new LivroController();
        TabelaEmprestimo emprestimosAndamento = new TabelaEmprestimo();
        tableEmprestimos.setModel(emprestimosAndamento);
        tableEmprestimos.setAutoCreateRowSorter(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.setSize(640, 480);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        devolucaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableEmprestimos.getSelectedRow();

                if (linhaSelecionada != -1) {
                    Long idEmprestimoSelecionado = Long.parseLong(tableEmprestimos.getValueAt(linhaSelecionada, 0).toString());
                    EmprestimoModel emprestimo = emprestimoController.buscarEmprestimoPorId(idEmprestimoSelecionado);

                    if (emprestimo != null && emprestimo.getDataDevolucao() == null) {
                        double multa = emprestimoController.calcularMulta(emprestimo);
                        if (multa >0){
                            JOptionPane.showMessageDialog(null, "Empréstimo atrasado!\nMulta: R$" + multa);
                            emprestimo.setMulta(multa);
                        }
                        try {
                            LivroModel livro = emprestimo.getLivro();
                            livro.setQuantidade(livro.getQuantidade() + 1);
                            emprestimo.setDataDevolucao(LocalDate.now());
                            emprestimoController.confirmarDevolucao(emprestimo);
                            livroController.atualizarController(livro);
                            JOptionPane.showMessageDialog(null, "Livro devolvido com sucesso!");
                            tableEmprestimos.setModel(new TabelaEmprestimo());

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Erro ao registrar devolução: " + ex.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Empréstimo já devolvido!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um empréstimo para devolver.");
                }
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuEmprestimoView menu = new MenuEmprestimoView();
                menu.setVisible(true);
                dispose();
            }
        });

    }

    private static class TabelaEmprestimo extends AbstractTableModel {
        private EmprestimoRepository emprestimoRepository = new EmprestimoRepository();
        private final String[] colunas = new String[]{"ID", "Livro", "Usuário", "Previsão Devolução", "Data Devolução", "Multa"};
        private List<EmprestimoModel> listaEmprestimo = emprestimoRepository.buscarTodos();

        @Override
        public int getRowCount() {
            return listaEmprestimo.size();
        }

        @Override
        public int getColumnCount() {
            return colunas.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            EmprestimoModel emprestimo = listaEmprestimo.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> emprestimo.getIdEmprestimo();
                case 1 -> emprestimo.getLivro().getTitulo();
                case 2 -> emprestimo.getUsuario().getNome();
                case 3 -> emprestimo.getDataPrevisaoDevolucao();
                case 4 -> emprestimo.getDataDevolucao();
                case 5 -> emprestimo.getMulta();
                default -> "-";
            };
        }


        @Override
        public String getColumnName(int columnIndex) {
            return colunas[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
}
