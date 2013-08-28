package bean.DaoImpl;

import bean.Dao.LoginDao;
import bean.bean.UserDao;
import bean.mybatisInterface.LoginInterface;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-27
 * Time: 下午2:54
 * To change this template use File | Settings | File Templates.
 */
public class LoginDaoImpl implements LoginDao{
	public LoginInterface loginInterface;

	public LoginInterface getLoginInterface() {
		return loginInterface;
	}

	public void setLoginInterface(LoginInterface loginInterface) {
		this.loginInterface = loginInterface;
	}

	@Override
	public List getUserList(UserDao user) {
		return loginInterface.getUser(user);  //To change body of implemented methods use File | Settings | File Templates.
	}
}
