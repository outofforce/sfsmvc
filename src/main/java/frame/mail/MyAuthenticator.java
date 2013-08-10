package frame.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-5-31
 * Time: 下午12:11
 * To change this template use File | Settings | File Templates.
 */
public class MyAuthenticator extends Authenticator {
		String userName=null;
		String password=null;

		public MyAuthenticator(){
		}
		public MyAuthenticator(String username, String password) {
			this.userName = username;
			this.password = password;
		}
		protected PasswordAuthentication getPasswordAuthentication(){
			return new PasswordAuthentication(userName, password);
		}
	}
