package bean.mybatisInterface;

import bean.bean.UserDao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-27
 * Time: 下午2:54
 * To change this template use File | Settings | File Templates.
 */
public interface LoginInterface {
	public List getUser(UserDao user);
}
