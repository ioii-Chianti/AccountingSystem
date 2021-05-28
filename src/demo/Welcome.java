import java.util.*;
import java.io.*;

public class Welcome {
	static Scanner input;
	static boolean scannerEnable = false;
	
    /* method startScanner()
	 * Make scanner enable(call this method before any inputting starts).
	 *
	 * Time Complexity: O(1)
	 * */
	public static void startScanner() {
		if(scannerEnable)	return;
		input = new Scanner(System.in);
		scannerEnable = true;
	}
	
    /* method finishScanner()
	 * Make scanner unable(call this method after any inputting finishes).
	 *
	 * Time Complexity: O(1)
	 * */
	public static void finishScanner() {
		if(!scannerEnable)	return;
		input.close();
		scannerEnable = false;
	}

    /* method checkNumber()
	 * Check if the string consists of only digits.
     * 
     * @param str input string
     * @return true if it is correct, false otherwise
     *  * Example: checkNumber(1234567890) ; return true
	 *
	 * Time Complexity: O(N), where N is the length of the input string
	 * */
	public static boolean checkNumber(String str) {
		int sz = str.length();
		for (int i = 0; i < sz; i++)
			if(str.charAt(i) < '0' || str.charAt(i) > '9') return false;
		return true;
	}
	
    /* method checkID()
	 * Check if user ID exists in the Accounting System.
     * 
     * @param id input user ID
     * @param user all user ID in Accounting System
     * @return true if it exists, false otherwise
     *  * Example: checkID(15317546) ; return true
	 *
	 * Time Complexity: O(N), where N is the number of transactions.
	 * */
	public static boolean checkID(String id, List<String> userList) {
		boolean suc = false;
		for(String nowId : userList)
			if(id.equals(nowId))
				suc = true;
		return suc;
	}

    /* method checkOpt()
	 * Check if the operation is valid
     * 
     * @param op input operation
     * @return true if it is valid, false otherwise
     *  * Example: checkOp('Z') ; return false
	 *
	 * Time Complexity: O(1)
	 * */
	public static boolean checkOpt(String opt) {
		String validOpts = "ABCDQ";
		for(int i = 0; i < 5; i++)
			if(opt.equals(validOpts.substring(i, i + 1)))
				return true;
		return false;
	}
	
