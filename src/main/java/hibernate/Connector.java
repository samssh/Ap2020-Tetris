package hibernate;

import model.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Connector {
    private static final Connector connector = new Connector();
    private final SessionFactory sessionFactory = buildSessionFactory();
    private Session session;
    private boolean hasTransaction = false;

    private SessionFactory buildSessionFactory() {
        PrintStream err = System.err;
        try {
            PrintStream nullOut = new PrintStream(new File("./log/hibernate log.txt"));
            System.setErr(nullOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        System.setErr(err);
        return sessionFactory;
    }

    private Connector() {
    }

    public static Connector getConnector() {
        return connector;
    }

    public void open() {
        session = sessionFactory.openSession();
    }

    public void close() {
        if (session.isOpen())
            session.close();
    }

    public void beginTransaction() {
        if (!hasTransaction) {
            session.beginTransaction();
            hasTransaction = true;
        }
    }

    public void commit() {
        if (hasTransaction) {
            session.getTransaction().commit();
            hasTransaction = false;
        }
    }

    public void saveOrUpdate(Object o) {
        session.saveOrUpdate(o.getClass().getName(), o);
    }

    public void delete(Object o) {
        session.delete(o.getClass().getName(), o);
    }

    public Criteria createCriteria(Class c) {
        return session.createCriteria(c);
    }

    public void saveOrUpdateList(List listId, List list) {
        listId.subList(0, listId.size()).clear();
        for (Object element : list) {
            ((SaveAble) element).saveOrUpdate();
            listId.add(((SaveAble) element).getId());
        }

    }

    public void deleteList(List list) {
        for (Object o : list) ((SaveAble) o).delete();
    }

    @SuppressWarnings("unchecked")
    public List fetchList(Class target, List listId) {
        List objectList = new ArrayList();
        for (Object s : listId) {
            objectList.add(fetch(target, (Serializable) s));
        }
        return objectList;
    }

    public Object fetch(Class c, Serializable id) {
        Object o = session.get(c.getName(), id);
        if (o == null)
            return null;
        ((SaveAble) o).load();
        return o;
    }

    public List fetchAll(Class c) {
        Criteria criteria = session.createCriteria(c.getName());
        List list = criteria.list();
        for (Object o : list) {
            ((SaveAble) o).load();
        }
        return list;
    }

    public void saveOrUpdateArray(int[][] arrayId, SaveAble[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] != null) {
                    array[i][j].saveOrUpdate();
                    arrayId[i][j] = array[i][j].getId();
                } else {
                    arrayId[i][j] = -1;
                }
            }
        }
    }

    public void deleteArray(SaveAble[][] array) {
        for (SaveAble[] i : array) {
            for (SaveAble s : i) {
                if (s != null)
                    s.delete();
            }
        }
    }

    public void fetchArray(Class target, int[][] arrayId, SaveAble[][] array) {
        for (int i = 0; i < arrayId.length; i++) {
            for (int j = 0; j < arrayId[i].length; j++) {
                if (arrayId[i][j] > -1) {
                    SaveAble saveAble = (SaveAble) fetch(target, arrayId[i][j]);
                    saveAble.load();
                    array[i][j] = saveAble;
                } else {
                    array[i][j] = null;
                }
            }
        }
    }

    public void deleteAll() {
        Class[] classes = new Class[]{Board.class, NextPiece.class, Piece.class,
                Player.class};
        for (Class c : classes) {
            List list = fetchAll(c);
            for (Object o : list) {
                ((SaveAble) o).delete();
            }
        }
    }

    public Object fetchLast(Class c) {
        SaveAble saveAble = (SaveAble) session.createCriteria(c).addOrder(Order.desc("id")).setMaxResults(1).uniqueResult();
        if (saveAble != null)
            saveAble.load();
        return saveAble;
    }
}