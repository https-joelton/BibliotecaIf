package View;

import Controller.UsuarioController;
import Model.UsuarioModel;
import Repository.UsuarioRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class BuscarUsuario extends JFrame{
    private UsuarioController usuarioController = new UsuarioController();
    private UsuarioModel usuarioSelecionado;
    private JPanel panel1;
    private JTable tableUsuarios;
    private JScrollPane scrollUsuario;
    private JButton deletarButton;
    private JButton voltarButton;
    private JButton atualizarDadosButton;

    public BuscarUsuario(){
        this.setTitle("Sistema de Gestão de Biblioteca - CRUD Usuários");
        TabelaUsuarios usuariosCadastrados = new TabelaUsuarios();
        tableUsuarios.setModel(usuariosCadastrados);
        tableUsuarios.setAutoCreateRowSorter(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.setSize(640, 480);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuUsuarioView menu = new MenuUsuarioView();
                menu.setVisible(true);
                dispose();
            }
        });

        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableUsuarios.getSelectedRow();
                if(linhaSelecionada != -1) {
                    Long idUsuarioSelecionado = Long.parseLong(tableUsuarios.getValueAt(linhaSelecionada,0).toString());
                    try {
                        JOptionPane.showMessageDialog(null, usuarioController.deletarController(idUsuarioSelecionado));
                        TabelaUsuarios usuarioCadastrado = new TabelaUsuarios();
                        tableUsuarios.setModel(usuarioCadastrado);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Selecione o usuário que deseja remover");
                }
            }
        });
        atualizarDadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableUsuarios.getSelectedRow();
                if(linhaSelecionada != -1) {
                    Long idUsuarioSelecionado = Long.parseLong(tableUsuarios.getValueAt(linhaSelecionada,0).toString());
                    UsuarioModel usuarioSelecionado = usuarioController.buscarUsuarioPorId(idUsuarioSelecionado);
                    EditarUsuarioView usuarioEditar = new EditarUsuarioView(usuarioSelecionado);
                    usuarioEditar.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Selecione o usuário que deseja editar");
                }
            }
        });

    }
    public UsuarioModel getUsuarioSelecionado() {
        return usuarioSelecionado;
    }
    private static class TabelaUsuarios extends AbstractTableModel {
        private UsuarioRepository usuarioRepository = new UsuarioRepository();
        private final String[] colunas = new String[] {"ID", "Nome", "Sexo", "Celular", "Email"};
        private List<UsuarioModel> listaUsuarios = usuarioRepository.buscarTodos();

        @Override
        public int getRowCount() {
            return listaUsuarios.size();
        }

        @Override
        public int getColumnCount() {
            return colunas.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            UsuarioModel usuario = listaUsuarios.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> usuario.getIdUsuario();
                case 1 -> usuario.getNome();
                case 2 -> usuario.getSexo();
                case 3 -> usuario.getCelular();
                case 4 -> usuario.getEmail();
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
