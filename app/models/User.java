package models;
import javax.persistence.Entity;
import play.db.ebean.Model;
import scala.reflect.internal.Trees.Super;

@Entity
public class User extends Model {
 
    public String email;
    public String password;
    public String fullname;
    public boolean isAdmin;
 
    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }
    
    public static Finder<Long,User> find = new Finder<Long,User>(Long.class, User.class);
    
    
}