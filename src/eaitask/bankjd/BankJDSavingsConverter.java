package eaitask.bankjd;

import java.util.ArrayList;

import eaitask.IntegrationProcessor;
import eaitask.targetsystem.TargetAccount;
import eaitask.targetsystem.TargetCustomer;
import eaitask.targetsystem.TypeOfAccount;
import ch.sic.ibantool.*;
import eaitask.Utils;
public class BankJDSavingsConverter {
	private ArrayList<BankJDSavings> jdSavings;
	private ArrayList<TargetCustomer> targetCustomers;
	private ArrayList<TargetAccount> targetAccounts;
	private String BIC;
	private int nextID;
	public void convert(
			ArrayList<BankJDSavings> jdSavings,
			ArrayList<TargetCustomer> targetCustomers,
			ArrayList<TargetAccount> targetAccounts, String BIC, int nextID) {
		this.jdSavings = jdSavings;
		this.targetCustomers = targetCustomers;
		this.targetAccounts = targetAccounts;
		this.BIC = BIC;
		this.nextID = nextID;
		for(BankJDSavings account: jdSavings)
		{
			account.setStreet(Utils.trim(account.getStreet()));
			account.setFirstname(Utils.trim(account.getFirstname()));
			account.setLastname(Utils.trim(account.getLastname()));
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
		if(id>targetCustomers.get(targetCustomers.size()-1).getCid())
		{
			targetCustomer.setCid(id);
			targetCustomers.add(targetCustomer);
		}
		else 
		{
			for(TargetCustomer tc : targetCustomers)
			{
				if(id == tc.getCid() && tc.getFirstname().matches("^[A-Z]\\.$"))
				{
					targetCustomers.get(id).setFirstname(targetCustomer.getFirstname());
				}
					
			}
			
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
		return nextID++;
	}

	private TargetAccount createTargetAccount(BankJDSavings account) {
		Main ibanclass = new Main();
		String accountNo = Long.valueOf(account.getAccountnumber()).toString();
		RecordIban recordiban = ibanclass.IBANConvert(new StringBuffer(BIC), new StringBuffer(accountNo)); 
		StringBuffer iban = recordiban.Iban;
		
		while(iban.length() == 0&&accountNo.length()<=16)
		{
			accountNo = Utils.completeAccountNumber(accountNo);
			RecordIban recordibanupdated = ibanclass.IBANConvert(new StringBuffer(BIC), new StringBuffer(accountNo)); 
			iban = recordibanupdated.Iban;
			
		}

		return new TargetAccount(iban.toString(),(float)(account.getAccountstatus()*IntegrationProcessor.dollarExchangeRate),TypeOfAccount.SAVINGS);
	}

	private TargetCustomer createTargetUser(BankJDSavings account) {
		String address = account.getStreet() + ", " + account.getZipandtown();

		TargetCustomer targetCustomer = new TargetCustomer(account.getFirstname(),account.getLastname(),address,"DE");
		
		return targetCustomer;
	}

	
}
