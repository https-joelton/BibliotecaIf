package View;

import Controller.UsuarioController;
import Model.UsuarioModel;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class CadastroUsuarioView extends JFrame{
    private JPanel panel1;
    private JTextField textFieldNome;
    private JRadioButton masculinoRadioButton;
    private JRadioButton femininoRadioButton;
    private JRadioButton naoDefinidoRadioButton;
    private JButton voltarButton;
    private JButton salvarButton;
    private JFormattedTextField formattedTextFieldCelular;
    private JTextField textFieldEmail;

    public CadastroUsuarioView() {
        this.setTitle("Sistema de Gestão de Biblioteca - Cadastro de Usuários");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.setSize(640, 480);
        this.setVisible(true);

        ButtonGroup sexoSelecionado = new ButtonGroup();
        sexoSelecionado.add(masculinoRadioButton);
        sexoSelecionado.add(femininoRadioButton);
        sexoSelecionado.add(naoDefinidoRadioButton);

        try {
            MaskFormatter celular = new MaskFormatter("(##) #####-####");
            celular.install(formattedTextFieldCelular);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Erro ao configurar a máscara para celular: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        salvarButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioModel usuarios = new UsuarioModel();

                usuarios.setNome(textFieldNome.getText());
                if (masculinoRadioButton.isSelected()) {
                    usuarios.setSexo("Masculino");
                } else if (femininoRadioButton.isSelected()) {
                    usuarios.setSexo("Feminino");
                } else if (naoDefinidoRadioButton.isSelected()) {
                    usuarios.setSexo("Não Definido");
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecione uma opção!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                usuarios.setCelular(formattedTextFieldCelular.getText());
                String email = textFieldEmail.getText();
                if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Email inválido! Insira um email no formato correto.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                usuarios.setEmail(email);

                try {
                    UsuarioController usuarioController = new UsuarioController();
                    String mensagem = usuarioController.salvarController(usuarios);
                    JOptionPane.showMessageDialog(null, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    Principal telaInicial = new Principal();
                    telaInicial.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuUsuarioView telamenu = new MenuUsuarioView();
                telamenu.setVisible(true);
                dispose();
            }
        });
    }
}
