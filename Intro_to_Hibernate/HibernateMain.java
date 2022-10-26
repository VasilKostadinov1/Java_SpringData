import entities.Town;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateMain {

    public static void main(String[] args) {

        //this is code with native hibernate, must define hibernate configuration
        //cfg file is NOT IN META-INF folder, only in resources
        //According to that who is a main file use, will be enabled different configuration
        // We must create mapping file

        Configuration configuration = new Configuration();
        configuration.configure();

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session currentSession = sessionFactory.openSession();
        currentSession.beginTransaction();

        Town town = currentSession.get(Town.class,1);
        System.out.println(town);


        currentSession.getTransaction().commit();
        currentSession.close();







    }
}
