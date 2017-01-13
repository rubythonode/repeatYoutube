package models;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class MusicCount extends Model {
	@Id
    public String code;
    public int size;
 
    public MusicCount(String code, int size) {
        this.code = code;
        this.size = size;
    }
    
    public static Finder<Long,MusicCount> find = new Finder<Long,MusicCount>(Long.class, MusicCount.class);
}