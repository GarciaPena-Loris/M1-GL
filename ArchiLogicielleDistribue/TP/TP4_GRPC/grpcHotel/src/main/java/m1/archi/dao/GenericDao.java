package m1.archi.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class GenericDao<T> {
    private final Logger logger = Logger.getLogger(GenericDao.class.getName());
    private final Class<T> entityClass;

    public GenericDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T findById(Long id) {
        try (// Crée une instance de EntityManagerFactory à partir de l'unité de persistence (cf. persistence.xml)
             EntityManagerFactory emf = Persistence.createEntityManagerFactory("grpcHotel");
             // Crée une instance de EntityManager pour effectuer des opérations de persistance
             EntityManager em = emf.createEntityManager()) {

            // Recherche l'entité par son id
            T entity = em.find(entityClass, id);

            if (entity == null) {
                throw new NoSuchElementException("Aucun enregistrement avec l'id '" + id + "' n'a été trouvé.");
            }

            return entity;
        }
    }

    public void create(T entity) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("grpcHotel");
             EntityManager em = emf.createEntityManager()) {
            // Crée une instance de EntityManager pour effectuer des opérations de persistance
            EntityTransaction transaction = em.getTransaction();

            // Démarre une transaction
            transaction.begin();

            // Ajoute l'entité à la base de données
            em.persist(entity);

            // Valide la transaction
            transaction.commit();
        } catch (Exception e) {
            logger.severe("Erreur lors de la création de l'entité : " + e.getMessage());
            throw new RuntimeException("Erreur lors de la création de l'entité.", e);
        }
    }

    public void update(T entity) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("grpcHotel");
             EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();

            transaction.begin();
            em.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            logger.severe("Erreur lors de la mise à jour de l'entité : " + e.getMessage());
            throw new RuntimeException("Erreur lors de la mise à jour de l'entité.", e);
        }
    }
}
