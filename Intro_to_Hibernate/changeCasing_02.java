import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class changeCasing_02 {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery
                ("UPDATE Town t SET t.name = UPPER(t.name) WHERE length(t.name) <= 5");

        int affectedRows = query.executeUpdate();
        System.out.println("Affected rows is" + " " + + affectedRows + ".");

//        Query from_Town =
//                entityManager.createQuery("SELECT t FROM Town t", Town.class);
//        List<Town> resultList = from_Town.getResultList();
//
//        for (Town town : resultList) {
//            String name = town.getName();
//            if (name.length() <= 5 ){
//                String toUpper = name.toUpperCase();
//                town.setName(toUpper);
//                entityManager.persist(town);
//            }
//        }


        entityManager.getTransaction().commit();
    }
}
