package bean.mybatisInterface;

import bean.bean.UserPublish;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-23
 * Time: 下午2:31
 * To change this template use File | Settings | File Templates.
 */
public interface QueryMsg {
	public List<UserPublish>  getMsg(String userId);
}
