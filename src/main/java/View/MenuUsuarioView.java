package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuUsuarioView extends JFrame{
    private JPanel panel1;
    private JButton buttonCadastrar;
    private JButton listarUsuariosButton;
    private JButton voltarButton;

    public MenuUsuarioView(){
        this.setTitle("Sistema de Gestão de Biblioteca - CRUD Usuários");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.setSize(640, 480);
        this.setVisible(true);

        buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastroUsuarioView usuarioView = new CadastroUsuarioView();
                usuarioView.setVisible(true);
                dispose();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Principal telainicial = new Principal();
                telainicial.setVisible(true);
                dispose();
            }
        });

        listarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarUsuario usuariobuscados = new BuscarUsuario();
                usuariobuscados.setVisible(true);
                dispose();
            }
        });
    }
}
