package bean.bean;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-21
 * Time: 下午4:08
 * To change this template use File | Settings | File Templates.
 */
public class UserRelation extends BaseBean{
	private String  userId;
	private String relationType;
	private String relaUserId;
	private String chgTime;
	private Date inactiveTime;
	private String nickName;
	private int count;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getRelaUserId() {
		return relaUserId;
	}

	public void setRelaUserId(String relaUserId) {
		this.relaUserId = relaUserId;
	}

	public String getChgTime() {
		return chgTime;
	}

	public void setChgTime(String chgTime) {
		this.chgTime = chgTime;
	}

	public Date getInactiveTime() {
		return inactiveTime;
	}

	public void setInactiveTime(Date inactiveTime) {
		this.inactiveTime = inactiveTime;
	}
}
