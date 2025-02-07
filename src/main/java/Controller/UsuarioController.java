package Controller;

import Model.UsuarioModel;
import Repository.UsuarioRepository;

import java.sql.SQLException;

public class UsuarioController {
    private UsuarioRepository usuarioRepository = new UsuarioRepository();

    public String salvarController (UsuarioModel usuario) throws SQLException {
        return usuarioRepository.salvarRepository(usuario);
    }
    public String deletarController(Long idUsuarioSelecionado) throws SQLException {
        UsuarioModel usuario = usuarioRepository.buscarPorId(idUsuarioSelecionado);
        return usuarioRepository.deletarRepository(usuario);
    }

    public String atualizarController(UsuarioModel usuario) throws SQLException {
        return usuarioRepository.atualizarRepository(usuario);
    }

    public UsuarioModel buscarUsuarioPorId(Long idUsuarioSelecionado) {
        UsuarioRepository usuario = new UsuarioRepository();
        return usuario.buscarPorId(idUsuarioSelecionado);
    }
}
