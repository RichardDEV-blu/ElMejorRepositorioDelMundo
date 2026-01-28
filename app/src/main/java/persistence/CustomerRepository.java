package persistence;

import java.util.List;

import domain.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class CustomerRepository {

    private EntityManager em;

    @Inject
    public void CustomerRepository(EntityManager em){
        this.em = em;
    }


    public List<Customer> findCustomersWithTotalSpentGreaterThan(double amount){

        TypedQuery<Customer> query = em.createQuery(


            """
            SELECT c 
            FROM Customer c
            JOIN c.purchaseOrders o
            GROUP BY c
            HAVING SUM(o.total) > :amount
            """, Customer.class);

            query.setParameter("amount", amount);

            return query.getResultList();



        
    }

}
