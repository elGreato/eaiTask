package eaitask.bankjd;

import java.util.ArrayList;

import eaitask.targetsystem.TargetAccount;
import eaitask.targetsystem.TargetCustomer;
import eaitask.targetsystem.TypeOfAccount;

public class BankJDSavingsConverter {
	ArrayList<BankJDSavings> jdSavings;
	ArrayList<TargetCustomer> targetCustomers;
	ArrayList<TargetAccount> targetAccounts;
	
	public void convert(
			ArrayList<BankJDSavings> jdSavings,
			ArrayList<TargetCustomer> targetCustomers,
			ArrayList<TargetAccount> targetAccounts) {
		this.jdSavings = jdSavings;
		this.targetCustomers = targetCustomers;
		this.targetAccounts = targetAccounts;
		
		for(BankJDSavings account: jdSavings)
		{
			TargetCustomer targetCustomer = createTargetUser(account);
			TargetAccount targetAccount = createTargetAccount(account);
			addToTargetSystem(targetCustomer, targetAccount);
		}
	}

	private void addToTargetSystem(TargetCustomer targetCustomer,
			TargetAccount targetAccount) {
		int id = getID(targetCustomer, targetAccount);
		targetAccount.setCid(id);
		targetAccounts.add(targetAccount);
		if(id==targetCustomers.size())
		{
			targetCustomer.setCid(id);
			targetCustomers.add(targetCustomer);
		}
		else if(targetCustomers.get(id).getFirstname().matches("^[A-Z]\\.$"))
		{
			targetCustomers.get(id).setFirstname(targetCustomer.getFirstname());
		}
	}

	private int getID(TargetCustomer userProcessed, TargetAccount targetAccount) {
		
		for (TargetCustomer listUser : targetCustomers)
		{
			if(userProcessed.equals(listUser))
			{
				return listUser.getCid();
			}
		}
		return targetCustomers.size();
	}

	private TargetAccount createTargetAccount(BankJDSavings account) {
		
		return new TargetAccount(0,"",0,TypeOfAccount.SAVINGS);
	}

	private TargetCustomer createTargetUser(BankJDSavings account) {
		String address = account.getStreet() + ", " + account.getZipandtown();

		TargetCustomer targetCustomer = new TargetCustomer(account.getFirstname(),account.getLastname(),address,"DE");
		
		return targetCustomer;
	}

	
}
