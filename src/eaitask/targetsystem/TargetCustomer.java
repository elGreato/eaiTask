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
		this.firstname = removeUmlauts(firstname);
		this.lastname = removeUmlauts(lastname);
		this.address = removeUmlauts(address);
		this.countrycode = countrycode;
	}
	private String removeUmlauts(String input) {
		input = input.replace("ä", "ae");
		input = input.replace("ö", "oe");
		input = input.replace("ü", "ue");
		return input;	
	}
	public TargetCustomer(int cid, String firstname, String lastname,
			String address, String countrycode, Status status) {
		super();
		this.cid = cid;
		this.firstname = removeUmlauts(firstname);
		this.lastname = removeUmlauts(lastname);
		this.address = removeUmlauts(address);
		this.countrycode = countrycode;
		this.status = status;
		removeUmlauts(this.firstname);
		removeUmlauts(this.lastname);
		removeUmlauts(this.address);
	}
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof TargetCustomer)
		{
			TargetCustomer compCustomer = (TargetCustomer)o;
			String street1 = getStreet(this.getAddress());
			String street2 = getStreet(compCustomer.getAddress());
			String zip1 = getZip(compCustomer.getAddress());
			String zip2 = getZip(compCustomer.getAddress());
			if(!compCustomer.getFirstname().isEmpty()&&
					!compCustomer.getLastname().isEmpty()&&
					!compCustomer.getAddress().isEmpty()&&
					compCustomer.getLastname().toLowerCase().equals(this.getLastname().toLowerCase())&&
					street1.equals(street2)&&zip1.equals(zip2))
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
	private String getZip(String address2) {
		return address.substring(address.indexOf(",")+1, address.indexOf(" ", address.indexOf(",")+2));
	}
	private String getStreet(String address) {
		int index = 0;
		for(int i =0;i<address.length();i++)
		{
			if(address.substring(i,i+1).matches("^[0-9]"))
			{
				
				index = i;
				break;
			}
		}
		return address.substring(0,index-1);
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
