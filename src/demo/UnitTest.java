import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

class UnitTest {
	private List<String> userList;
	private List<Integer> dateList;
	private List<Integer> expensesList;
	
	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private PrintStream stdOut = System.out;
	
	private InputStream stdIn = System.in;
	private boolean switchInputStream = false;

    /** method setUp()
	 * Get the information of all the transactions.
	 * Open scanner before testing. Also switch the output stream into specific string.
	 */
	@BeforeEach
	void setUp() throws Exception {
		userList = new ArrayList<String>();
		dateList = new ArrayList<Integer>();
        expensesList = new ArrayList<Integer>();
		Welcome.buildData(userList, dateList, expensesList);
	
		Welcome.startScanner();
		
	    System.setOut(new PrintStream(outContent));
	}
	
    /** method switchInputStream()
	 * Using the string to replace standard input stream("System.in").
     * 
     * @param inStr the string which be using to replace standard input stream
	 *
	 *	Example: switchInputStream("abc")
	 *	So the input by "System.in" would become "abc".
	 */
	void switchInputStream(String inStr) {
		ByteArrayInputStream inContent = new ByteArrayInputStream(inStr.getBytes());
		
		Welcome.finishScanner();
		System.setIn(inContent);
		Welcome.startScanner();
		
		switchInputStream = true;
	}

    /** method tearDown()
	 * Close scanner after testing. Also switch the output(and input) stream into standard output(and input).
	 */
	@AfterEach
	void tearDown() throws Exception {
		Welcome.finishScanner();
		
	    System.setOut(stdOut);
	    
	    if(!switchInputStream) {
			System.setIn(stdIn);
	    	switchInputStream = false;
	    }
	}
	 
	/**
	 * test method: totalExpenses
	 * test: test if the total expenses by "15317546" is correct
	*/
	@Test
	void testTotalExpenses_1() {
		String id = "15317546";
		int expectCostSum = 150;
		String expectOutput = "您的總支出為 " + expectCostSum + " 元\n";
		Welcome.totalExpenses(id, userList, dateList, expensesList);
		assertEquals(expectOutput, outContent.toString());
	}
	
	/**
	 * test method: totalExpenses
	 * test: test if the total expenses by "10000001" is correct
	*/
	@Test
	void testTotalExpenses_2() {
		String id = "10000001";
		int expectCostSum = 27264;
		String expectOutput = "您的總支出為 " + expectCostSum + " 元\n";
		Welcome.totalExpenses(id, userList, dateList, expensesList);
		assertEquals(expectOutput, outContent.toString());
	}
	
	/**
	 * test method: allTransactions
	 * test: test  if all the transactions by "15317546" are correct
	*/
	@Test
	void testAllTransactions_1() {
		String id = "15317546";
		String expectOutput = "2021年3月1日   支出: 150\n";
		Welcome.allTransactions(id, userList, dateList, expensesList);
		assertEquals(expectOutput, outContent.toString());
	}
	
	/**
	 * test method: allTransactions
	 * test: test  if all the transactions by "10000001" are correct
	*/
	@Test
	void testAllTransactions_2() {
		String id = "10000001";
		String expectOutput = "1996年10月1日   支出: 239\n"
				+ "1998年2月28日   支出: 1\n"
				+ "2000年1月23日   支出: 1024\n"
				+ "2004年2月29日   支出: 4000\n"
				+ "1998年2月28日   支出: 22000\n";
		Welcome.allTransactions(id, userList, dateList, expensesList);
		assertEquals(expectOutput, outContent.toString());
	}
	
	/**
	 * test method: expensesOnASpecificDay
	 * test: try some invalid input and then test the information by "15317546" in 2021/3/1
	*/
	@Test
	void testExpensesOnASpecificDay_1() {
		String id = "15317546";
		switchInputStream("hey\n\n12345678\n20010229\n020210301\n20210301\n");
		String expectOutput = "請輸入八碼日期 (年月日): \n"
				+  "日期格式輸入錯誤! 請輸入八碼日期 (年月日): \n"
				+  "日期格式輸入錯誤! 請輸入八碼日期 (年月日): \n"
				+  "日期不存在! 請輸入合理的八碼日期 (年月): \n"
				+  "日期不存在! 請輸入合理的八碼日期 (年月): \n"
				+  "日期格式輸入錯誤! 請輸入八碼日期 (年月日): \n"
				+  "您在2021年3月1日的支出為: 150 元\n";
		Welcome.expensesOnASpecificDay(id, userList, dateList, expensesList);
		assertEquals(expectOutput, outContent.toString());
	}
	
