import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class employeesMaximumSalaries_12 {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager.createNativeQuery("SELECT d.name, MAX(e.salary) AS m_salary FROM departments d " +
                        "join employees e on d.department_id = e.department_id " +
                        "GROUP BY d.name " +
                        "HAVING m_salary NOT BETWEEN 30000 AND 70000; ")
                .getResultList();

        for (Object[] array : rows) {
            for (Object i : array) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        entityManager.getTransaction().commit();
    }
}
