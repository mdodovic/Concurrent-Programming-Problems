package kdp.lab.FilmMakers;

public interface Buffer<T> {

	public void put(T item);
	public T get();

}