	/**
	 * test method: dailyAverageExpensesOnASpecificMonth
	 * test: test the information by "10000001" in 1998/2/28
	*/
	@Test
	void testExpensesOnASpecificDay_2() {
		String id = "10000001";
		switchInputStream("19980228\n");
		String expectOutput = "請輸入八碼日期 (年月日): \n"
				+ "您在1998年2月28日的支出為: 22001 元\n";
		Welcome.expensesOnASpecificDay(id, userList, dateList, expensesList);
		assertEquals(expectOutput, outContent.toString());
	}
	
	/**
	 * test method: dailyAverageExpensesOnASpecificMonth
	 * test: try some invalid input and then test the information by "15317546" in 2021/3
	*/
	@Test
	void testDailyAverageExpensesOnASpecificMonth_1() {
		String id = "15317546";
		switchInputStream("hey\n\n123456\n200013\n200000\n0202103\n202103\n");
		String expectOutput = "請輸入六碼日期 (年月): \n"
				+ "日期格式輸入錯誤! 請輸入六碼日期 (年月): \n"
				+ "日期格式輸入錯誤! 請輸入六碼日期 (年月): \n"
				+ "日期不存在! 請輸入合理的六碼日期 (年月): \n"
				+ "日期不存在! 請輸入合理的六碼日期 (年月): \n"
				+ "日期不存在! 請輸入合理的六碼日期 (年月): \n"
				+ "日期格式輸入錯誤! 請輸入六碼日期 (年月): \n"
				+ "您在2021年3月的日平均支出為: 4.839 元\n";
		Welcome.dailyAverageExpensesOnASpecificMonth(id, userList, dateList, expensesList);
		assertEquals(expectOutput, outContent.toString());
	}
	
	/**
	 * test method: dailyAverageExpensesOnASpecificMonth
	 * test: test the information by "10000001" in 1998/2
	*/
	@Test
	void testDailyAverageExpensesOnASpecificMonth_2() {
		String id = "10000001";
		switchInputStream("199802\n");
		String expectOutput = "請輸入六碼日期 (年月): \n"
				+ "您在1998年2月的日平均支出為: 785.75 元\n";
		Welcome.dailyAverageExpensesOnASpecificMonth(id, userList, dateList, expensesList);
		assertEquals(expectOutput, outContent.toString());
	}
	
	/**
	 * startScanner and finishScanner are used widely in Accounting System and Junit Test
	 * So it's unnecessary to test them particularly.
	@Test
	void testStartScanner_1() {
	}
	
	@Test
	void testStartScanner_2() {
	}
	
	@Test
	void testFinishScanner_1() {
	}
	
	@Test
	void testFinishScanner_2() {
	}
	*/
	
