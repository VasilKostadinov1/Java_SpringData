import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Set;

public class increaseSalaries_10 {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager
                .createQuery("UPDATE  Employee e SET e.salary = e.salary * 1.12 " +
                        " WHERE e.department.id IN :ids ")
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .executeUpdate();

        entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.department.id IN :idsD", Employee.class)
                .setParameter("idsD", Set.of(1, 2, 4, 11))
                .getResultList().forEach(employee -> System.out.printf("%s %s ($%.2f)%n",
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary()));

        entityManager.getTransaction().commit();
    }
}
