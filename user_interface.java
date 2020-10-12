import java.util.Scanner;

/**
 * This class implements a user interface including operations add, delete,
 * update, search, and clear the information about COVID-19 test results of
 * university housing residents.
 * 
 *
 */
public class user_interface {
	static private UniversityHousing table = new UniversityHousing();

	/**
	 * Add an entry of key (in the form of a string containing dorm name + room
	 * number) and value (a Room object) to the hashtable. It will be failed if the
	 * dorm is already existed.
	 * 
	 * @param sc scanner to read user input.
	 * @return true if added successfully, false otherwise.
	 */
	public static boolean add(Scanner sc) {
		// read room info
		Object[] roomInfo = readRoomInfo(sc);
		String dorm = (String) roomInfo[0];
		long num = (long) roomInfo[1];
		// check if the room is existed
		if (table.containsKey((String) roomInfo[0] + (long) roomInfo[1])) {
			System.out.println("This room is already existed! Please try another room or use 'update'.");
			return false;
		}
		// ask user whether to add resident info
		System.out.println("Do you want to add resident info to this room?(y/n):");
		String ans = "";
		while (true) {
			ans = sc.nextLine();
			if (ans.equalsIgnoreCase("y"))
				break;
			else if (ans.equalsIgnoreCase("n")) {
				Room room = new Room(dorm, num);
				return table.userPut(dorm + num, room);
			}
		}
		// read resident info
		String[] res = readResInfo(sc);
		String name = res[0];
		Boolean test = stringToBoolean(res[1]);
		// create Resident obj
		Resident stu = new Resident(name, test);
		// create Room obj
		Room room = new Room(dorm, num, stu);

		return table.userPut(dorm + num, room);

	}

	/**
	 * Delete an entry with the given key (in the form of a string containing dorm
	 * name + room number) from user.
	 * 
	 * @param sc scanner to read user input.
	 * @return the Room object has been deleted, null if the room doesn't exist.
	 */
	public static Room delete(Scanner sc) {
		// read room info
		Object[] roomInfo = readRoomInfo(sc);
		String dorm = (String) roomInfo[0];
		long num = (long) roomInfo[1];
		// remove operation
		return table.remove(dorm + num);
	}

	/**
	 * Update a user's test result. This need the user has added his/her information
	 * before. Otherwise, it will be failed and suggest user to try 'add'.
	 * 
	 * @param sc sanner to read user input.
	 * @return true if update successfully, false otherwise.
	 */
	public static Resident update(Scanner sc) {
		// read room info
		Object[] roomInfo = readRoomInfo(sc);
		String dorm = (String) roomInfo[0];
		long num = (long) roomInfo[1];
		// update operation
		if (!table.containsKey(dorm + num)) { // when the room doesn't exist
			System.out.println("The room does not existed! Please try 'add'.");
			return null;
		} else if (!table.get(dorm + num).isEmpty()) {
			while (true) {
				Resident old = table.get(dorm + num).getResident();
				System.out.println(
						"This room is " + old.getName() + ", test result: " + booleanToString(old.getResult()) + ".");
				System.out.println(
						"What do you want to do? updates result(up); replace with a new resident (re); remove current resident(rm):");
				String ans = sc.nextLine();
				if (ans.equalsIgnoreCase("up")) { // updates resident's test result
					String result;
					Boolean res;
					while (true) {
						System.out.println("Please enter your latest test result (positive/negative/null): ");
						result = sc.nextLine().toLowerCase();
						if (result.equals("positive") || result.equals("negative") || result.equals("null")) {
							res = stringToBoolean(result);
							break;
						}
					}
					table.update(dorm, num, res);
					return table.get(dorm + num).getResident();
				} else if (ans.equalsIgnoreCase("re")) { // replace the resident with a new one
					String[] newR = readResInfo(sc);
					Resident curr = new Resident(newR[0], stringToBoolean(newR[1]));
					// Room room = table.get(dorm+num);
					// table.remove(dorm+num);
					// room.removeResident();
					// room.addResident(curr);
					// table.userPut(dorm+num, room);
					table.update(dorm, num, table.get(dorm + num).getResident(), "remove");
					table.update(dorm, num, curr, "");
					return table.get(dorm + num).getResident();
				} else if (ans.equalsIgnoreCase("rm")) { // remove current resident
					table.update(dorm, num, table.get(dorm + num).getResident(), "remove");
					return null;
				}

			}
		} else { // update resident to an empty room
			String[] resident = readResInfo(sc);
			Resident res = new Resident(resident[0], stringToBoolean(resident[1]));
			table.get(dorm + num).addResident(res);
			return res;
		}

	}

