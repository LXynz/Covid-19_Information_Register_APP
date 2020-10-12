import java.util.NoSuchElementException;
public class UniversityHousing extends HashTableMap<String, Room> {


  /**
   * Loads all data from the file into the hashTableArray by repeatedly calling put() method
   * 
   * @return true if all data loaded successfully
   */
  public boolean loadData() {
    // This method should repeatedly calls the put() method without modifying the file
    // NOTE that this method is called when loading data from the file, NOT when dealing with user
    // input
    int i = 0;
    while (i < DataExchange.size()) { // iterate until end of JSON file reached
      Room obj = DataExchange.loadOne(i);
      String roomKey = obj.getDormName() + obj.getRoomNum();
      if (!(put(roomKey, obj)))
        return false;
      i++;
    }
    return true;

  }

  /**
   * Adds a new Room instance provided by the user input into the array and also saves this addition
   * to the file.
   * 
   * @param roomInfo room information in the form of <dorm name + room num> as a String key
   * @param room     the Room instance as the value
   * @return true if the user input is successful
   */
  public boolean userPut(String roomInfo, Room room) {
    // This method should act in the same way as the put() method, and the only difference is that
    // it will also modify the file to save the changes.

    // NOTE that this method is called when dealing with the user input, so this function will be
    // called in the user interface. Please check with the front-end dev to ensure its correct
    // functionality and its correct usage.

    // NOTE that a pair with the same key cannot be added again and this function will return false.

    if (put(roomInfo, room)) { // add Room object with key as roomInfo into hash table
      return DataExchange.addJSON(room); // add Room object into JSON file
    }
    return false;
  }

  /**
   * Updates the test result of a resident within the hash table and in the file. This is done by
   * deleting the Room instance and adding the updated Room object in the JSON file. For the hash
   * table, the setResult() from the Resident class is called to make the change.
   * 
   * @param dormName for which resident wants to update their result
   * @param roomNum  for which resident wants to update their result
   * @param test     is the new test result that is to be used for the update
   * @return true if update was successful
   */
  public boolean update(String dormName, long roomNum, Boolean test) {
    Room toUpdate = get((dormName + roomNum));
    Room roomToDelete = get((dormName + roomNum));
    if (!(DataExchange.deleteJSON(roomToDelete))) // deleting Room object in JSON file
      return false;

    toUpdate.getResident().setResult(test); // updating the hash table
    if (!(DataExchange.addJSON(toUpdate))) // adding updated Room object in JSON file
      return false;

    return true;
  }


  /**
   * Updates the resident in a room in a similar way to the previous update method. The user would
   * be prompted if they would like to "remove" or "add" a resident. If they would like to "add"
   * they would be asked to input their name and test result(These are needed to create a Resident
   * instance).
   * 
   * @param dormName for which resident wants to update their result
   * @param roomNum for which resident wants to update their result
   * @param resident object that contains information of the new resident to be added
   * @param inst determines if the user wants to remove or add a resident
   * @return true if update was successful
   */
  public boolean update(String dormName, long roomNum, Resident resident, String inst) {
    Room toUpdate = get(dormName + roomNum);
    Room roomToDelete = get(dormName + roomNum);

    if (!(DataExchange.deleteJSON(roomToDelete)))
      return false;
    if (inst.contains("remove")) { // if the user wants to "remove" a resident
      toUpdate.removeResident();
    } else { // if the user wants to "add" a resident, their name and test result should be asked
             // for
      toUpdate.addResident(resident);
    }
    if (!(DataExchange.addJSON(toUpdate)))
      return false;


    return true;
  }


  /**
   * Removes a Room instance from the array, returns that Room instance, and also deletes this room
   * from the file.
   * 
   * @param roomInfo room information in the form of <dorm name + room num> as a String key
   * @return the Room instance that was removed both from the array and the file
   */
  @Override
  public Room remove(String roomInfo) {
    // Make a call to the deletion method provided by Data Wrangler here to modify the file

    // NOTE that this method is called when dealing with the user input, so this function will be
    // called in the user interface. Please check with the front-end dev to ensure its correct
    // functionality and its correct usage.

    // NOTE that removing a pair that does not exist will return null reference.
	  
//	  try{
//		  DataExchange.deleteJSON(get(roomInfo));}
//		  catch (NoSuchElementException e){
//		    //e.printStackTrace();
//		    System.out.println("problem detected");
//		  }

    DataExchange.deleteJSON(get(roomInfo));
    return super.remove(roomInfo);
  }