	/**
	 * test method: buildData
	 * test: if the user ID of all the transactions are correct
	*/
	@Test
	void testBuildData_1() {
		List<String> testUserList = new ArrayList<String>();
		List<Integer> testDateList = new ArrayList<Integer>();
		List<Integer> testExpensesList= new ArrayList<Integer>();
		
		Welcome.buildData(testUserList, testDateList, testExpensesList);
		assertEquals("15317546", testUserList.get(0));
		assertEquals("18501179", testUserList.get(1));
		assertEquals("17111563", testUserList.get(2));
		assertEquals("19254301", testUserList.get(3));
		assertEquals("13501324", testUserList.get(4));
		assertEquals("11254730", testUserList.get(5));
		assertEquals("10000001", testUserList.get(6));
		assertEquals("17111563", testUserList.get(7));
		assertEquals("19254301", testUserList.get(8));
		assertEquals("19254301", testUserList.get(9));
		assertEquals("13501324", testUserList.get(10));
		assertEquals("11254730", testUserList.get(11));
		assertEquals("12345678", testUserList.get(12));
		assertEquals("18501179", testUserList.get(13));
		assertEquals("17111563", testUserList.get(14));
		assertEquals("10000001", testUserList.get(15));
		assertEquals("11254730", testUserList.get(16));
		assertEquals("17111563", testUserList.get(17));
		assertEquals("19254301", testUserList.get(18));
		assertEquals("13501324", testUserList.get(19));
		assertEquals("18501179", testUserList.get(20));
		assertEquals("17111563", testUserList.get(21));
		assertEquals("19254301", testUserList.get(22));
		assertEquals("12345678", testUserList.get(23));
		assertEquals("19254301", testUserList.get(24));
		assertEquals("18501179", testUserList.get(25));
		assertEquals("17111563", testUserList.get(26));
		assertEquals("18501179", testUserList.get(27));
		assertEquals("10000001", testUserList.get(28));
		assertEquals("19254301", testUserList.get(29));
		assertEquals("18501179", testUserList.get(30));
		assertEquals("17111563", testUserList.get(31));
		assertEquals("10000001", testUserList.get(32));
		assertEquals("19254301", testUserList.get(33));
		assertEquals("17111563", testUserList.get(34));
		assertEquals("12345678", testUserList.get(35));
		assertEquals("19254301", testUserList.get(36));
		assertEquals("18501179", testUserList.get(37));
		assertEquals("18501179", testUserList.get(38));
		assertEquals("19254301", testUserList.get(39));
		assertEquals("18501179", testUserList.get(40));
		assertEquals("17111563", testUserList.get(41));
		assertEquals("19254301", testUserList.get(42));
		assertEquals("13501324", testUserList.get(43));
		assertEquals("10000001", testUserList.get(44));
		assertEquals("17111563", testUserList.get(45));
		assertEquals("19254301", testUserList.get(46));
		assertEquals("13501324", testUserList.get(47));
		assertEquals("11254730", testUserList.get(48));
		assertEquals("19254301", testUserList.get(49));
	}

	/**
	 * test method: buildData
	 * test: if the date of all the transactions are correct
	*/
	@Test
	void testBuildData_2() {
		List<String> testUserList = new ArrayList<String>();
		List<Integer> testDateList = new ArrayList<Integer>();
		List<Integer> testExpensesList= new ArrayList<Integer>();
		
		Welcome.buildData(testUserList, testDateList, testExpensesList);
		assertEquals(20210301, testDateList.get(0));
		assertEquals(20200422, testDateList.get(1));
		assertEquals(20210124, testDateList.get(2));
		assertEquals(20181225, testDateList.get(3));
		assertEquals(20191111, testDateList.get(4));
		assertEquals(20200713, testDateList.get(5));
		assertEquals(19961001, testDateList.get(6));
		assertEquals(20210102, testDateList.get(7));
		assertEquals(20181225, testDateList.get(8));
		assertEquals(20200618, testDateList.get(9));
		assertEquals(20191111, testDateList.get(10));
		assertEquals(20200713, testDateList.get(11));
		assertEquals(30000101, testDateList.get(12));
		assertEquals(20200229, testDateList.get(13));
		assertEquals(20211002, testDateList.get(14));
		assertEquals(19980228, testDateList.get(15));
		assertEquals(20200714, testDateList.get(16));
		assertEquals(20210102, testDateList.get(17));
		assertEquals(20201225, testDateList.get(18));
		assertEquals(20191121, testDateList.get(19));
		assertEquals(20200422, testDateList.get(20));
		assertEquals(20210123, testDateList.get(21));
		assertEquals(20180925, testDateList.get(22));
		assertEquals(20121212, testDateList.get(23));
		assertEquals(20191105, testDateList.get(24));
		assertEquals(20201120, testDateList.get(25));
		assertEquals(20210101, testDateList.get(26));
		assertEquals(20181223, testDateList.get(27));
		assertEquals(20000123, testDateList.get(28));
		assertEquals(20191101, testDateList.get(29));
		assertEquals(20200422, testDateList.get(30));
		assertEquals(20210130, testDateList.get(31));
		assertEquals(20040229, testDateList.get(32));
		assertEquals(20181201, testDateList.get(33));
		assertEquals(20211102, testDateList.get(34));
		assertEquals(11111111, testDateList.get(35));
		assertEquals(20191114, testDateList.get(36));
		assertEquals(20200421, testDateList.get(37));
		assertEquals(20191231, testDateList.get(38));
		assertEquals(20191121, testDateList.get(39));
		assertEquals(20200404, testDateList.get(40));
		assertEquals(20210104, testDateList.get(41));
		assertEquals(20181125, testDateList.get(42));
		assertEquals(20191101, testDateList.get(43));
		assertEquals(19980228, testDateList.get(44));
		assertEquals(20210124, testDateList.get(45));
		assertEquals(20200928, testDateList.get(46));
		assertEquals(20191003, testDateList.get(47));
		assertEquals(20200712, testDateList.get(48));
		assertEquals(20200303, testDateList.get(49));
	}	
	
