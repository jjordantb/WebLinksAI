/**
 * Created by Jordan on 2/9/2016.
 *
 * Base interface for filtering objects
 */
public interface Filter<T> {

    boolean accept(T t);

}
