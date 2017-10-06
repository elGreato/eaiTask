package eaitask.bankjd;

public class BankJDSavings {
	private String firstname;
	private String lastname;
	private String street;
	private String zipandtown;
	private float interestrate;
	private long accountnumber;
	private long accountstatus;
	
	public BankJDSavings(String firstname, String lastname, String street,
			String zipandtown, float interestrate, long accountnumber,
			long accountstatus) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.street = street;
		this.zipandtown = zipandtown;
		this.interestrate = interestrate;
		this.accountnumber = accountnumber;
		this.accountstatus = accountstatus;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipandtown() {
		return zipandtown;
	}

	public void setZipandtown(String zipandtown) {
		this.zipandtown = zipandtown;
	}

	public float getInterestrate() {
		return interestrate;
	}

	public void setInterestrate(float interestrate) {
		this.interestrate = interestrate;
	}

	public long getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(long accountnumber) {
		this.accountnumber = accountnumber;
	}

	public long getAccountstatus() {
		return accountstatus;
	}

	public void setAccountstatus(long accountstatus) {
		this.accountstatus = accountstatus;
	}
}
