import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class AddressesWithEmployeeCount_07 {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Address> addressList = entityManager
                .createQuery("SELECT a FROM Address a " +
                        " ORDER BY a.employees.size DESC ", Address.class)
                .setMaxResults(10)
                .getResultList();

        addressList
                .forEach(address -> {
                    System.out.printf("%s, %s - %d employees%n",
                            address.getText(),
                            address.getTown() == null
                                    ? "Unknown"
                                    : address.getTown().getName(),
                            address.getEmployees().size());
                });
        entityManager.getTransaction().commit();
    }
}
