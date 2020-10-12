public class LinkedHashPairs<KeyType, ValueType> {
    private KeyType key;
    private ValueType value;
    private LinkedHashPairs<KeyType, ValueType> next;
    
    LinkedHashPairs(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
        this.next = null;
  }

  public ValueType getValue() {
        return value;
  }

  public KeyType getKey() {
        return key;
  }
  
  public void setValue(ValueType value) {
        this.value = value;
  }

  public LinkedHashPairs<KeyType, ValueType> getNext() {
        return next;
  }

  public void setNext(LinkedHashPairs<KeyType, ValueType> next) {
        this.next = next;
  }
}
