package Repository;

import Model.UsuarioModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

public class UsuarioRepository {
    private static UsuarioRepository instance;
    protected EntityManager entityManager;

    public UsuarioRepository() {
        entityManager = getEntityManager();
    }
    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }
    public static UsuarioRepository getInstance() {
        if (instance == null) {
            instance = new UsuarioRepository();
        }
        return instance;
    }

    public String salvarRepository(UsuarioModel usuario) throws SQLException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
            return "Usuário Cadastrado com Sucesso!";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public List<UsuarioModel> buscarTodos() {
        return entityManager.createQuery("from UsuarioModel", UsuarioModel.class).getResultList();
    }

    public UsuarioModel buscarPorId(Long id) {
        UsuarioModel usuario = new UsuarioModel();
        try {
            usuario = entityManager.find(UsuarioModel.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public String deletarRepository (UsuarioModel usuario){
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(usuario);
            entityManager.getTransaction().commit();
            return "Deletado com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public String atualizarRepository(UsuarioModel usuario) throws SQLException {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();
            return "Dados Atualizado com Sucesso!";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