	/**
	 * test method: buildData
	 * test: if the expenses of all the transactions are correct
	*/
	@Test
	void testBuildData_3() {
		List<String> testUserList = new ArrayList<String>();
		List<Integer> testDateList = new ArrayList<Integer>();
		List<Integer> testExpensesList= new ArrayList<Integer>();
		
		Welcome.buildData(testUserList, testDateList, testExpensesList);
		assertEquals(150, testExpensesList.get(0));
		assertEquals(399, testExpensesList.get(1));
		assertEquals(87, testExpensesList.get(2));
		assertEquals(1674, testExpensesList.get(3));
		assertEquals(10, testExpensesList.get(4));
		assertEquals(85, testExpensesList.get(5));
		assertEquals(239, testExpensesList.get(6));
		assertEquals(215, testExpensesList.get(7));
		assertEquals(1000, testExpensesList.get(8));
		assertEquals(33, testExpensesList.get(9));
		assertEquals(81000, testExpensesList.get(10));
		assertEquals(130, testExpensesList.get(11));
		assertEquals(999, testExpensesList.get(12));
		assertEquals(229, testExpensesList.get(13));
		assertEquals(1000000, testExpensesList.get(14));
		assertEquals(1, testExpensesList.get(15));
		assertEquals(1399, testExpensesList.get(16));
		assertEquals(123, testExpensesList.get(17));
		assertEquals(889, testExpensesList.get(18));
		assertEquals(50, testExpensesList.get(19));
		assertEquals(3000, testExpensesList.get(20));
		assertEquals(449, testExpensesList.get(21));
		assertEquals(4999, testExpensesList.get(22));
		assertEquals(1212, testExpensesList.get(23));
		assertEquals(10000, testExpensesList.get(24));
		assertEquals(357, testExpensesList.get(25));
		assertEquals(20, testExpensesList.get(26));
		assertEquals(180, testExpensesList.get(27));
		assertEquals(1024, testExpensesList.get(28));
		assertEquals(157, testExpensesList.get(29));
		assertEquals(36, testExpensesList.get(30));
		assertEquals(999, testExpensesList.get(31));
		assertEquals(4000, testExpensesList.get(32));
		assertEquals(1001, testExpensesList.get(33));
		assertEquals(102, testExpensesList.get(34));
		assertEquals(1111111111, testExpensesList.get(35));
		assertEquals(166, testExpensesList.get(36));
		assertEquals(128000000, testExpensesList.get(37));
		assertEquals(4096, testExpensesList.get(38));
		assertEquals(800, testExpensesList.get(39));
		assertEquals(650, testExpensesList.get(40));
		assertEquals(875, testExpensesList.get(41));
		assertEquals(383, testExpensesList.get(42));
		assertEquals(49, testExpensesList.get(43));
		assertEquals(22000, testExpensesList.get(44));
		assertEquals(125, testExpensesList.get(45));
		assertEquals(1500, testExpensesList.get(46));
		assertEquals(830000, testExpensesList.get(47));
		assertEquals(27, testExpensesList.get(48));
		assertEquals(3650, testExpensesList.get(49));
	}	
	
