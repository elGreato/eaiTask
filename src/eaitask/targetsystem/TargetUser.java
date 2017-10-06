package eaitask.targetsystem;

public class TargetUser {
	private int cid;
	private String firstname;
	private String lastname;
	private String address;
	private String countrycode;
	private Status status;
	
	public TargetUser(int cid, String firstname, String lastname,
			String address, String countrycode, Status status) {
		super();
		this.cid = cid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.countrycode = countrycode;
		this.status = status;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
