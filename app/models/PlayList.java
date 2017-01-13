package models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class PlayList extends Model {
	@Id
	@GeneratedValue
    public Long id;
    public String keycode;
    public String title;
    public String description;
    public Long recommend;
    public String thumbnail;
       
    public PlayList(String keycode, String title, String description, Long recommend, String thumbnail) {
        this.keycode = keycode;
        this.title = title;
        this.description = description;
        this.recommend = recommend;
        this.thumbnail = thumbnail;
    }
    
    public static Finder<Long,PlayList> find = new Finder<Long,PlayList>(Long.class, PlayList.class);
}