	/**
	 * test method: getUserId
	 * test: try some invalid input and then test valid input
	*/
	@Test
	void testGetUserId_1() {
		String id = "15317546";
		switchInputStream("Kirito\n \n\n123\n381283712983712983791827381273123\n" + id + "\n");
		String expectOutput = "輸入 ID 或 Q (結束使用)? (ID 需要存在且僅由數字組成)\n"
				+ "輸入的ID非數字!\n輸入 ID 或 Q (結束使用)? (ID 需要存在且僅由數字組成)\n"
				+ "輸入的ID非數字!\n輸入 ID 或 Q (結束使用)? (ID 需要存在且僅由數字組成)\n"
				+ "輸入的ID非數字!\n輸入 ID 或 Q (結束使用)? (ID 需要存在且僅由數字組成)\n"
				+ "輸入的ID不存在!\n輸入 ID 或 Q (結束使用)? (ID 需要存在且僅由數字組成)\n"
				+ "輸入的ID不存在!\n輸入 ID 或 Q (結束使用)? (ID 需要存在且僅由數字組成)\n";
		assertEquals(id, Welcome.getUserId(userList));
		assertEquals(expectOutput, outContent.toString());
	}

	/**
	 * test method: getUserId
	 * test: quit system directly
	*/
	@Test
	void testGetUserId_2() {
		switchInputStream("Q\n");
		String expectOutput = "輸入 ID 或 Q (結束使用)? (ID 需要存在且僅由數字組成)\n";
		assertEquals("", Welcome.getUserId(userList));
		assertEquals(expectOutput, outContent.toString());
	}
	
	/**
	 * test method: getOpt
	 * test: try some invalid input and then test valid input
	*/
	@Test
	void testGetOpt_1() {
		switchInputStream("a\nAA\nA \n A\n \n\nA\n");
		String welcome = "\n" + "1) A 顯示總支出\n" + "2) B 顯示全部消費紀錄\n" + "3) C 顯示特定日期消費金額\n" + "4) D 顯示特定月份日平均消費金額\n" + "5) Q 離開系統\n" + "輸入指令:\n";
		String expectOutput = welcome
				+ "無效的指令!\n" + welcome
				+ "無效的指令!\n" + welcome
				+ "無效的指令!\n" + welcome
				+ "無效的指令!\n" + welcome
				+ "無效的指令!\n" + welcome
				+ "無效的指令!\n" + welcome;
		assertEquals('A', Welcome.getOpt());
		assertEquals(expectOutput, outContent.toString());
	}
	
	/**
	 * test method: getOpt
	 * test: test all the valid input
	*/
	@Test
	void testGetOpt_2() {
		switchInputStream("A\nB\nC\nD\nQ\n");
		String welcome = "\n" + "1) A 顯示總支出\n" + "2) B 顯示全部消費紀錄\n" + "3) C 顯示特定日期消費金額\n" + "4) D 顯示特定月份日平均消費金額\n" + "5) Q 離開系統\n" + "輸入指令:\n";
		String expectOutput = welcome + welcome + welcome + welcome + welcome;
		assertEquals('A', Welcome.getOpt());
		assertEquals('B', Welcome.getOpt());
		assertEquals('C', Welcome.getOpt());
		assertEquals('D', Welcome.getOpt());
		assertEquals('Q', Welcome.getOpt());
		assertEquals(expectOutput, outContent.toString());
	}
	
	/**
	 * test method: DaysCalculator
	 * test months: all months in 2000 except February
	*/
	@Test
	void testDaysCalculator_1() {
		assertEquals(31, Welcome.daysCalculator(2000, 1));
		assertEquals(31, Welcome.daysCalculator(2000, 3));
		assertEquals(30, Welcome.daysCalculator(2000, 4));
		assertEquals(31, Welcome.daysCalculator(2000, 5));
		assertEquals(30, Welcome.daysCalculator(2000, 6));
		assertEquals(31, Welcome.daysCalculator(2000, 7));
		assertEquals(31, Welcome.daysCalculator(2000, 8));
		assertEquals(30, Welcome.daysCalculator(2000, 9));
		assertEquals(31, Welcome.daysCalculator(2000, 10));
		assertEquals(30, Welcome.daysCalculator(2000, 11));
		assertEquals(31, Welcome.daysCalculator(2000, 12));
	}
	
