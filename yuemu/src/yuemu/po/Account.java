package yuemu.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 账户编码
	 */
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*
	 * 邮箱
	 */
	@Column(name = "email", nullable = false, unique = true)
	private String email = "";

	/*
	 * 密码
	 */
	private String password = "";

	/*
	 * 姓名
	 */
	private String name = "";

	/*
	 * 性别
	 */
	private Sex sex = Sex.MALE;

	/*
	 * 出生日期.
	 */
	private Date birthday = new Date();

	/*
	 * 联系方式
	 */
	private String contact = "";

	/*
	 * 地址
	 */

	private String address = "";

	/*
	 * 职业
	 */
	private String profession = "";

	private String portrait = "";

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	/*
	 * 用户类型
	 */
	private UserType userType = UserType.CUSTOMER;

	public Account() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", email=" + email + ", password="
				+ password + ", name=" + name + ", sex=" + sex + ", birthday="
				+ birthday + ", contact=" + contact + ", address=" + address
				+ ", profession=" + profession + ", userType=" + userType + "]";
	}

	public JSONObject toJSON() {
		JSONObject uploader = new JSONObject();
		try {
			uploader.put("accountId", this.getId());
			uploader.put("uploaderName", this.getName());
			uploader.put("accountEmail", this.getEmail());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return uploader;
	}

}
