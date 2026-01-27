package persistence;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;


@ApplicationScoped
public class EntityManagerProducer {

   private EntityManagerFactory emf;

   @PostConstruct
   public void init(){
    this.emf = Persistence.createEntityManagerFactory("avanzada");

   }

   public EntityManager entityManager(){
    return this.emf.createEntityManager();

   }


   @PreDestroy
   public void destroy(){
    if(emf != null && emf.isOpen()){
        emf.close();


    }


   }



}