	/**
	 * test method: daysCalculator
	 * test months: February in different years, some are leap years
	*/
	@Test
	void testDaysCalculator_2() {
		assertEquals(28, Welcome.daysCalculator(1900, 2));
		assertEquals(28, Welcome.daysCalculator(1901, 2));
		assertEquals(28, Welcome.daysCalculator(1902, 2));
		assertEquals(28, Welcome.daysCalculator(1903, 2));
		assertEquals(29, Welcome.daysCalculator(1904, 2));
		assertEquals(29, Welcome.daysCalculator(2000, 2));
	}
	
	/**
	 * test method: checkNumber
	 * test cases: all valid digits
	*/
	@Test
	void TestCheckNumber_1() {
		assertTrue(Welcome.checkNumber("123"));
		assertTrue(Welcome.checkNumber("01234569"));
		assertTrue(Welcome.checkNumber("1389123128937218937982173981273981273981273981273981273928173891237"));
		
		assertTrue(Welcome.checkNumber(""));	// since the method only check if all the character are digits.
	}

	/**
	 * test method: checkNumber
	 * test cases: all included invalid characters
	*/
	@Test
	void TestCheckNumber_2() {
		assertFalse(Welcome.checkNumber("o123456q"));
		assertFalse(Welcome.checkNumber("0xAB"));
		assertFalse(Welcome.checkNumber(" 123"));
		assertFalse(Welcome.checkNumber("123 "));
		assertFalse(Welcome.checkNumber(" "));
		assertFalse(Welcome.checkNumber("\n"));
	}
	
	/**
	 * test method: checkID
	 * test accounts: accounts that exists in user list
	*/
	@Test
	void TestCheckID_1() {
		assertTrue(Welcome.checkID("10000001", userList));
		assertTrue(Welcome.checkID("11254730", userList));
		assertTrue(Welcome.checkID("12345678", userList));
		assertTrue(Welcome.checkID("13501324", userList));
		assertTrue(Welcome.checkID("15317546", userList));
		assertTrue(Welcome.checkID("17111563", userList));
		assertTrue(Welcome.checkID("18501179", userList));
		assertTrue(Welcome.checkID("19254301", userList));
	}
	
	/**
	 * test method: checkID
	 * test accounts: accounts that doesn't exist in user list and wrong input format
	*/
	@Test
	void TestCheckID_2() {
		assertFalse(Welcome.checkID("11111111", userList));
		assertFalse(Welcome.checkID("010000001", userList));
		assertFalse(Welcome.checkID(" 10000001", userList));
		assertFalse(Welcome.checkID("10000001 ", userList));
		assertFalse(Welcome.checkID("1000003123819238912738912739812739821739817239712939821793871298301", userList));
		assertFalse(Welcome.checkID(" ", userList));
		assertFalse(Welcome.checkID("\n", userList));
		assertFalse(Welcome.checkID("", userList));
	}
	
	/**
	 * test method: checkOpt
	 * test options: all valid keys
	*/
	@Test
	void TestCheckOpt_1() {
		assertTrue(Welcome.checkOpt("A"));
		assertTrue(Welcome.checkOpt("B"));
		assertTrue(Welcome.checkOpt("C"));
		assertTrue(Welcome.checkOpt("D"));
		assertTrue(Welcome.checkOpt("Q"));
	}
	
