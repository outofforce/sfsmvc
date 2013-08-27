package bean.bean;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-23
 * Time: 上午10:25
 * To change this template use File | Settings | File Templates.
 */
public class UserMsgQueue extends BaseBean{
	private int user_id;
	private int publish_id;
	private Date inactiveTime;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getPublish_id() {
		return publish_id;
	}

	public void setPublish_id(int publish_id) {
		this.publish_id = publish_id;
	}

	public Date getInactiveTime() {
		return inactiveTime;
	}

	public void setInactiveTime(Date inactiveTime) {
		this.inactiveTime = inactiveTime;
	}
}
