package com.so.demosboot.modules.sys.entity;

public class UserEx {
	
	protected Integer id;
	protected Integer pageNo=1;
	protected Integer pageSize;
	protected String sqlStr;


	private String usermine;
	private String userto;
	private String msg;
	private String createdate;
	private String bak1;
	private String bak2;

	
	public String getUsermine() {
		return usermine;
	}

	public void setUsermine(String usermine) {
		this.usermine = usermine;
	}

	public String getUserto() {
		return userto;
	}

	public void setUserto(String userto) {
		this.userto = userto;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getBak1() {
		return bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak2() {
		return bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

}
