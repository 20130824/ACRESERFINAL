import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dany on 05/11/2016.
 */
public interface ICRUD <T> {
    public Boolean insert (T entity);
    public ArrayList<T> getElements();
    public Boolean update(T entity);
    public T readOne(String id);
    public Boolean delete(String id);
}
