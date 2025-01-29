package Repository;

import Model.LivroModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroRepository {
    private static LivroRepository instance;
    protected EntityManager entityManager;
    public LivroRepository() {
        entityManager = getEntityManager();
    }
    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }
    public static LivroRepository getInstance() {
        if (instance == null) {
            instance = new LivroRepository();
        }
        return instance;
    }

    public String salvarRepository(LivroModel livro) throws SQLException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(livro);
            entityManager.getTransaction().commit();
            return "Livro Cadastrado com Sucesso!";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public List<LivroModel> buscarTodos() {
        return entityManager.createQuery("from LivroModel", LivroModel.class).getResultList();
    }

    public LivroModel buscarPorId(Long id) {
        LivroModel livro = new LivroModel();
        try {
            livro = entityManager.find(LivroModel.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livro;
    }

    public String deletarRepository (LivroModel livro){
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(livro);
            entityManager.getTransaction().commit();
            return "Deletado com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}