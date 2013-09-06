package bean.bean;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-28
 * Time: 上午11:21
 * To change this template use File | Settings | File Templates.
 */
public class MsgRelaBean {
	private String userId;
	private String watcherId;
	private String publishId;

	public String getPublishId() {
		return publishId;
	}

	public void setPublishId(String publishId) {
		this.publishId = publishId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWatcherId() {
		return watcherId;
	}

	public void setWatcherId(String watcherId) {
		this.watcherId = watcherId;
	}
}
