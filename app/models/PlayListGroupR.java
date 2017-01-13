package models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class PlayListGroupR extends Model {
	@Id
	@GeneratedValue
    public Long id;
    public String keycode;
    public String password;
    public String title;
    public String description;
    public Long recommend;
    public String thumbnail;
    
    @ManyToOne
    public PlayListGroup group;
       
    public PlayListGroupR(PlayListGroup group, String keycode, String title, String description, Long recommend, String thumbnail) {
    	this.group = group;
        this.keycode = keycode;
        this.title = title;
        this.description = description;
        this.recommend = recommend;
        this.thumbnail = thumbnail;
    }
    
    public static Finder<Long,PlayListGroupR> find = new Finder<Long,PlayListGroupR>(Long.class, PlayListGroupR.class);
}