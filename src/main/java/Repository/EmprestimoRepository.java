package Repository;

import Model.EmprestimoModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class EmprestimoRepository {
    private static EmprestimoRepository instance;
    protected EntityManager entityManager;

    public EmprestimoRepository() {
        entityManager = getEntityManager();
    }
    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public String realizarEmprestimoRepository(EmprestimoModel emprestimo) throws SQLException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(emprestimo);
            entityManager.getTransaction().commit();
            return "Emprestimo realizado com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<EmprestimoModel> buscarTodos() {
        return entityManager.createQuery("from EmprestimoModel", EmprestimoModel.class).getResultList();
    }
    public EmprestimoModel buscarPorId(Long id) {
        EmprestimoModel emprestimo = new EmprestimoModel();
        try {
            emprestimo = entityManager.find(EmprestimoModel.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emprestimo;
    }

    public String atualizarRepository(EmprestimoModel emprestimo) {
        entityManager.getTransaction().begin();
        entityManager.merge(emprestimo);
        entityManager.getTransaction().commit();
        return "Devolvido com sucesso";
    }

    public Long quantidadeRepository(Long idUsuario) {
        String jpql = "SELECT COUNT(e) FROM EmprestimoModel e WHERE e.usuario.idUsuario = :idUsuario AND e.dataDevolucao IS NULL";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("idUsuario", idUsuario);
        return query.getSingleResult();
    }

}
