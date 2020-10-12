public class Room {

  private String dormName; // name of the residence hall
  private long roomNum; // room number
  private Resident resident; // a ResidentInfo instance containing the resident information

  /**
   * Constructs a new Room instance with the name of the residence hall, the room number. Without
   * resident information provided, this room is empty by default. Note that the name of the
   * residence hall and the room number mustb e provided.
   * 
   * @param dormName name of the residence hall
   * @param roomNum  room number
   */
  public Room(String dormName, long roomNum) {
    this.dormName = dormName;
    this.roomNum = roomNum;
    this.resident = null;
  }

  /**
   * Constructs a new Room instance with the name of the residence hall, the room number, and the
   * resident information.
   * 
   * @param dormName name of the residence hall
   * @param roomNum room number
   * @param resident resident information instance
   */
  public Room(String dormName, long roomNum, Resident resident) {
    this.dormName = dormName;
    this.roomNum = roomNum;
    this.resident = resident;
  }
  
  /**
   * Adds a new resident to this room.
   * 
   * @param roomTest new resident
   * @throws IllegalArgumentException if this room is full
   */
  public void addResident(Resident resident) {
	    if (!isEmpty()) {
	      throw new IllegalArgumentException("Full room. Remove a resident first.");
	    }
	    this.resident = resident;
	  }
  
  /**
   * Removes the resident from this room and return this Resident instance.
   * 
   * @return the resident that was removed
   * @throws java.util.NoSuchElementException if the room is empty
   */
  public Resident removeResident() throws java.util.NoSuchElementException {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException("Cannot remove from empty room.");
    }
    Resident toReturn = resident;
    resident = null;
    return toReturn;
  }
  
  /**
   * Gets the ResidentInfo instance that contains the resident's basic information
   * 
   * @return the ResidentInfo instance
   */
  public Resident getResident() {
    return resident;
  }
  
  /**
   * Gets the name of the residence hall.
   * 
   * @return name of the residence hall
   */
  public String getDormName() {
    return dormName;
  }

  /**
   * Gets the room number
   * 
   * @return room number
   */
  public long getRoomNum() {
    return roomNum;
  }

  /**
   * Checks if this room is empty (has no resident).
   * 
   * @return true if it is empty
   */
  public boolean isEmpty() {
    return resident == null;
  }
  
  /**
   * Calculates the hash code number for this Room instance
   * 
   * @return the hash code of this Room instance
   */
  @Override
  public int hashCode() {
    // This is just my suggestion
    String hashingString = new String(this.dormName + this.roomNum);
    return hashingString.hashCode();
  }

  /**
   * Determines whether this Room instance equals another Room instance by comparing their residence
   * hall names and room numbers.
   * 
   * @param room another Room instance
   * @return true if they have the same residence hall name and room number
   */
  @Override
  public boolean equals(Object room) {
    return this.getDormName().equals(((Room) room).getDormName())
        & this.getRoomNum() == ((Room) room).getRoomNum();

  }


}
