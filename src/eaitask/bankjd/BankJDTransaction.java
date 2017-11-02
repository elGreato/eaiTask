package eaitask.bankjd;

public class BankJDTransaction {
	private String firstname;
	private String lastname;
	private String address;
	private String country;
	private int ranking;
	private String ibannumber;
	private float accountstatus;
	private String BIC;
	
	public BankJDTransaction(String firstname, String lastname, String address,
			String country, int ranking, String ibannumber,
			float accountstatus, String BIC) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.country = country;
		this.ranking = ranking;
		this.ibannumber = ibannumber;
		this.accountstatus = accountstatus;
		this.BIC = BIC;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public String getIbannumber() {
		return ibannumber;
	}
	public void setIbannumber(String ibannumber) {
		this.ibannumber = ibannumber;
	}
	public float getAccountstatus() {
		return accountstatus;
	}
	public void setAccountstatus(float accountstatus) {
		this.accountstatus = accountstatus;
	}
	public String getBIC() {
		return BIC;
	}
	public void setBIC(String bIC) {
		BIC = bIC;
	}
}
