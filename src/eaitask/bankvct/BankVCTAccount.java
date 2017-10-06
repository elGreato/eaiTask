package eaitask.bankvct;

public class BankVCTAccount {
	private int customerID;
	private String customername;
	private String streetname;
	private String zip;
	private String town;
	private String state;
	private String typeofcustomer;
	private long accountnumber;
	private float total;
	private long clearing;
	
	public BankVCTAccount(int customerID, String customername,
			String streetname, String zip, String town, String state,
			String typeofcustomer, long accountnumber, float total,
			long clearing) {
		super();
		this.customerID = customerID;
		this.customername = customername;
		this.streetname = streetname;
		this.zip = zip;
		this.town = town;
		this.state = state;
		this.typeofcustomer = typeofcustomer;
		this.accountnumber = accountnumber;
		this.total = total;
		this.clearing = clearing;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getStreetname() {
		return streetname;
	}

	public void setStreetname(String streetname) {
		this.streetname = streetname;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTypeofcustomer() {
		return typeofcustomer;
	}

	public void setTypeofcustomer(String typeofcustomer) {
		this.typeofcustomer = typeofcustomer;
	}

	public long getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(long accountnumber) {
		this.accountnumber = accountnumber;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public long getClearing() {
		return clearing;
	}

	public void setClearing(long clearing) {
		this.clearing = clearing;
	}
}
