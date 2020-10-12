public class EntryNode <KeyType, ValueType> //Self-defined class to store key-value pair.
{                                 
  	public KeyType key;  
  	public ValueType value; 
  	EntryNode(KeyType Key , ValueType Value)
  	{
  		this.key = Key; 
  		this.value = Value;  		
  	}
}
