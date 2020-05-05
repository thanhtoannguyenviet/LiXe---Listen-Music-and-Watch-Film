package Server.model.DAO;

import Server.model.DB.AuthorEntity;
import Server.model.DB.CategoryfilmEntity;
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
public class CategoryFilmDAO {
    public List<CategoryfilmEntity> getAll() {
        Session s = HibernateUtil.getSession(CategoryfilmEntity.class);
        List<CategoryfilmEntity> ls = DBUtil.loadAllData(CategoryfilmEntity.class, s);
        return Collections.unmodifiableList(ls);
    }
    public CategoryfilmEntity save(CategoryfilmEntity entity){
        Session s = HibernateUtil.getSession(CategoryfilmEntity.class);
        return DBUtil.addData(entity,s);
    }
    public void delete(Long id){
        Session s= HibernateUtil.getSession(CategoryfilmEntity.class);
        DBUtil.deleteData(id,CategoryfilmEntity.class,s);
    }
    public CategoryfilmEntity getByID(Long id){
        Session s = HibernateUtil.getSession(CategoryfilmEntity.class);
        CategoryfilmEntity entity = DBUtil.getDataByID(id,CategoryfilmEntity.class,s);
        return entity;
    }
    public long count(){
        Session s = HibernateUtil.getSession(CategoryfilmEntity.class);
        return DBUtil.countData(s, CategoryfilmEntity.class);
    }
    public List<CategoryfilmEntity> loadDataPagination(Criteria criteria) {
        Session s = HibernateUtil.getSession(CategoryfilmEntity.class);
        List<CategoryfilmEntity> ls = DBUtil.loadDataPagination( s,criteria);
        return Collections.unmodifiableList(ls);
    }
    public List<CategoryfilmEntity> getTop10(Criteria criteria){
        Session s = HibernateUtil.getSession(CategoryfilmEntity.class);
        List<CategoryfilmEntity> ls = DBUtil.getTop10(criteria,s);
        return Collections.unmodifiableList(ls);
    }
    public List<CategoryfilmEntity> getTopRandom(Criteria criteria){
        Session s = HibernateUtil.getSession(CategoryfilmEntity.class);
        List<CategoryfilmEntity> ls = DBUtil.getTopRandom(criteria,s);
        return Collections.unmodifiableList(ls);
    }
}
