package bean.Dao;

import bean.bean.UserDao;
import bean.bean.UserRelation;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-22
 * Time: 下午4:58
 * To change this template use File | Settings | File Templates.
 */
public interface UserRelationInterface {
	public List<UserDao> getUserRelationList(UserRelation userRelation);
}
