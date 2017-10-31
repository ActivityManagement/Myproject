package ActivityManagement.Model;

import javax.persistence.*;

public class ObjectDB {

    private final String PATH = "objectdb://localhost/";
    private final String user = "user=admin";
    private final String password = "password=admin";
    private EntityManagerFactory emf;
    private EntityManager em;

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
