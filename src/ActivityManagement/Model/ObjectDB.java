package ActivityManagement.Model;

import javax.persistence.*;

public class ObjectDB {

    private final String PATH = "$objectdb/db/";
    private EntityManagerFactory emf;
    private EntityManager em;

    public EntityManager createConnection(String file)
    {
        emf = Persistence.createEntityManagerFactory(PATH+file);
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

}
