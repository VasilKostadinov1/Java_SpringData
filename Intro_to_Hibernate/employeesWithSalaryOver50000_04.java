import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class employeesWithSalaryOver50000_04 {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createQuery("SELECT e FROM Employee e " +
                                " WHERE e.salary > :min_salary ",
                        Employee.class)
                .setParameter("min_salary", BigDecimal.valueOf(50000L))
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);


        entityManager.getTransaction().commit();
    }
}
