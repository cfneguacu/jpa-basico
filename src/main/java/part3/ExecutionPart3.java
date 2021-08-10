package part3;

import classes.Aluno;
import classes.Aluno_;
import classes.Estado;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ExecutionPart3 {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Part2-DTO");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Estado estadoParaAdicionar = new Estado("Rio de Janeiro", "RJ");
        entityManager.persist(estadoParaAdicionar);
        entityManager.persist(new Estado("São Paulo", "SP"));
        entityManager.persist(new Aluno("Daniel", 29, estadoParaAdicionar));
        entityManager.persist(new Aluno("João", 20, estadoParaAdicionar));
        entityManager.persist(new Aluno("Pedro", 30, estadoParaAdicionar));
        entityManager.getTransaction().commit();

        String nome = "Daniel";

        //Aluno alunoEntityManager = entityManager.find(Aluno.class,1);
        //System.out.println("Consulta alunoEntityManager" + alunoEntityManager);

        String jpql = "select a from Aluno a where a.nome = :nome";
         Aluno alunoJPQL = entityManager
            .createQuery(jpql, Aluno.class)
            .setParameter("nome", nome)
            .getSingleResult();

        String jpqlList = "SELECT a FROM Aluno a";
        List<Aluno> alunoJPQLList = entityManager
                .createQuery(jpqlList,Aluno.class)
                .getResultList();

        System.out.println("Consulta alunoJPQL" + alunoJPQL);
        alunoJPQLList.forEach(Aluno -> System.out.println("Consulta alunoJPQLList "+ Aluno));

        CriteriaQuery<Aluno> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Aluno.class);
        Root<Aluno> alunoRoot = criteriaQuery.from(Aluno.class);
        CriteriaBuilder.In<String> inClause = entityManager.getCriteriaBuilder().in(alunoRoot.get(Aluno_.nome));
        inClause.value(nome);
        criteriaQuery.select(alunoRoot).where(inClause);
        Aluno alunoAPICriteria = entityManager.createQuery(criteriaQuery).getSingleResult();

        CriteriaQuery<Aluno> criteriaQueryList = entityManager.getCriteriaBuilder().createQuery(Aluno.class);
        Root<Aluno> alunoRootList = criteriaQueryList.from(Aluno.class);
        List<Aluno> alunoAPICriteriaList = entityManager.createQuery(criteriaQueryList).getResultList();

        System.out.println("Consulta alunoAPICriteria: "+alunoAPICriteria);
        alunoAPICriteriaList.forEach(Aluno -> System.out.println("Consulta alunoAPICriteriaList "+ Aluno));
    }

}
