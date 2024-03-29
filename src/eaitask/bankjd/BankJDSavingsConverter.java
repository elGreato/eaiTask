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
	private int vctHighestID;
	private ArrayList<TargetCustomer> customersForManualCheck;
	public void convert(
			ArrayList<BankJDSavings> jdSavings,
			ArrayList<TargetCustomer> targetCustomers,
			ArrayList<TargetAccount> targetAccounts, String BIC, int nextID,int vctHighestID, ArrayList<TargetCustomer> customersForManualCheck) {
		this.jdSavings = jdSavings;
		this.targetCustomers = targetCustomers;
		this.targetAccounts = targetAccounts;
		this.BIC = BIC;
		this.nextID = nextID;
		this.vctHighestID = vctHighestID;
		this.customersForManualCheck = customersForManualCheck;
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
				if(id == tc.getCid() && tc.getFirstname().matches("^[[A-Z][\\.][ ]?]*$"))
				{
					tc.setFirstname(targetCustomer.getFirstname());
				}
				if(id == tc.getCid() && id<=vctHighestID&&!targetCustomer.getLastname().equals(tc.getLastname()))
				{
					tc.setFirstname(targetCustomer.getFirstname());
					tc.setLastname(targetCustomer.getLastname());
				}
					
			}
			
		}
	}

	private int getID(TargetCustomer userProcessed, TargetAccount targetAccount) {
		
		for (TargetCustomer listUser : targetCustomers)
		{
			if((userProcessed.checkEquality(listUser))==3)
			{
				return listUser.getCid();
			}
			else if(listUser.getCid() <= vctHighestID&&listUser.getFirstname().contains(" "))
			{
				String[] firstNames = listUser.getFirstname().split(" ");
				for(int i = firstNames.length-1; i>=0; i--)
				{
					String newFirstName = new String();
					for (int j = 0; j<i;j++)
					{
						newFirstName += firstNames[j] + " ";
					}
					newFirstName.trim();
					String newLastName = firstNames[i] + " " + listUser.getLastname();
					TargetCustomer editedUser = new TargetCustomer(newFirstName,newLastName, listUser.getAddress(), listUser.getCountrycode());
					if(userProcessed.checkEquality(editedUser) == 3)
					{
						return listUser.getCid();
					}
					else if(userProcessed.checkEquality(editedUser) == 2 && 
							userProcessed.getLastname().equalsIgnoreCase(editedUser.getLastname()))
					{
						customersForManualCheck.add(userProcessed);
						customersForManualCheck.add(editedUser);
					}
				}

			}
			else if(userProcessed.checkEquality(listUser) == 2)
			{
				customersForManualCheck.add(userProcessed);
				customersForManualCheck.add(listUser);
			}
		}
		return nextID++;
	}

	private TargetAccount createTargetAccount(BankJDSavings account) {
		Main ibanclass = new Main();
		String accountNo = Long.valueOf(account.getAccountnumber()).toString();
		RecordIban recordiban = ibanclass.IBANConvert(new StringBuffer(BIC), new StringBuffer(accountNo)); 
		StringBuffer iban = recordiban.Iban;
		float accountBalance = (float)(Math.round(account.getAccountstatus()*(account.getInterestrate()+1)*IntegrationProcessor.euroExchangeRate*100))/100;
		while(iban.length() == 0&&accountNo.length()<=16)
		{
			accountNo = Utils.completeAccountNumber(accountNo);
			RecordIban recordibanupdated = ibanclass.IBANConvert(new StringBuffer(BIC), new StringBuffer(accountNo)); 
			iban = recordibanupdated.Iban;
			
		}
		
		return new TargetAccount(iban.toString(),accountBalance,TypeOfAccount.SAVINGS);
	}

	private TargetCustomer createTargetUser(BankJDSavings account) {
		String address = account.getStreet() + ", " + account.getZipandtown();

		TargetCustomer targetCustomer = new TargetCustomer(account.getFirstname(),account.getLastname(),address,"DE");
		
		return targetCustomer;
	}

	
}
