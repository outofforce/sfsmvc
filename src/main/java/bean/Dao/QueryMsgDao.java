package bean.Dao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-23
 * Time: 下午2:34
 * To change this template use File | Settings | File Templates.
 */
public interface QueryMsgDao {
	public List getMsgList(String userId);
}