	/**
	 * Convert Boolean type "true", "false", "null" to String "positive",
	 * "negative", "haven't taken test yet" respectively.
	 * 
	 * @param res the Boolean type to be converted
	 * @return corresponding string
	 */
	private static String booleanToString(Boolean res) {
		String result = "";
		if (res == null)
			result = "hasn't taken test yet";
		else if (res == false)
			result = "negative";
		else
			result = "positive";
		return result;
	}

	/**
	 * Convert String "positive", "negative", "null" to Boolean type "true",
	 * "false", or "null" respectively.
	 * 
	 * @param s the String o be converted
	 * @return corresponding Boolean type
	 */
	private static Boolean stringToBoolean(String s) {
		Boolean result = null;
		if (s.equalsIgnoreCase("positive")) {
			result = true;
		} else if (s.equalsIgnoreCase("negative")) {
			result = false;
		} else if (s.equalsIgnoreCase("null")) {
			result = null;
		}
		return result;
	}

	/**
	 * Search the resident's test result with given room info (dorm name + room
	 * number). This needs the resident's information has been added before.
	 * 
	 * @param sc scanner to read user input.
	 * @return the Resident object, null if the room info doesn't exist.
	 */
	public static Resident search(Scanner sc) {
		// read room info
		Object[] roomInfo = readRoomInfo(sc);
		String dorm = (String) roomInfo[0];
		long num = (long) roomInfo[1];
		// search operation
		if (!table.containsKey(dorm + num))
			return null;
		return table.get(dorm + num).getResident();
	}

	/**
	 * Clear all information in the table. This will ask user for a second time
	 * verification to continue the clear operation.
	 * 
	 * @param sc scanner to read user input.
	 */
	public static void clear(Scanner sc) {
		while (true) {
			System.out.println("Caution: this operation will delete all the data, are you sure to contiune?(y/n)");
			String ans = sc.nextLine();
			if (ans.equalsIgnoreCase("y")) {
				table.clear();
				return;
			} else if (ans.equalsIgnoreCase("n")) {
				System.out.println("Operation canceled.");
				return;
			}
		}
	}

	/**
	 * Help to list the menu.
	 * 
	 */
	private static void menu() {
		line();
		System.out.println("(A):Add test result. This will need your name, dorm name, and room number.\n"
				+ "(D):Delete test result. This will need your dorm name and room number.\n" + "(M):Show the menu."
				+ "(U):Update test result. If you have added your test result before, then use this to 'add' your information.\n"
				+ "(S):Search test result. This will need your dorm name and room number.\n" + "(Q):Quit."
				+ "(*):Clear all information.");
		line();
	}

	/**
	 * Help to create a line.
	 * 
	 */
	private static void line() {
		for (int i = 0; i < 50; ++i) {
			System.out.print("*-");
		}
		System.out.println("*");
	}

	/**
	 * Help to read the first character from user input and convert it to lowercase.
	 * 
	 * @param sc scanner to read user input
	 * @return the first character from user's input
	 */
	private static char firstChar(Scanner sc) {
		String input = sc.nextLine().toLowerCase();
		char first = '\0';
		if (input.length() == 0 || input == null) {
			return first;
		}
		return first = input.charAt(0);
	}

	/**
	 * Help to 'formalize' user input. For example, if the user input is "abCd EfG",
	 * the output will be "Abcd Efg".
	 * 
	 * @param string the string need formalized
	 * @return a formalized string
	 */
	private static String cap_string(String string) {
		String[] input_string = string.toLowerCase().split(" ");
		string = "";
		for (int i = 0; i < input_string.length; ++i) {
			string = input_string[i].substring(0, 1).toUpperCase() + input_string[i].substring(1);
		}
		return string;
	}

