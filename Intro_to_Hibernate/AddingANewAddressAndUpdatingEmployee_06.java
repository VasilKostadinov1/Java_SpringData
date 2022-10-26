import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class AddingANewAddressAndUpdatingEmployee_06 {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee last name: ");
        String lastName = scanner.nextLine();


        Address address = new Address();
        address.setText("Vitoshka 15");

        entityManager.getTransaction().begin();

        entityManager.persist(address);

        entityManager.createQuery("UPDATE Employee e SET e.address = :address " +
                        "WHERE e.lastName = :last_name")
                .setParameter("address", address)
                .setParameter("last_name", lastName)
                .executeUpdate();

        entityManager.getTransaction().commit();
    }
}
