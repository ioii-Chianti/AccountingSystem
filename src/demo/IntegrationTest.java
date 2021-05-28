import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

class IntegrationTest {
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
	
	    System.setOut(new PrintStream(outContent));
		
		Welcome.startScanner();
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
	
    /** method getContentFromFile()
	 * Get test data from file(in specific format) and divide them into two string.
     * 
     * @param filePath the path of the file
     * @param tempStr for storing the two result string
     *	
     *	Example: using this method to divide the following content:
     *  ### Test 001 ###
     *	$input
     *	&input
     *	$output
     *	輸入 ID 或 Q (結束使用)? (ID 需要存在且僅由數字組成)
     *	&output
     *	
     *	### Test 002 ###
     *	$input
     *	Q
     *	&input
     *	$output
     *	Bye <3
     *	&output
	 *
	 *	tempStr would becomes: {"Q\n", "輸入 ID 或 Q (結束使用)? (ID 需要存在且僅由數字組成)\nBye <3\n"}.
	 */
	void getContentFromFile(String filePath, String[] tempStr) {
		tempStr[0] = tempStr[1] = "";
		try(BufferedReader inputFromFile = new BufferedReader(new FileReader(filePath))) {
			String str;
			while((str = inputFromFile.readLine()) != null) {
				if(str.length() >= 3 && str.substring(0, 3).equals("###")) {
					String in = "", out = "";

					str = inputFromFile.readLine();
					while(!(str = inputFromFile.readLine()).equals("&input")) {
						in = in + str + "\n";
					}
					tempStr[0] += in;
					
					str = inputFromFile.readLine();
					while(!(str = inputFromFile.readLine()).equals("&output")) {
						out = out + str + "\n";
					}
					tempStr[1] += out;
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
	
	/**
	 * Integration test for all the function except quiting in the begging of accounting system.
	 * Testing user ID is 11254730.
	 * Refer to "MainTest_1.txt" for more detail.
	*/@Test
	void mainTest_1() {
		String[] tempStr = new String[2];
		getContentFromFile("MainTest_1.txt", tempStr);
		String inContentFromFile = tempStr[0], outContentFromFile = tempStr[1].replaceAll("\n", "");
		
		switchInputStream(inContentFromFile);
		Welcome.main(null);
		assertEquals(outContentFromFile, outContent.toString().replaceAll("\n", ""));
	}
	
	/**
	 * Test the function which didn't tested in "mainTest_1".
	 * Refer to "MainTest_2.txt" for more detail.
	*/@Test
	void mainTest_2() {
		String[] tempStr = new String[2];
		getContentFromFile("MainTest_2.txt", tempStr);
		String inContentFromFile = tempStr[0], outContentFromFile = tempStr[1].replaceAll("\n", "");
		
		
		switchInputStream(inContentFromFile);
		Welcome.main(null);
		assertEquals(outContentFromFile, outContent.toString().replaceAll("\n", ""));
	}
}
