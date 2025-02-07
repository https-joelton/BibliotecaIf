package View;

import Controller.LivroController;
import Controller.UsuarioController;
import Model.UsuarioModel;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class EditarUsuarioView extends JFrame {
    private final UsuarioModel usuario;
    private JPanel panel1;
    private JTextField textFieldNome;
    private JRadioButton masculinoRadioButton;
    private JRadioButton femininoRadioButton;
    private JRadioButton naoDefinidoRadioButton;
    private JFormattedTextField formattedTextFieldCelular;
    private JTextField textFieldEmail;
    private JButton voltarButton;
    private JButton atualizarButton;

    public EditarUsuarioView(UsuarioModel usuarioSelecionado) {
        this.usuario = usuarioSelecionado;
        this.setTitle("Edição de Usuário");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        textFieldNome.setText(usuario.getNome());
        if (usuario.getSexo().equalsIgnoreCase("Masculino")) {
            masculinoRadioButton.setSelected(true);
        } else if (usuario.getSexo().equalsIgnoreCase("Feminino")) {
            femininoRadioButton.setSelected(true);
        } else {
            naoDefinidoRadioButton.setSelected(true);
        }
        formattedTextFieldCelular.setText(usuario.getCelular());
        textFieldEmail.setText(usuario.getEmail());

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuario.setNome(textFieldNome.getText());
                if (masculinoRadioButton.isSelected()) {
                    usuario.setSexo("Masculino");
                } else if (femininoRadioButton.isSelected()) {
                    usuario.setSexo("Feminino");
                } else if (naoDefinidoRadioButton.isSelected()) {
                    usuario.setSexo("Não Definido");
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecione uma opção!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                usuario.setCelular(formattedTextFieldCelular.getText());
                usuario.setEmail(textFieldEmail.getText());
                try {
                    UsuarioController usuarioController = new UsuarioController();
                    JOptionPane.showMessageDialog(null, usuarioController.atualizarController(usuario));
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
