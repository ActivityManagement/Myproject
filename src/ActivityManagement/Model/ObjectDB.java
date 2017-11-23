package ActivityManagement.Model;

import ActivityManagement.PropertiesDBLoader;
import javax.persistence.*;

public class ObjectDB {

    private String PATH = "objectdb://localhost/";
    private String user = "user=admin";
    private String password = "password=admin";
    private EntityManagerFactory emf;
    private EntityManager em;

    public ObjectDB()
    {
        PropertiesDBLoader.load();
        this.PATH = "objectdb://"+PropertiesDBLoader.getDatabase()+"/";
        this.user = "user="+PropertiesDBLoader.getUser();
        this.password = "password="+PropertiesDBLoader.getPass();
    }
    public EntityManager createConnection(String file)
    {
        emf = Persistence.createEntityManagerFactory(PATH+file+";"+user+";"+password);
        em = emf.createEntityManager();
        return em;
    }

    public void closeConnection()
    {
        em.close();
        emf.close();
    }

    public void saveObject(Object obj)
    {
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
    }

    public boolean isRecordExist(String entity) {
        String query = "select count(e) from "+entity+" e";
        // you will always get a single result
        Long count = (Long) em.createQuery( query ).getSingleResult();
        return ( ( count.equals( 0L ) ) ? false : true );
    }

}
