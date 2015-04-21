package Meals.Model.Dao;

import Meals.Model.NutrientsIdealValues;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by cristiprg on 01.03.2015.
 */
public class NutrientsIdealValuesDAOImpl implements NutrientsIdealValuesDAO {
    SessionFactory sessionFactory = SessionFactoryHelper.getSessionFactory();

    public NutrientsIdealValues getNutrientsIdealValuesById(int id)
    {
        Session session = sessionFactory.openSession();
        try
        {
            NutrientsIdealValues nv = (NutrientsIdealValues)session.get(NutrientsIdealValues.class, id);
            nv.populateMap();
            return nv;
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
