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

public class EditarLivroView extends JFrame{
    private JPanel panel1;
    private JTextField textFieldTitulo;
    private JTextField textFieldTema;
    private JTextField textFieldAutor;
    private JTextField textFieldIsbn;
    private JButton cancelarButton;
    private JButton salvarButton;
    private JFormattedTextField formattedTextFieldData;
    private JTextField textFieldQuantidade;
    private LivroModel livro;

    public EditarLivroView(LivroModel livroSelecionado){
        this.livro = livroSelecionado;
        this.setTitle("Edição de Livro");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(panel1);
        this.setSize(640, 480);
        this.setVisible(true);
        try {
            MaskFormatter data = new MaskFormatter("##/##/####");
            data.install(formattedTextFieldData);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Erro ao configurar a máscara: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        textFieldTitulo.setText(livro.getTitulo());
        textFieldTema.setText(livro.getTema());
        textFieldAutor.setText(livro.getAutor());
        textFieldIsbn.setText(livro.getIsbn());
        textFieldQuantidade.setText(String.valueOf(livro.getQuantidade()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formattedTextFieldData.setText(livro.getDatapublicacao().format(formatter));

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                livro.setTitulo(textFieldTitulo.getText());
                livro.setTema(textFieldTema.getText());
                livro.setAutor(textFieldAutor.getText());
                livro.setIsbn(textFieldIsbn.getText());
                try {
                    LocalDate dataPublicacao = LocalDate.parse(formattedTextFieldData.getText(), formatter);
                    livro.setDatapublicacao(dataPublicacao);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Data inválida! Use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    LivroController livroController = new LivroController();
                    JOptionPane.showMessageDialog(null, livroController.atualizarController(livro));
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar livro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarLivros buscarLivros = new BuscarLivros();
                buscarLivros.setVisible(true);
                dispose();
            }
        });

    }
}
