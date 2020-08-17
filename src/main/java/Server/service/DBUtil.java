package Server.service;

import Server.common.CUSTOM_QUERY;
import Server.model.DAO.LogDAO;
import Server.model.DB.LogEntity;
import Server.model.DTO.Criteria;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.events.EventException;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class DBUtil {
    public static <T> List<T> loadAllData(Class<T> type, Session session) {
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        session.getTransaction().commit();
        return data;
    }
    public static <T> List<T> execCustomSQL(Class<T> clazz, String query,Session session) {
        try {
            SQLQuery q = session.createSQLQuery(query); // bỏ custom SQL vào
            q.setResultTransformer((org.hibernate.Criteria.ALIAS_TO_ENTITY_MAP));

            List<T> data = (List<T>) q.list();
            return data;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public static <T> T addData(T newItem, Session session) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(newItem);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            new LogDAO().save(new LogEntity(ex));
            ex.printStackTrace();//Up server Delete
        } finally {
            session.close();
            return newItem;
        }
    }

    public static <T, K> void deleteData(T primaryID, Class<K> cl, Session session) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            K item = session.load(cl, (Serializable)primaryID);
            if (item != null) {
                session.delete(item);
            }
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            new LogDAO().save(new LogEntity(ex));
            ex.printStackTrace();
        }finally {
            session.close();
        }
    }

    public static <T, K> K getDataByID(T primaryID, Class<K> cl, Session session) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            K item = (K) session.get(cl, (Serializable)primaryID);
            tx.commit();
            return item;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            new LogDAO().save(new LogEntity(ex));
            ex.printStackTrace();
            return null;
        }
        finally {
            session.close();
        }
    }

    public static <K> K convertToOBject(Object object, Class<K> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, clazz);

    }
    public static <T> List<T> getTop10(Criteria criteria,Session session){
        Transaction tx = null;
        try{
            tx= session.beginTransaction();
            org.hibernate.Criteria  q= session.createCriteria(criteria.getClazz()).addOrder(Order.desc("range")).setMaxResults(criteria.getTop());
            tx.commit();
            return  q.list();
        }catch (HibernateException ex){
            new LogDAO().save(new LogEntity(ex));
            return null;
        }
        finally {
            session.close();
        }
    }
    public static <T> long countDataWithCondition(Session session, Class<T> type){
        Transaction tx = null;
        try {
            tx=session.beginTransaction();
            org.hibernate.Criteria q = session.createCriteria(type).
                    add(Restrictions.eq("active",true)).setProjection(Projections.rowCount());
            tx.commit();
            return (long) q.uniqueResult();
        }catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }finally {
            session.close();
        }
    }
    public static <T> long countData(Session session, Class<T> type){
        Transaction tx = null;
        try{
            tx=session.beginTransaction();
            org.hibernate.Criteria q = session.createCriteria(type).
                    setProjection(Projections.rowCount());
            tx.commit();
            return (long) q.uniqueResult();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
        finally {
            session.close();
        }
    }
    public static <T> List<T> loadDataPagination(Session session,Criteria criter ) {
        try{
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        int itemStart = 0;
        int itemEnd = 2;
        if (criter != null) {
            itemStart = criter.getCurrentPage() * criter.getItemPerPage();
            itemEnd = itemStart + criter.getItemPerPage();
        }
        CriteriaQuery<T> criteriaQuery = builder.createQuery(criter.getClazz());
        Root<T> from = criteriaQuery.from(criter.getClazz());
        CriteriaQuery<T> select = criteriaQuery.select(from);
        TypedQuery<T> typedQuery = session.createQuery(select);
        typedQuery.setFirstResult(itemStart);
        typedQuery.setMaxResults(itemEnd);
        List<T> data = typedQuery.getResultList();
        // session.getTransaction().commit();
        return typedQuery.getResultList();
        }catch (Exception ex){
            ex.getMessage();
            return null;
        }finally {
            session.close();
        }
    }

    public static <T> List<T> getTop10New(String condition,Criteria criteria,Session session){
        Transaction tx = null;
        try{
            tx=session.beginTransaction();
            org.hibernate.Criteria crit = session.createCriteria(criteria.getClazz()).addOrder(Order.desc(condition)).
                    setMaxResults(criteria.getTop());
            tx.commit();
            return  crit.list();
        }catch (HibernateException ex){
            new LogDAO().save(new LogEntity(ex));
            return null;
        }finally {
            session.close();
        }
    }
    public static <T> List<T> getTopRandom(Criteria criteria,Session session){
        Transaction tx = null;
        try{
            tx=session.beginTransaction();
            org.hibernate.Criteria  q= session.createCriteria(criteria.getClazz())
                    .add(Restrictions.sqlRestriction("1=1 order by newid()"))
                    .setMaxResults(criteria.getTop());
            tx.commit();
            return  q.list();
        }catch (HibernateException ex){
            new LogDAO().save(new LogEntity(ex));
            return null;
        }finally {
            session.close();
        }
    }
    public static <T> List<T> getListHasCondition(String table,String conditionColumn,String condition,Class<T> type,Session session){
        Transaction tx = null;
        try {
            tx =  session.beginTransaction();
            String sql = CUSTOM_QUERY.sqlGetId(table,conditionColumn,condition);
            SQLQuery q = session.createSQLQuery(sql);
            q.addEntity(type);
            tx.commit();
            return  q.getResultList()  ;
        }catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            new LogDAO().save(new LogEntity(ex));
            return null;
        }
        finally {
            session.close();
        }
    }

    public static <T> List<T> getListHasCondition(String conditionColumn,String condition,Class<T> type,Session session){
        Transaction tx = null;
        try {
            tx =  session.beginTransaction();
            org.hibernate.Criteria criteria = session.createCriteria(type).add(Restrictions.eq(conditionColumn, Long.parseLong(condition)));
            tx.commit();
            return  criteria.list() ;
        }catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            new LogDAO().save(new LogEntity(ex));
            return null;
        }
        finally {
            session.close();
        }
    }
    public static <T> List<T> getImageOrResource(String table,String model,long entryid,Class<T> type,Session session){
        Transaction tx = null;
        try {
            tx= session.beginTransaction();
            String sql = CUSTOM_QUERY.sqlGetIdFromImageOrResource(table,model,entryid);
            SQLQuery q = session.createSQLQuery(sql);
            q.addEntity( type);
            tx.commit();
            return q.getResultList();
        }catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            new LogDAO().save(new LogEntity(ex));
            return null;
        }finally {
            session.close();
        }
    }
    public static <T> List<T>  getSearch(String table,String keyword,String model ,Class<T> type,Session session){
        Transaction tx = null;
        try {
            tx= session.beginTransaction();
            String sql = CUSTOM_QUERY.sqlGetSearch(table,keyword,model);
            SQLQuery q = session.createSQLQuery(sql);
            q.addEntity( type);
            tx.commit();
            return  q.getResultList();
        }catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            new LogDAO().save(new LogEntity(ex));
            return null;
        }finally {
            session.close();
        }
    }
    public static <T> List<T> getDataByName(String table,String conditionColumn,String condition,Class<T> type,Session session){
        Transaction tx = null;
        try{
            tx=session.beginTransaction();
            SQLQuery  q = session.createSQLQuery(CUSTOM_QUERY.searchBasic(table, conditionColumn, condition));
            q.addEntity(type);
            tx.commit();
            return q.list();
        }catch(HibernateException ex){
            if(tx!=null) tx.rollback();
            new LogDAO().save(new LogEntity(ex));
            return null;
        }
        finally {
            session.close();
        }
    }
    public static <T> List<T> getIdTableM2M(String table, String conditionColumn,String condition,String conditionColumn1,String condition1,Class <T> type, Session session){
        Transaction tx= null;
        try{
            tx=session.beginTransaction();
            SQLQuery q = session.createSQLQuery(CUSTOM_QUERY.getIdTableM2M(table,conditionColumn,condition,conditionColumn1,condition1));
            q.addEntity(type);
            tx.commit();
            return q.list();
        }catch(HibernateException ex){
            if(tx!=null) tx.rollback();
            new LogDAO().save(new LogEntity(ex));
            return null;
        }
        finally {
            session.close();
        }
    }

    public static <T> int addData2(T newItem, Session session) {
        int cnt = 0;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(newItem);
            cnt = 1;
            tx.commit();
        }  catch (HibernateException ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            new LogDAO().save(new LogEntity(ex));
        } catch (EventException ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            new LogDAO().save(new LogEntity(ex));
        } finally {
            session.close();
            return cnt;
        }
    }

    public static <T> int updateData(T newItem, Session session) {
        int cnt = 0;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(newItem);
            cnt = 1;
            tx.commit();
        }  catch (HibernateException ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            new LogDAO().save(new LogEntity(ex));
        } catch (EventException ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            new LogDAO().save(new LogEntity(ex));
        } finally {
            session.close();
            return cnt;
        }
    }
}