	/* method daysCalculator()
	 * Calculate how many days in specific month of specific year.
	 *
	 * @param year the year
	 * @param month the month
     * @return the number of days in the month
     *  * Example: daysCalculator(2020, 2); return 29
	 *
	 * Time Complexity: O(1)
	 * */
	static int daysCalculator(int year, int month) {
		boolean isLeap = (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0));
		int ret = 0;
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				ret = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				ret = 30;
				break;
			case 2:
				ret = isLeap ? 29 : 28;
				break;
			default:
				break;
		}
		return ret;
	}

    /* method checkDate()
	 * Check if the input date is valid.
     * 
     * @param date the date in eight-digits format (ex. 20201011)
     * @return true if it is valid, false otherwise
     *  * Example: checkDate(20200250) ; return false
	 *
	 * Time Complexity: O(1)
	 * */
	public static boolean checkDate(int date) {
		int year = date / 10000, month = date % 10000 / 100, day = date % 100;
		return 0 <= year && year <= 9999 && 1 <= month && month <= 12 && 1 <= day && day <= daysCalculator(year, month);
	}

    /* method checkSixDigitDate()
	 * Check if specific month of specific year is valid.
     * 
     * @param date the month in six-digits format (ex. 202010)
     * @return true if it is valid, false otherwise
     *  * Example: checkSixDigitDate(202113) ; return false
	 *
	 * Time Complexity: O(1)
	 * */
	public static boolean checkSixDigitDate(int date) {
		return checkDate(date * 100 + 1);
	}
	
	/* method totalExpenses()
	* Show the summation of expenses by the specific user.
	* @param id user ID
    * @param user all users in Accounting System
    * @param date all dates in Accounting System
    * @param expenses all expenses in Accounting System
	*
	* Time Complexity: O(N), where N is the number of transactions.
	* */
	static void totalExpenses(String id, List<String> userList, List<Integer> dateList, List<Integer> expensesList) {
		int total = 0;	
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).equals(id)) {
				total += expensesList.get(i);
			}
		}
		System.out.println("您的總支出為 " + total + " 元");
	}
	
	/* method allTransactions()
	 * Show all transactions(included date and expense) of the specific user.
     * @param id user ID
     * @param user all users in Accounting System
     * @param date all dates in Accounting System
     * @param expenses all expenses in Accounting System
	 *
	 * Time Complexity: O(N), where N is the number of transactions.
	 * */
	static void allTransactions(String id, List<String> userList, List<Integer> dateList, List<Integer> expensesList) {
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).equals(id)) {
				System.out.println(dateList.get(i)/10000 + "年" + dateList.get(i)%10000/100 + "月" + dateList.get(i)%100 + "日" + "   支出: " + expensesList.get(i));
			}
		}
	}
	
	/* method expensesOnASpecificDay()
	 * Show the expenses by the specific user on a specific date.
	 *
     * @param id user ID
     * @param user all users in Accounting System
     * @param date all dates in Accounting System
     * @param expenses all expenses in Accounting System
	 *
	 * Time Complexity: O(N), where N is the number of transactions.
	 * */
	static void expensesOnASpecificDay(String id, List<String> userList, List<Integer> dateList, List<Integer> expensesList) {
		System.out.println("請輸入八碼日期 (年月日): ");
		
		while(true) {
			String str = input.nextLine();
			if(str.length() != 8 || !checkNumber(str)) {
				System.out.println("日期格式輸入錯誤! 請輸入八碼日期 (年月日): ");
				continue;
			}
			if(!checkDate(Integer.parseInt(str))) {
				System.out.println("日期不存在! 請輸入合理的八碼日期 (年月): ");
				continue;
			}
			int currentDate = Integer.parseInt(str);
			
			int costSum = 0;
			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).equals(id) && dateList.get(i) == currentDate) {
					costSum += expensesList.get(i);
				}
			}			
			System.out.println("您在" + currentDate/10000 + "年" + currentDate%10000/100 + "月" + currentDate%100 + "日的支出為: " + costSum + " 元");
			break;
		}
	}
	
	/* method dailyAverageExpensesOnASpecificMonth()
	 * Show the average expenses by the specific user on a specific month.
	 *
     * @param id user ID
     * @param user all users in Accounting System
     * @param date all dates in Accounting System
     * @param expenses all expenses in Accounting System
	 *
	 * Time Complexity: O(N), where N is the number of transactions.
	 * */
	static void dailyAverageExpensesOnASpecificMonth(String id, List<String> userList, List<Integer> dateList, List<Integer> expensesList) {
		System.out.println("請輸入六碼日期 (年月): ");
		
		int sum = 0;
		int year = -1, month = -1;
		while(true) {
			String s = input.nextLine();
			
			if(s.length() != 6 || !checkNumber(s)) {
				System.out.println("日期格式輸入錯誤! 請輸入六碼日期 (年月): ");
				continue;
			}
			if(!checkSixDigitDate(Integer.parseInt(s))) {
				System.out.println("日期不存在! 請輸入合理的六碼日期 (年月): ");
				continue;
			}
			int currentDate = Integer.parseInt(s);
			year = currentDate / 100;	month = currentDate % 100;
			break;
		}
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).equals(id) && dateList.get(i) / 10000 == year && dateList.get(i) % 10000 / 100 == month) {
				sum += expensesList.get(i);
			}
		}
		double costSum = (Math.round((double)sum * 1000.0 / daysCalculator(year, month)) / 1000.0);
		System.out.println("您在" + year + "年" + month + "月的日平均支出為: " + costSum + " 元");
	}
	
	/* method actionListener()
	 * To take corresponding actions of the key inserted by user.
	 *
     * @param id user ID
     * @param user all users in Accounting System
     * @param date all dates in Accounting System
     * @param expenses all expenses in Accounting System
     * 
	 * Time Complexity: O(N), where N is the number of transactions
	 * */
	static void actionListener(String id, char opt, List<String> userList, List<Integer> dateList, List<Integer> expensesList) {
		switch(opt) {
			case 'A':
				totalExpenses(id, userList, dateList, expensesList);
				break;
			case 'B':
				allTransactions(id, userList, dateList, expensesList);
				break;
			case 'C':
				expensesOnASpecificDay(id, userList, dateList, expensesList);
				break;
			case 'D':
				dailyAverageExpensesOnASpecificMonth(id, userList, dateList, expensesList);
				break;
			/*
			case 'Q':
				//exit();
				break;
			*/
		}
	}

	/* method buildData()
	 * Create the data structure for all the transactions from the file "input.txt"
	 *
     * @param user all users in Accounting System
     * @param date all dates in Accounting System
     * @param expenses all expenses in Accounting System
     * 
	 * Time Complexity: O(N), where N is the number of transactions.
	 * */
	public static void buildData(List<String> userList, List<Integer> dateList, List<Integer> expensesList) {
		String filePath = "input.txt";
		try(BufferedReader inputFromFile = new BufferedReader(new FileReader(filePath))) {
			String str;
			while((str = inputFromFile.readLine()) != null) {
				int pre = 0, sz = str.length();
				for(int i=0;i<3;i++) {
					int l = pre, r = l;
					
					while(r < sz && str.charAt(r) >= '0' && str.charAt(r) <= '9')	r++;
					
					if(i == 0)
						userList.add(str.substring(l, r));
					else if(i == 1)
						dateList.add(Integer.parseInt(str.substring(l, r)));
					else
						expensesList.add(Integer.parseInt(str.substring(l, r)));
					
					pre = r + 1;
				}
			}
			inputFromFile.close();
		}
		catch(FileNotFoundException e){
			System.out.println("未找到所要檔案");
		}
		catch(IOException e){
			System.out.println("IO fail");
		}
	}	
	
	/* method getUserId()
	 * Get your user ID from keyboard.
	 *
     * @param userList all users in Accounting System
     * 
	 * Time Complexity: O(1)
	 * */
	public static String getUserId(List<String> userList) {
		String userId = "";
		while(true) {
			System.out.println("輸入 ID 或 Q (結束使用)? (ID 需要存在且僅由數字組成)");

			String in = input.nextLine();
			if(in.equals("Q")) {
				break;
			}
			if(in.length() == 0 || !checkNumber(in)) {
				System.out.println("輸入的ID非數字!");
				continue;
			}
			if(!checkID(in, userList)) {
				System.out.println("輸入的ID不存在!");
				continue;
			}
			userId = in;
			break;
		}
		return userId;
	}
	
	/* method getOpt()
	 * Get your option from keyboard.
	 *
	 * Time Complexity: O(1)
	 * */
	public static char getOpt() {
		String in = "";

		while(true) {
			System.out.println("");
			System.out.println("1) A 顯示總支出");
			System.out.println("2) B 顯示全部消費紀錄");
			System.out.println("3) C 顯示特定日期消費金額");
			System.out.println("4) D 顯示特定月份日平均消費金額");
			System.out.println("5) Q 離開系統");
			System.out.println("輸入指令:");
			
			in = input.nextLine();
			if(!checkOpt(in)) {
				System.out.println("無效的指令!");
				continue;
			}
			break;
		}

		return in.charAt(0);
	}
	
    public static void main(String[] args) {
        startScanner();
    	
    	List<String> userList = new ArrayList<String>();
        List<Integer> dateList = new ArrayList<Integer>();
        List<Integer> expensesList = new ArrayList<Integer>();
        
        buildData(userList, dateList, expensesList);
        
        String userId = getUserId(userList);
		if(userId.length() > 0) {
			System.out.println("");
			System.out.println("Welcome! " + userId);
			
			while(true) {
				char opt = getOpt();
				if(opt == 'Q')
					break;
				actionListener(userId, opt, userList, dateList, expensesList);
			}
		}
		
		System.out.println("Bye <3");
		
        finishScanner();
	}
}