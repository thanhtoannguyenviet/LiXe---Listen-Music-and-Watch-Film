package Server.model.DAO;

import Server.common.CUSTOM_QUERY;
import Server.model.DB.SongSingerEntity;
import Server.service.DBUtil;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
@Repository
public class SongSingerDAO {
    SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(SongSingerEntity.class).buildSessionFactory();
    public List<SongSingerEntity> getAll() {
        Session s = factory.getCurrentSession();
        List<SongSingerEntity> ls = DBUtil.loadAllData(SongSingerEntity.class, s);
        return Collections.unmodifiableList(ls);
    }
    public SongSingerEntity Save(SongSingerEntity entity){
        Session s = factory.getCurrentSession();
        return DBUtil.addData(entity,s);
    }
    public void Delete(long id){
        Session s= factory.getCurrentSession();
        DBUtil.deleteData(id,SongSingerEntity.class,s);
    }
    public SongSingerEntity GetByID(long id){
        Session s = factory.getCurrentSession();
        SongSingerEntity entity = DBUtil.GetDataByID(id,SongSingerEntity.class,s);
        return entity;
    }
    public List<SongSingerEntity> GetId(String conditionColumn, String condition ){

        Session s = factory.getCurrentSession();
        Transaction tx = s.beginTransaction();
        try {
            //sql = select * from User_ where userName = '?'
            String sql = CUSTOM_QUERY.sqlGetId("Song_Singer",conditionColumn,condition);
            SQLQuery q = s.createSQLQuery(sql);
            q.addEntity(SongSingerEntity .class);
            return  q.getResultList() ;

        }catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return null;
        } finally {
            s.close();
        }
    }
}
