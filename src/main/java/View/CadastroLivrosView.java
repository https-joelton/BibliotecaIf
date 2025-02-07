package View;

import Controller.LivroController;
import Model.LivroModel;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CadastroLivrosView extends JFrame{
    private JPanel panel1;
    private JPanel JPaneLivros;
    private JTextField textFieldTitulo;
    private JTextField textFieldTema;
    private JTextField textFieldAutor;
    private JTextField textFieldIsbn;
    private JTextField textFieldQuantidade;
    private JButton buttonSalvarLivro;
    private JButton buttonVoltar;
    private JFormattedTextField formattedTextFieldData;

    public CadastroLivrosView() {
        this.setTitle("Sistema de Gestão de Biblioteca - CRUD Livros");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.setSize(640, 480);
        this.setVisible(true);

        try {
            MaskFormatter data = new MaskFormatter("##/##/####");
            data.install(formattedTextFieldData);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Erro ao configurar a máscara para data: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        buttonSalvarLivro.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LivroModel livros = new LivroModel();
                livros.setTitulo(textFieldTitulo.getText());
                livros.setTema(textFieldTema.getText());
                livros.setAutor(textFieldAutor.getText());
                livros.setIsbn(textFieldIsbn.getText());
                String data = formattedTextFieldData.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataPublicacao = LocalDate.parse(data, formatter);
                livros.setDatapublicacao(dataPublicacao);
                livros.setQuantidade(Integer.parseInt(textFieldQuantidade.getText()));

                try {
                    LivroController livroController = new LivroController();
                    JOptionPane.showMessageDialog(null, livroController.salvarController(livros));
                    Principal telainicial = new Principal();
                    telainicial.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Principal telainicial = new Principal();
                telainicial.setVisible(true);
                dispose();
            }
        });


    }
}
