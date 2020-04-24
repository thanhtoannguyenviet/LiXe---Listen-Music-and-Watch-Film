package Server.model.DAO;

import Server.model.DB.DirectorEntity;
import Server.model.DB.FilmEntity;
import Server.model.DTO.Criteria;
import Server.service.DBUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
@Repository
public class FilmDAO {
    SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(FilmEntity.class).buildSessionFactory();
    public List<FilmEntity> getAll() {
        Session s = factory.getCurrentSession();
        List<FilmEntity> ls = DBUtil.loadAllData(FilmEntity.class, s);
        return Collections.unmodifiableList(ls);
    }
    public FilmEntity save(FilmEntity entity){
        Session s = factory.getCurrentSession();
        return DBUtil.addData(entity,s);
    }
    public void delete(Long id){
        Session s= factory.getCurrentSession();
        DBUtil.deleteData(id,FilmEntity.class,s);
    }
    public FilmEntity getByID(Long id){
        Session s = factory.getCurrentSession();
        FilmEntity entity = DBUtil.getDataByID(id,FilmEntity.class,s);
        return entity;
    }
    public long count(){
        Session s =factory.getCurrentSession();
        return DBUtil.countDataWithCondition(s, FilmEntity.class);
    }
    public List<FilmEntity> loadDataPagination(Criteria criteria) {
        Session s = factory.getCurrentSession();
        List<FilmEntity> ls = DBUtil.loadDataPagination( s,criteria);
        return Collections.unmodifiableList(ls);
    }
    public List<FilmEntity> GetTop10(Criteria criteria){
        Session s = factory.getCurrentSession();
        List<FilmEntity> ls = DBUtil.getTop10(criteria,s);
        return Collections.unmodifiableList(ls);
    }
    public List<FilmEntity> getTopRandom(Criteria criteria){
        Session s = factory.getCurrentSession();
        List<FilmEntity> ls = DBUtil.getTopRandom(criteria,s);
        return Collections.unmodifiableList(ls);
    }
    public List<FilmEntity> getTopNew(Criteria criteria){
        Session s = factory.getCurrentSession();
        List<FilmEntity> ls = DBUtil.getTop10New("createdate",criteria,s);
        return Collections.unmodifiableList(ls);
    }
}
