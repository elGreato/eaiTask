package eaitask.targetsystem;

public class TargetAccount {
	private int cid;
	private String iban;
	private float accountbalance;
	private TypeOfAccount typeofaccount;
	
	public TargetAccount(int cid, String iban, float accountbalance,
			TypeOfAccount typeofaccount) {
		super();
		this.cid = cid;
		this.iban = iban;
		this.accountbalance = accountbalance;
		this.typeofaccount = typeofaccount;
	}
	public TargetAccount(String iban, float accountbalance,
			TypeOfAccount typeofaccount) {
		super();
		this.iban = iban;
		this.accountbalance = accountbalance;
		this.typeofaccount = typeofaccount;
	}
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban; 
	}

	public float getAccountbalance() {
		return accountbalance;
	}

	public void setAccountbalance(float accountbalance) {
		this.accountbalance = accountbalance;
	}

	public TypeOfAccount getTypeofaccount() {
		return typeofaccount;
	}

	public void setTypeofaccount(TypeOfAccount typeofaccount) {
		this.typeofaccount = typeofaccount;
	}
	public void print()
	{
		System.out.println("Account:");
		System.out.println("CID: " + cid);
		System.out.println("IBAN: " + iban);
		System.out.println("Account Balance: " + accountbalance);
		System.out.println("Type of Account: " + TypeOfAccount.print(typeofaccount));
		System.out.println("***********");
	}
	
}
