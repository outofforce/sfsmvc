package bean.bean;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-13
 * Time: 下午10:53
 * To change this template use File | Settings | File Templates.
 */
public class RecruitInfo extends BaseBean {
	public String recruitNum;
	public String baseCode;
	public String jobDesc;
	public String companyId;
	public String compAddr;
	public String position;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCompAddr() {
		return compAddr;
	}

	public void setCompAddr(String compAddr) {
		this.compAddr = compAddr;
	}

	public String getRecruitNum() {
		return recruitNum;
	}

	public void setRecruitNum(String recruitNum) {
		this.recruitNum = recruitNum;
	}

	public String getBaseCode() {
		return baseCode;
	}

	public void setBaseCode(String baseCode) {
		this.baseCode = baseCode;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
