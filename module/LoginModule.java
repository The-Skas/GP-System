package module;
import framework.GPSISModule;

import java.util.*;
import object.StaffMember;
import mapper.StaffMemberDMO;
public class LoginModule extends GPSISModule {
	
	private boolean loggedIn = false;
	
	public void run()
	{
		System.out.println("---------------------------");
		System.out.println("| Login                   |");
		System.out.println("---------------------------");
		
		Scanner sc = new Scanner(System.in);
		
		
		
		while (!loggedIn)
		{
			System.out.print("| Username: ");
			String username = sc.nextLine();
			System.out.print("| Password: ");
			String password = sc.nextLine();
			this.doLogin(username, password);
		}
	}
	
	private boolean doLogin(String username, String password)
	{
		StaffMemberDMO dmo = new StaffMemberDMO();
		HashMap<String, String> filter = new HashMap<String, String>();
		
		filter.put("username", username);
		filter.put("full_time", "1");
		StaffMember sM = dmo.getByProperties(filter);
		
		String pwd = new String(sM.getEncryptedPassword());
		String testPwd = new String(StaffMember.encrypt(password));
		
		
		if (pwd.trim().equals(testPwd.trim()))
		{
			System.out.println("Successfully Logged In! :D");
			this.loggedIn = true;
			return true;
		}
		System.out.println(pwd.trim() + " != " + testPwd.trim());
		return false;
	}
}
