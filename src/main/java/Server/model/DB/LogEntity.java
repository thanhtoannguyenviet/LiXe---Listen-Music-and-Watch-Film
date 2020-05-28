package Server.model.DB;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Log_", schema = "dbo", catalog = "ProjectMusicFilm")
public class LogEntity {
    private long id;
    private String messageerror;
    private String codeerror;
    private String nameapierror;
    public LogEntity(){}
    public LogEntity(Exception ex){
        messageerror=ex.getCause().getMessage().replace("\n","");
        codeerror= ex.getMessage();
        nameapierror=ex.getClass().getName();
    }
    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "messageerror", nullable = true, length = -1)
    public String getMessageerror() {
        return messageerror;
    }

    public void setMessageerror(String messageerror) {
        this.messageerror = messageerror;
    }

    @Basic
    @Column(name = "codeerror", nullable = true, length = -1)
    public String getCodeerror() {
        return codeerror;
    }

    public void setCodeerror(String codeerror) {
        this.codeerror = codeerror;
    }

    @Basic
    @Column(name = "nameapierror", nullable = true, length = -1)
    public String getNameapierror() {
        return nameapierror;
    }

    public void setNameapierror(String nameapierror) {
        this.nameapierror = nameapierror;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntity logEntity = (LogEntity) o;
        return id == logEntity.id &&
                Objects.equals(messageerror, logEntity.messageerror) &&
                Objects.equals(codeerror, logEntity.codeerror) &&
                Objects.equals(nameapierror, logEntity.nameapierror);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, messageerror, codeerror, nameapierror);
    }
}
