package org.licenta.d4elders.model.Dao;

import org.licenta.d4elders.model.FoodProperties;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by root on 22.02.2015.
 */
public class FoodPropertiesDAOImpl implements FoodPropertiesDAO {
    SessionFactory sessionFactory = SessionFactoryHelper.getSessionFactory();

    @Override
    public List<FoodProperties> listFoodProperties() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            //List<FoodProperties> foodPropertiesList = session.createSQLQuery("FROM food_properties").list();
            List<FoodProperties> foodPropertiesList = session.createCriteria(FoodProperties.class).list();
            for(FoodProperties food : foodPropertiesList)
            {
                food.populateMap();
            }
            tx.commit();
            return foodPropertiesList;
        }
        catch (HibernateException e)
        {
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return null;
    }

    @Override
    public FoodProperties getFoodPropertiesByCode(String code) {
        Session session = sessionFactory.openSession();
        try
        {
            FoodProperties fp = (FoodProperties)session.get(FoodProperties.class, code);
            return fp;
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return null;
    }
}