	/**
	 * test method: checkOpt
	 * test options: invalid keys
	*/
	@Test
	void TestCheckOpt_2() {
		assertFalse(Welcome.checkOpt("a"));
		assertFalse(Welcome.checkOpt("b"));
		assertFalse(Welcome.checkOpt("c"));
		assertFalse(Welcome.checkOpt("d"));
		assertFalse(Welcome.checkOpt("q"));
		assertFalse(Welcome.checkOpt("E"));
		assertFalse(Welcome.checkOpt("Z"));
		assertFalse(Welcome.checkOpt("AA"));
		assertFalse(Welcome.checkOpt("AB"));
		assertFalse(Welcome.checkOpt("A "));
		assertFalse(Welcome.checkOpt("65"));
		assertFalse(Welcome.checkOpt("0x41"));
		assertFalse(Welcome.checkOpt(""));
		assertFalse(Welcome.checkOpt(" "));
		assertFalse(Welcome.checkOpt("\n"));
	}

	/**
	 * test method: checkDate
	 * test cases: valid input formats of date
	*/
	@Test
	void TestCheckDate_1() {
		assertTrue(Welcome.checkDate(20001011));
		assertTrue(Welcome.checkDate(20200229));
		assertTrue(Welcome.checkDate(20000229));
		assertTrue(Welcome.checkDate(229));	// 0000/02/29
		assertTrue(Welcome.checkDate(11111111));
		assertTrue(Welcome.checkDate(19961001));
		assertTrue(Welcome.checkDate(19980228));
		assertTrue(Welcome.checkDate(20000123));
		assertTrue(Welcome.checkDate(20040229));
		assertTrue(Welcome.checkDate(20121212));
		assertTrue(Welcome.checkDate(20180925));
		assertTrue(Welcome.checkDate(20181125));
		assertTrue(Welcome.checkDate(20181201));
		assertTrue(Welcome.checkDate(20181223));
		assertTrue(Welcome.checkDate(20181225));
		assertTrue(Welcome.checkDate(20191003));
		assertTrue(Welcome.checkDate(20191101));
		assertTrue(Welcome.checkDate(20191105));
		assertTrue(Welcome.checkDate(20191111));
		assertTrue(Welcome.checkDate(20191114));
		assertTrue(Welcome.checkDate(20191121));
		assertTrue(Welcome.checkDate(20191231));
		assertTrue(Welcome.checkDate(20200229));
		assertTrue(Welcome.checkDate(20200303));
		assertTrue(Welcome.checkDate(20200404));
		assertTrue(Welcome.checkDate(20200421));
		assertTrue(Welcome.checkDate(20200422));
		assertTrue(Welcome.checkDate(20200618));
		assertTrue(Welcome.checkDate(20200712));
		assertTrue(Welcome.checkDate(20200713));
		assertTrue(Welcome.checkDate(20200714));
		assertTrue(Welcome.checkDate(20200928));
		assertTrue(Welcome.checkDate(20201120));
		assertTrue(Welcome.checkDate(20201225));
		assertTrue(Welcome.checkDate(20210101));
		assertTrue(Welcome.checkDate(20210102));
		assertTrue(Welcome.checkDate(20210104));
		assertTrue(Welcome.checkDate(20210123));
		assertTrue(Welcome.checkDate(20210124));
		assertTrue(Welcome.checkDate(20210130));
		assertTrue(Welcome.checkDate(20210301));
		assertTrue(Welcome.checkDate(20211002));
		assertTrue(Welcome.checkDate(20211102));
		assertTrue(Welcome.checkDate(30000101));
	}
	
	/**
	 * test method: checkDate
	 * test cases: invalid input formats of date or non-existent dates
	*/
	@Test
	void TestCheckDate_2() {
		assertFalse(Welcome.checkDate(20001234));
		assertFalse(Welcome.checkDate(20001200));
		assertFalse(Welcome.checkDate(20001301));
		assertFalse(Welcome.checkDate(20000001));
		assertFalse(Welcome.checkDate(20000230));
		assertFalse(Welcome.checkDate(20010229));
		assertFalse(Welcome.checkDate(19000229));
		assertFalse(Welcome.checkDate(200001010));
		assertFalse(Welcome.checkDate(2000010));
		assertFalse(Welcome.checkDate(200001));
		assertFalse(Welcome.checkDate(2000));
		assertFalse(Welcome.checkDate(200));
		assertFalse(Welcome.checkDate(20));
		assertFalse(Welcome.checkDate(2));
	}
	
