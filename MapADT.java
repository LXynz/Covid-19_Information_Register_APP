import java.util.NoSuchElementException;

public interface MapADT<KeyType, ValueType> {

	public ValueType get(KeyType key) throws NoSuchElementException;
	public int size();
	public boolean containsKey(KeyType key);
	public ValueType remove(KeyType key);
	public void clear();
    public boolean put(KeyType key, ValueType value);
    int indexing(String roomInfo, int length);
	
}
