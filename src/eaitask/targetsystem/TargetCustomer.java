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
	public int checkEquality(TargetCustomer compCustomer)
	{
		int equalityMeasure = 0;
		String street1 = getStreet(this.getAddress());
		String street2 = getStreet(compCustomer.getAddress());
		String zip1 = getZip(this.getAddress());
		String zip2 = getZip(compCustomer.getAddress());
		if(compCustomer.getLastname().toLowerCase().equals(this.getLastname().toLowerCase())){
			equalityMeasure +=1;
		}
				
		if(street1.equals(street2))
		{
			equalityMeasure +=1;
		}
		if(zip1.equals(zip2))
		{
			equalityMeasure +=1;
		}
		
		return equalityMeasure;
		
					
		
	}
	private String getZip(String input) {

		return input.substring(input.indexOf(",")+1, input.indexOf(" ", input.indexOf(",")+2));
		
	}
	private String getStreet(String input) {
		int index = 0;
		for(int i =0;i<input.length();i++)
		{
			if(input.substring(i,i+1).matches("[0-9]"))
			{
				
				index = i-1;
				break;
			}
			if(input.substring(i, i+1).matches("[\\.,]"))
			{
				index = i;
				break;
			}
		}
		return input.substring(0,index);
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
		System.out.println("Status: " + Status.print(status));
	}
}
