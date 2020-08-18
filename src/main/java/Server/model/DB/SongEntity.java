package Server.model.DB;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Song", schema = "dbo", catalog = "ProjectMusicFilm")
public class SongEntity {
    private long id;
    private String songname;
    private Long authorid;
    private Timestamp createdate;
    private String modifieduser;
    private Timestamp modifieddate;
    private String uploadsource;
    private String img;
    private int range;
    private Boolean active;
    private int index_;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "songname", nullable = false, length = 255)
    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    @Basic
    @Column(name = "authorid", nullable = true)
    public Long getAuthorid() {
        return authorid;
    }

    public void setAuthorid(Long authorid) {
        this.authorid = authorid;
    }
    @Basic
    @Column(name = "index_", nullable = false)
    public int getIndex() {
        return index_;
    }

    public void setIndex(int index) {
        this.index_ = index;
    }

    @Basic
    @Column(name = "createdate", nullable = false)
    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name = "modifieduser", nullable = false, length = 255)
    public String getModifieduser() {
        return modifieduser;
    }

    public void setModifieduser(String modifieduser) {
        this.modifieduser = modifieduser;
    }

    @Basic
    @Column(name = "modifieddate", nullable = false)
    public Timestamp getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(Timestamp modifieddate) {
        this.modifieddate = modifieddate;
    }

    @Basic
    @Column(name = "uploadsource", nullable = false, length = -1)
    public String getUploadsource() {
        return uploadsource;
    }

    public void setUploadsource(String uploadsource) {
        this.uploadsource = uploadsource;
    }

    @Basic
    @Column(name = "img", nullable = false, length = -1)
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Basic
    @Column(name = "range", nullable = false)
    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    @Basic
    @Column(name = "active", nullable = true)
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongEntity that = (SongEntity) o;
        return id == that.id &&
                range == that.range &&
                Objects.equals(songname, that.songname) &&
                Objects.equals(authorid, that.authorid) &&
                Objects.equals(createdate, that.createdate) &&
                Objects.equals(modifieduser, that.modifieduser) &&
                Objects.equals(modifieddate, that.modifieddate) &&
                Objects.equals(uploadsource, that.uploadsource) &&
                Objects.equals(img, that.img) &&
                Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, songname, authorid, createdate, modifieduser, modifieddate, uploadsource, img, range, active);
    }
}
