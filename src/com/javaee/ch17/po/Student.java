package com.javaee.ch17.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Student implements Serializable { // 实体类，一般都要实现可串行化接口Serializable，以常驻内存，便于系统各层调用。
	private static final long serialVersionUID = 1L;
	
	private int id; // 客户编号 自增 主键
	private String userName; // 客户登录用户名 希望是非空且不重复
	private String password; // 客户登录密码
	private String userRealName; // 客户真名
	private String gender; // 客户性别： 字符‘0’、‘1’分别表示'男'、'女'。默认值是'0'
	private String email; //客户邮箱 如特别重要，该字段应设成非空且不重复。
	private String contactPhone; // 客户联系电话：可有多个手机、电话
	private String address;
	private String memo;
	private Date creationTime; // 建立时间 默认值是做insert操作（可能是游客自己注册或由操作员添加）时的时刻点。
	private Date lastChangeTime; // 最近一次修改个人信息的日期时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getLastChangeTime() {
		return lastChangeTime;
	}
	public void setLastChangeTime(Date lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
	}

	
	
	/*
	// 为了在控制台便于显示该对象的各字段值，所以特意重写toString()方法。否则，是那种默认的 完整包名@十六进制数  这是并无大的实际意义。
	
	public String toString() {
		return "id:"+ id + ", userName:" + userName + ", password: " + password + ", userRealName:" + userRealName + ", gender:" + gender 
				+ ", email:" + email + ", contactPhone:" + contactPhone +", address:" + address 
				+ ", creationTime:" + creationTime;  
		
	}
	*/
}
/*
create table Student(
	id int primary key auto_increment,		
	userName varchar(30) not null unique,
	password varchar(32) not null,
	userRealName varchar(20),
	gender char(1) default '0',
	email varchar(50) unique,
	contactPhone varchar(50) unique,
	address varchar(100),
	memo text,
	creationTime datetime(3) default now(3),
	lastChangeTime datetime(3)
);

insert into Student(userName, password, userRealName, gender, email, contactPhone, address, memo)
		values('gduf002', 'wfqsdasefeb', '李明', '0', 'erhg@qq.com', '010-12345678', '北京市海淀区建设大道', 'wefresd');
insert into Student(userName, password, userRealName, gender, email, contactPhone, address, memo)
		values('gduf003', 'iigheb', '张三', '1', 'zsrerj@sohu.com', '020-52145678', '广州市天河区龙洞2号', '');
				
insert into Student(userName, password, userRealName, gender, email, contactPhone, address, memo)
		values('gduf004', 'wwhht', '李四', '1', 'lisi@126.com', '13023451987', '广州市白云区人和镇190号', '可以！');
insert into Student(userName, password, userRealName, gender, email, contactPhone, address, memo)
		values('gzgduf005', '33wwrgd', '王五', '0', 'ww@163.org.cn', '15109021642', '广州市海珠区人民南路241号', 'New 2021');
insert into Student(userName, password, userRealName, gender, email, contactPhone, address, memo)
		values('cngdetdg21', 'erghwf', '赵六', '0', 'etrhef@edu.cn', '156021429920', '佛山市南海区前锋北路50号', '不错的！');	
insert into Student(userName, password, userRealName, gender, email, contactPhone, address, memo)
		values('erthsdrok', '20okrvs', '王慧', '0', 'hwrvdf@ertbdf.cioj', '18613250680', '肇庆市应达西路109号', 'ewtrhef');
insert into Student(userName, password, userRealName, gender, email, contactPhone, address, memo)
		values('tdeonuflo', 'ecgwdljh', 'Tom', '0', 'theTom@cartoon.com', '15822991209', '江门市人民东路6号', 'ergsdweniuh');
insert into Student(userName, password, userRealName, gender, email, contactPhone, address, memo)
		values('rytjegg86', 'rrsvsdcx', 'Jerry', '0', 'JerryE@cartoon.org', '15066352501', '广州市越秀区庆峰北路190号', 'fherFEDeniuh');						
 */
