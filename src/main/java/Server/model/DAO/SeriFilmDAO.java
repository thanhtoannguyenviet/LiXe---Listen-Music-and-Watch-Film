package Server.model.DAO;

import Server.model.DB.SeriCategoryfilmEntity;
import Server.model.DB.SerifilmEntity;
import Server.model.DTO.Criteria;
import Server.service.DBUtil;
import Server.service.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
@Repository
public class SeriFilmDAO {
    public List<SerifilmEntity> getAll() {
        Session s = HibernateUtil.getSession(SerifilmEntity.class);
        List<SerifilmEntity> ls = DBUtil.loadAllData(SerifilmEntity.class, s);
        return Collections.unmodifiableList(ls);
    }
    public SerifilmEntity save(SerifilmEntity entity){
        Session s = HibernateUtil.getSession(SerifilmEntity.class);
        return DBUtil.addData(entity,s);
    }
    public void delete(long id){
        Session s= HibernateUtil.getSession(SerifilmEntity.class);
        DBUtil.deleteData(id,SerifilmEntity.class,s);
    }
    public SerifilmEntity getByID(long id){
        Session s = HibernateUtil.getSession(SerifilmEntity.class);
        SerifilmEntity entity = DBUtil.getDataByID(id,SerifilmEntity.class,s);
        return entity;
    }
    public List<SerifilmEntity> loadDataPagination(Criteria criteria) {
        Session s = HibernateUtil.getSession(SerifilmEntity.class);
        List<SerifilmEntity> ls = DBUtil.loadDataPagination( s,criteria);
        return Collections.unmodifiableList(ls);
    }
    public long count(){
        Session s = HibernateUtil.getSession(SerifilmEntity.class);
        return DBUtil.countData(s,SerifilmEntity.class);
    }
    public List<SerifilmEntity> getTop10(Criteria criteria){
        Session s = HibernateUtil.getSession(SerifilmEntity.class);
        List<SerifilmEntity> ls = DBUtil.getTop10(criteria,s);
        return Collections.unmodifiableList(ls);
    }
    public List<SerifilmEntity> getTopRandom(Criteria criteria){
        Session s = HibernateUtil.getSession(SerifilmEntity.class);
        List<SerifilmEntity> ls = DBUtil.getTopRandom(criteria,s);
        return Collections.unmodifiableList(ls);
    }
}
