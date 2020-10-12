import java.util.NoSuchElementException;
import java.util.LinkedList;

public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType>{
    private LinkedHashPairs<KeyType, ValueType>[] table;
    private int capacity;
    private int size;
    
    /**
     * Constructor with an input as the capacity.
     * 
     * @param capacity how many items are allowed to be stored in the table.
     */
    public HashTableMap(int capacity) {
        table = new LinkedHashPairs[capacity];
        this.capacity = capacity;
        size = 0;
    }
    
    /**
     * Constructor with default capacity = 10.
     * 
     */
    public HashTableMap() {
        table = new LinkedHashPairs[10];// with default capacity = 10
        capacity = 10;
        size = 0;
    }
    
    /**
     * Calculate the load factor with current size and capacity.
     * 
     * @return current load factor of hashtable
     */
    private double loadFacCal() {
        return (double)size/(double)capacity;
    }
    
    /**
     * Expanding the current hashtable by creating a new table with and rehash all items
     * stored in the old table.
     * 
     */
    private void rehash() {
        LinkedHashPairs<KeyType, ValueType>[] newTable = new LinkedHashPairs[2*capacity];
        int i = 0;
        capacity = 2*capacity;
        LinkedHashPairs<KeyType, ValueType> curr;
        LinkedHashPairs<KeyType, ValueType> newCurr;
        while(table[i] != null) {
            curr = table[i];
            newCurr = newTable[hashFunction(curr.getKey())];
            if(newCurr == null) newCurr = curr; // to ensure that the position to put is empty
            else {
                while(newCurr.getNext() != null) {//--a To find the next empty bucket in new Array
                    newCurr = newCurr.getNext();
                }
                newCurr = curr;//--a
            }
            if(curr.getNext() != null) {// if there are buckets chained to the curr
                curr = curr.getNext();
                newCurr = newTable[hashFunction(curr.getKey())];
                if (newCurr == null) newCurr = curr;
                else {
                    while(newCurr.getNext() != null) {//--a
                        newCurr = newCurr.getNext();
                    }
                    newCurr = curr;//--a
                }
            }
            ++i;
        }
        table = newTable; //final step: assign new address to the current table.
        
    }
    
    /**
     * The hash function used by this hashtable.
     * 
     * @param key user input
     * @return hash index of the key
     */
    private int hashFunction(KeyType key) {
        return Math.abs(key.hashCode()%capacity);
    }
    
    /**
     * Add items to the hashtable, store them in the form of a pair of key and value.
     * 
     * @return true when adding successfully, false if the key needed to be stored is existed.
     */
    @Override
    public boolean put(KeyType key, ValueType value) {
        
        if(containsKey(key))
            return false; //returns false if the key is existed
        
        int hashIndex = hashFunction(key);
        if(table[hashIndex] == null) {// if current position is empty, then put it here
            table[hashIndex] = new LinkedHashPairs<KeyType, ValueType>(key,value);
            ++size;
            if(loadFacCal() >= 0.8) rehash();
            return true;
        }
        else {
            LinkedHashPairs<KeyType, ValueType> pairs = table[hashIndex];
            while(pairs.getNext() != null) //&& pairs.getKey != key
                pairs = pairs.getNext(); // finding an empty position and put the pairs there
            pairs.setNext(new LinkedHashPairs<KeyType, ValueType>(key,value));
            ++size;
            if(loadFacCal() >= 0.8) rehash();
            return true;
        }
        
    }

    /**
     * Find the corresponding value with a given key.
     * 
     * @throws NoSuchElementExcpetion when there is no corresponding value with the given key
     */
    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        int hashIndex = hashFunction(key);
        if(table[hashIndex] == null) // if current position is null, then thow exception
            throw new NoSuchElementException("nothing with this key");
        else {
            LinkedHashPairs<KeyType, ValueType> pairs = table[hashIndex];
            while(pairs!= null && !pairs.getKey().equals(key)) // if it is chaining, then find through the chain
                pairs = pairs.getNext();
            if (pairs == null)
                throw new NoSuchElementException("nothing with this key");
            else 
                return pairs.getValue(); 
        }
    }
    //
    /**
     * Help to get the current size of hashtable.
     * 
     * @return the size of hashtable
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * To check if a key is already existed in the hashtable.
     * 
     * @return true if there is an existed same key, false otherwise.
     */
    @Override
    public boolean containsKey(KeyType key) {
        if(loadFacCal() >= 0.8) rehash();
        int hashIndex = hashFunction(key);
        if(table[hashIndex] == null)
            return false;
        if (table[hashIndex].getKey().equals(key))
            return true;
        else {
            LinkedHashPairs<KeyType, ValueType> curr = table[hashIndex];
            while(curr.getNext() != null) {
                if(curr.getKey().equals(key))
                    return true;
            curr = curr.getNext();
            }
        }
        return false;
    }

    /**
     * Removing items in the hashtable with a given key.
     * 
     * @return the removed item, null if nothing removed
     */
    @Override
    public ValueType remove(KeyType key) {
        if(loadFacCal() >= 0.8) rehash();
        if (!containsKey(key)) // if the key is not existed, then return null
            return null;
        LinkedHashPairs<KeyType, ValueType> pairs = null;
        LinkedHashPairs<KeyType, ValueType> previous = null;
        LinkedHashPairs<KeyType, ValueType> removed = null;
        int hashIndex = hashFunction(key);
        
        if(table[hashIndex] != null) {
            pairs = table[hashIndex];
            while(pairs.getKey() != key && pairs.getNext() != null) {
                previous = pairs;
                pairs = pairs.getNext();
            }
            if (pairs.getKey().equals(key)) {
                if(previous == null) { // if we remove the first item in the head of linkedlist
                    removed = pairs;
                    --size;
                    table[hashIndex] = pairs.getNext();
                    
                } else { 
                    removed = pairs;
                    --size;
                    previous.setNext(pairs.getNext());
                    
                }
            }
        }
        
        return removed.getValue();
    }

    /**
     * reeturn the capacity of current table.
     * 
     * @return capacity
     */
    protected int capacity() {
        return capacity;
    }
    
    /**
     * Clear the hashtable.
     * 
     */
    @Override
    public void clear() {
        size = 0;
        table = new LinkedHashPairs[capacity]; // just refer to another new array
    }

    public int indexing(String roomInfo, int length) {
        // TODO Auto-generated method stub
        return 0;
    }

}
