package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame{
    private JPanel panel1;
    private JButton buttonLivro;
    private JButton buttonUsuario;
    private JPanel jpanePrincipal;
    private JLabel logoLabel;
    private JLabel jlabelTitle;
    private JButton buttonLivrosDisponiveis;
    private JButton sairButton;
    private JButton emprestimoButton;

    public Principal() {
    this.setTitle("Sistema de Gestão de Biblioteca");
    this.setContentPane(panel1);
    this.setSize(640,480);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/logo.png"));
        Image dimensaoImagem = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(dimensaoImagem);
        logoLabel.setIcon(logoIcon);
    this.setVisible(true);

    buttonLivro.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            CadastroLivrosView livrosView = new CadastroLivrosView();
            livrosView.setVisible(true);
            dispose();
        }
    });
        buttonLivrosDisponiveis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarLivros livrosbuscados = new BuscarLivros();
                livrosbuscados.setVisible(true);
                dispose();
            }
        });
        buttonUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuUsuarioView usuarioView = new MenuUsuarioView();
                usuarioView.setVisible(true);
                dispose();
            }
        });
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        emprestimoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuEmprestimoView emprestimoView = new MenuEmprestimoView();
                emprestimoView.setVisible(true);
                dispose();
            }
        });
}
}
