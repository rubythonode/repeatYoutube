package models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class PlayListGroup extends Model {
 	@Id
	@GeneratedValue
    public Long id;
    public String title;
    public String password;
    public String description;
    public Long recommend;
    public String thumbnail;
    
    public PlayListGroup(String title, String password, String description, Long recommend, String thumbnail) {
        this.title = title;
        this.password = password;
        this.description = description;
        this.recommend = recommend;
        this.thumbnail = thumbnail;
    }
    
    public static Finder<Long,PlayListGroup> find = new Finder<Long,PlayListGroup>(Long.class, PlayListGroup.class);
}