	/**
	 * test method: checkSixDigitDate
	 * test cases: valid input formats of six-digits date
	*/
	@Test
	void TestCheckSixDigitDate_1() {
		assertTrue(Welcome.checkSixDigitDate(200010));
		assertTrue(Welcome.checkSixDigitDate(1));	// 0000/01
		
		assertTrue(Welcome.checkSixDigitDate(111111));
		assertTrue(Welcome.checkSixDigitDate(199610));
		assertTrue(Welcome.checkSixDigitDate(199802));
		assertTrue(Welcome.checkSixDigitDate(200001));
		assertTrue(Welcome.checkSixDigitDate(200402));
		assertTrue(Welcome.checkSixDigitDate(201212));
		assertTrue(Welcome.checkSixDigitDate(201809));
		assertTrue(Welcome.checkSixDigitDate(201811));
		assertTrue(Welcome.checkSixDigitDate(201812));
		assertTrue(Welcome.checkSixDigitDate(201910));
		assertTrue(Welcome.checkSixDigitDate(201911));
		assertTrue(Welcome.checkSixDigitDate(201912));
		assertTrue(Welcome.checkSixDigitDate(202002));
		assertTrue(Welcome.checkSixDigitDate(202003));
		assertTrue(Welcome.checkSixDigitDate(202004));
		assertTrue(Welcome.checkSixDigitDate(202006));
		assertTrue(Welcome.checkSixDigitDate(202007));
		assertTrue(Welcome.checkSixDigitDate(202009));
		assertTrue(Welcome.checkSixDigitDate(202011));
		assertTrue(Welcome.checkSixDigitDate(202012));
		assertTrue(Welcome.checkSixDigitDate(202101));
		assertTrue(Welcome.checkSixDigitDate(202103));
		assertTrue(Welcome.checkSixDigitDate(202110));
		assertTrue(Welcome.checkSixDigitDate(202111));
		assertTrue(Welcome.checkSixDigitDate(300001));
	}
	
	/**
	 * test method: checkSixDigitDate
	 * test cases: invalid input formats of six-digits date or non-existent six-digits dates
	*/
	@Test
	void TestCheckSixDigitDate_2() {
		assertFalse(Welcome.checkSixDigitDate(200019));
		assertFalse(Welcome.checkSixDigitDate(200000));
		assertFalse(Welcome.checkSixDigitDate(200013));
		assertFalse(Welcome.checkSixDigitDate(0));
		assertFalse(Welcome.checkSixDigitDate(13));
		assertFalse(Welcome.checkSixDigitDate(2000019));
		assertFalse(Welcome.checkSixDigitDate(-87));
	}
	
	/**
	 * For testing main method accurately, refer to integration test part: "IntegrationTest.java"
	 */
	@Test
	void mainTest_1() {
		switchInputStream("11254730\nA\nQ\n");

		Welcome.main(null);
		assertEquals("輸入 ID 或 Q (結束使用)? (ID 需要存在且僅由數字組成)\n" + 
				"\n" +
				"Welcome! 11254730\n" + 
				"\n" + 
				"1) A 顯示總支出\n" + 
				"2) B 顯示全部消費紀錄\n" + 
				"3) C 顯示特定日期消費金額\n" + 
				"4) D 顯示特定月份日平均消費金額\n" + 
				"5) Q 離開系統\n" + 
				"輸入指令:\n" + 
				"您的總支出為 1641 元\n" + 
				"\n" + 
				"1) A 顯示總支出\n" + 
				"2) B 顯示全部消費紀錄\n" + 
				"3) C 顯示特定日期消費金額\n" + 
				"4) D 顯示特定月份日平均消費金額\n" + 
				"5) Q 離開系統\n" + 
				"輸入指令:\n" + 
				"Bye <3\n", outContent.toString());
	}
	
	@Test
	void mainTest_2() {
		switchInputStream("Q\n");

		Welcome.main(null);
		assertEquals("輸入 ID 或 Q (結束使用)? (ID 需要存在且僅由數字組成)\n" + 
				"Bye <3\n", outContent.toString());
	}
}