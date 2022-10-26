import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class containsEmployee_03 {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee full name:");
        String[] fullName = scanner.nextLine().split("\\s+");
        String firstName = fullName[0];
        String lastName = fullName[1];

        Long singleResult = entityManager.createQuery("SELECT COUNT(e) FROM Employee e " +
                                "WHERE e.firstName = :f_name AND e.lastName = :l_name ",
                        Long.class)
                .setParameter("f_name", firstName)
                .setParameter("l_name", lastName)
                .getSingleResult();

        System.out.println(singleResult == 0
                ? "No"
                : "Yes");

        entityManager.getTransaction().commit();


    }
}
