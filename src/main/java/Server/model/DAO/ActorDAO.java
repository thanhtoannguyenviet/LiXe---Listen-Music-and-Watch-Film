package Server.model.DAO;

import Server.model.DB.ActorEntity;
import Server.model.DTO.Criteria;
import Server.service.DBUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
@Repository
public class ActorDAO {
    SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(ActorEntity.class).buildSessionFactory();
    public List<ActorEntity> getAll() {
        Session s = factory.getCurrentSession();
        List<ActorEntity> ls = DBUtil.loadAllData(ActorEntity.class, s);
        return Collections.unmodifiableList(ls);
    }
    public ActorEntity Save(ActorEntity entity){
        Session s = factory.getCurrentSession();
        return DBUtil.addData(entity,s);
    }
    public void Delete(Long id){
        Session s= factory.getCurrentSession();
        DBUtil.deleteData(id,ActorEntity.class,s);
    }
    public ActorEntity GetByID(Long id){
        Session s = factory.getCurrentSession();
        ActorEntity entity = DBUtil.GetDataByID(id,ActorEntity.class,s);
        return entity;
    }
    public List<ActorEntity> loadDataPagination(Criteria criteria) {
        Session s = factory.getCurrentSession();
        List<ActorEntity> ls = DBUtil.loadDataPagination( s,criteria);
            return Collections.unmodifiableList(ls);
    }
    public long count(){
        Session s = factory.getCurrentSession();
        return DBUtil.countData(s,ActorEntity.class);
    }
//    public List<ActorEntity> GetTop10(Criteria criteria){
//        Session s = factory.getCurrentSession();
//        List<ActorEntity> ls = DBUtil.GetTop10(criteria,s);
//        return Collections.unmodifiableList(ls);
//    }
}
