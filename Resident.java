public class Resident {

  private String name; // resident's name
  private Boolean test; // resident's COVID-19 test result 1)TRUE represents a POSITIVE test result;
                        // 2)FALSE represents a NEGATIVE test result; 3)NULL represents NOT TESTED;

  /**
   * Constructs a resident instance with his or her name, age, and COVID-19 test result. The test
   * result can be a null reference which indicates unknown result.
   * 
   * @param name resident's name
   * @param age  resident's age
   * @param test resident's COVID-19 test result
   */
  public Resident(String name, Boolean test) {
    this.name = name;
    this.test = test;
  }
  
  /**
   * Sets the COVID-19 test result of this resident.
   * 
   * @param result COVID-19 test result of this resident
   */
  public void setResult(Boolean result) {
    this.test = result;
  }
  
  /**
   * Gets the resident's COVID-19 test result.
   * 
   * @return null if the result is unknown, true if this resident was tested POSITIVE.
   */
  public Boolean getResult() {  
    return test;
  }

  /**
   * Gets the resident's name.
   * 
   * @return resident's name
   */
  public String getName() {
    return name;
  }
}
