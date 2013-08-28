package bean.DaoImpl;

import bean.Dao.QueryMsgDao;
import bean.bean.MsgRelaBean;
import bean.mybatisInterface.QueryMsg;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-23
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */
public class QueryMsgDaoImpl implements QueryMsgDao {
	public QueryMsg queryMsg;

	public QueryMsg getQueryMsg() {
		return queryMsg;
	}

	public void setQueryMsg(QueryMsg queryMsg) {
		this.queryMsg = queryMsg;
	}

	@Override
	public List getMsgList(MsgRelaBean relaBean) {
		return this.queryMsg.getMsg(relaBean);  //To change body of implemented methods use File | Settings | File Templates.
	}
}
