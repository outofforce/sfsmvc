package bean.DaoImpl;

import bean.Dao.UserUpdateDao;
import bean.bean.UserDao;
import bean.mybatisInterface.UserUpdateInterface;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-9-5
 * Time: 下午2:42
 * To change this template use File | Settings | File Templates.
 */
public class UserUpdateDaoImpl implements UserUpdateDao {
	public UserUpdateInterface userUpdateInterface;

	public UserUpdateInterface getUserUpdateInterface() {
		return userUpdateInterface;
	}

	public void setUserUpdateInterface(UserUpdateInterface userUpdateInterface) {
		this.userUpdateInterface = userUpdateInterface;
	}
	@Override
	public int updateUser(UserDao userDao) {
		return userUpdateInterface.updateUserImg(userDao);
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
