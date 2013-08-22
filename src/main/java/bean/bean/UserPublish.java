package bean.bean;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-20
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */
public class UserPublish extends BaseBean{
	private String userId;
	private String postImg;
	private String postContext;
	private String simpleImg;
	private String gisInfo;
	private String ttl_type;
	private String create_time;
	private String userName;
	private String nickName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPostImg() {
		return postImg;
	}

	public void setPostImg(String postImg) {
		this.postImg = postImg;
	}

	public String getPostContext() {
		return postContext;
	}

	public void setPostContext(String postContext) {
		this.postContext = postContext;
	}

	public String getSimpleImg() {
		return simpleImg;
	}

	public void setSimpleImg(String simpleImg) {
		this.simpleImg = simpleImg;
	}

	public String getGisInfo() {
		return gisInfo;
	}

	public void setGisInfo(String gisInfo) {
		this.gisInfo = gisInfo;
	}

	public String getTtl_type() {
		return ttl_type;
	}

	public void setTtl_type(String ttl_type) {
		this.ttl_type = ttl_type;
	}
}