	/**
	 * Help to check if the string contains only letters.
	 * 
	 * @param s string need to be checked.
	 * @return true if the string contains only letters, false otherwise.
	 */
	private static boolean containsOnlyLetters(String s) {
		boolean onlyLetters = true;
		char[] c = null;
		if (s != null && !s.isEmpty()) {
			c = s.toCharArray();
			for (int i = 0; i < c.length; ++i) {
				if (!Character.isLetter(c[i])) {// once a char is digit, assign legal true and
					onlyLetters = false; // break
					break;
				}
			}
		}

		return onlyLetters;
	}

	private static String[] readResInfo(Scanner sc) {
		// user's name
		String name = "";
		System.out.println("Please enter your name:");
		while (true) {
			name = sc.nextLine();
			if (containsOnlyLetters(name))
				break;
			System.out.println("illegal name! Please enter again:");
		}
		name = cap_string(name);
		// user's test result
		System.out.println("Please enter your test result:(postive/negative/null)");
		String result = "";
		while (true) {
			result = sc.nextLine();
			if (containsOnlyLetters(result)) {
				if (result.equalsIgnoreCase("positive")) {
					break;
				} else if (result.equalsIgnoreCase("negative")) {
					break;
				} else if (result.equalsIgnoreCase("null")) {
					break;
				}
			}
			System.out.println("illegal result! Please enter again:");
		}
		String[] ret = new String[] { name, result };
		return ret;
	}

	private static Object[] readRoomInfo(Scanner sc) {
		// dorm name
		String dorm = "";
		System.out.println("Please enter your dorm name:");
		while (true) {
			dorm = sc.nextLine();
			if (containsOnlyLetters(dorm))
				break;
			System.out.println("illegal dorm name! Please enter again:");
		}
		dorm = cap_string(dorm);
		// dorm number
		long num;
		System.out.println("Please enter your room number:");
		while (true) {
			try {
				num = Integer.parseInt(sc.nextLine());
				if (num > 100 && num < 10000) {
					break;
				}
				System.out.println("illegal room number! Please try again:");
			} catch (Exception e) {
				System.out.println("illegal room number! Please try again:");
			}
		}
		return new Object[] { dorm, num };
	}

	public static void main(String[] arg) {
		if (table.loadData()) {
			Scanner sc = new Scanner(System.in);
			boolean first = true;

			char instr = '\0';
			line();
			System.out.println("Welcome to COVID-19 information of University Housing!\n"
					+ "In this application you will be able to add your test result, and delete or update it anytime!\n"
					+ "You can also search others' information if you know their name, dorm, and room number.");
			menu();
			while (instr != 'q') {
				if (!first)
					line();
				first = false;
				System.out.println("Enter your action:");
				instr = firstChar(sc);

				if (instr == 'a') {
					if (add(sc))
						System.out.println("added successfully!");
					else
						System.out.println("added failed :( Please try again.");
					continue;
				} else if (instr == 'd') {
					Room room = delete(sc);
					if (room == null)
						System.out.println("The room doesn't exist! Please try another one.");
					else
						System.out.println(
								"Room:" + room.getDormName() + ", " + room.getRoomNum() + " has been removed.");
					continue;
				} else if (instr == 'm') {
					menu();
				} else if (instr == 'u') {
					Resident res = update(sc);
					if (res == null) {
						System.out.println("This is an empty room now.");
						continue;
					}
					String result = booleanToString(res.getResult());
					System.out.println(
							"The resident has been updated:" + res.getName() + ", test result: " + result + ".");
				} else if (instr == 's') {
					Resident stu = search(sc);
					String result = "";
					if (stu == null)
						System.out.println("The room doesn't exist! Please try 'add'.");
					else {
						result = booleanToString(stu.getResult());
						if (stu.getResult() != null) {
							System.out.println("The resident in this room is " + stu.getName()
									+ ", and the test result is " + result + ".");
						} else {
							System.out.println("The resident in this room is " + stu.getName()
									+ ", and the resident hasn't taken any test yet.");
						}
					}

				} else if (instr == 'q')
					break;
				else if (instr == '*')
					clear(sc);
				else {
					System.out.println("Unknown action! Please try again!");
				}

			}

		}
	}
}
