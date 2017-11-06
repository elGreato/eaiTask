package eaitask.targetsystem;

public class TargetCustomer {
	private int cid;
	private String firstname;
	private String lastname;
	private String address;
	private String countrycode;
	private Status status;
	
	public TargetCustomer(String firstname, String lastname,
			String address, String countrycode) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.countrycode = countrycode;
	}
	public TargetCustomer(int cid, String firstname, String lastname,
			String address, String countrycode, Status status) {
		super();
		this.cid = cid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.countrycode = countrycode;
		this.status = status;
	}
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof TargetCustomer)
		{
			TargetCustomer compCustomer = (TargetCustomer)o;
			if(!compCustomer.getFirstname().isEmpty()&&!compCustomer.getLastname().isEmpty()&&!compCustomer.getAddress().isEmpty()&&
					compCustomer.getFirstname().charAt(0) == this.getFirstname().charAt(0)&&compCustomer.getLastname().equals(this.getLastname())&&compCustomer.getAddress().equals(this.getAddress()))
			{
				return true;
			}
			else
			{
				return false;
			}
					
		}
		else
		{
			return super.equals(o);
		}
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
	public void print() {
		System.out.println("Customer:");
		System.out.println("CID: " + cid);
		System.out.println("First name: " + firstname);
		System.out.println("Last name: " + lastname);
		System.out.println("Address: " + address);
		System.out.println("Country Code: " + countrycode);
	}
}
