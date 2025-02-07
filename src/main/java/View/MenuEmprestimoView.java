package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuEmprestimoView extends JFrame{
    private JPanel panel1;
    private JButton emprestarLivroButton;
    private JButton voltarButton;
    private JButton listarEmprestimosButton;

    public MenuEmprestimoView(){
        this.setTitle("Empréstimo de Livro");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(panel1);
        this.setSize(640, 480);
        this.setVisible(true);

        emprestarLivroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListarLivroEmprestimoView buscarLivros = new ListarLivroEmprestimoView();
                buscarLivros.setVisible(true);
                dispose();
            }
        });

        listarEmprestimosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               ListarEmprestimos listarEmprestimos = new ListarEmprestimos();
                listarEmprestimos.setVisible(true);
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
    }
}
