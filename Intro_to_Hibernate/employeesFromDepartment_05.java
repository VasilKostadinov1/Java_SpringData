import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class employeesFromDepartment_05 {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createQuery("SELECT e FROM Employee e " +
                        " WHERE e.department.name = :d_name " +
                        "ORDER BY e.salary ASC, e.id ASC ", Employee.class)
                .setParameter("d_name", "Research and Development")
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s from %s - $%.2f%n",
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getDepartment().getName(),
                            employee.getSalary());
                });

        entityManager.getTransaction().commit();
    }
}
