package eaitask.bankjd;

import java.util.ArrayList;

import eaitask.IntegrationProcessor;
import eaitask.targetsystem.Status;
import eaitask.targetsystem.TargetAccount;
import eaitask.targetsystem.TargetCustomer;
import eaitask.targetsystem.TypeOfAccount;

public class BankJDTransactionConverter {
	private ArrayList<BankJDTransaction> jdTransactions;
	private ArrayList<TargetCustomer> targetCustomers;
	private ArrayList<TargetAccount> targetAccounts;
	private ArrayList<TargetCustomer> customersForManualCheck;
	private int nextID;
	private int vctHighestID;
	public void convert(
			ArrayList<BankJDTransaction> jdTransactions,
			ArrayList<TargetCustomer> targetCustomers,
			ArrayList<TargetAccount> targetAccounts,
			int nextID,
			int vctHighestID, 
			ArrayList<TargetCustomer> customersForManualCheck) {
		this.jdTransactions = jdTransactions;
		this.targetCustomers = targetCustomers;
		this.targetAccounts = targetAccounts;
		this.nextID = nextID;
		this.vctHighestID = vctHighestID;
		this.customersForManualCheck = customersForManualCheck;
		for(BankJDTransaction account: jdTransactions)
		{
			TargetCustomer targetCustomer = createTargetUser(account);
			TargetAccount targetAccount = createTargetAccount(account);
			addToTargetSystem(targetCustomer, targetAccount);
		}
	}



	private TargetAccount createTargetAccount(BankJDTransaction account) {
		
		TargetAccount targetAccount = new TargetAccount(account.getIbannumber(), (float) (account.getAccountstatus()*IntegrationProcessor.euroExchangeRate), TypeOfAccount.TRANSACTION);
		return targetAccount;
	}

	private TargetCustomer createTargetUser(BankJDTransaction account) {
		TargetCustomer targetCustomer = new TargetCustomer(account.getFirstname(), account.getLastname(),account.getAddress(),account.getCountry());
		if(account.getRanking()<=1)
		{
			targetCustomer.setStatus(Status.BRONZE);
		}
		else if (account.getRanking()<=3)
		{
			targetCustomer.setStatus(Status.SILBER);
		}
		else
		{
			targetCustomer.setStatus(Status.GOLD);
		}
		return targetCustomer;
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
				if(id == tc.getCid() && !targetCustomer.getCountrycode().equals(tc.getCountrycode()))
				{
					tc.setCountrycode(targetCustomer.getCountrycode());
				}
					
			}
			
		}
	}


	private int getID(TargetCustomer userProcessed, TargetAccount targetAccount) {
		
		for (TargetCustomer listUser : targetCustomers)
		{
			if(userProcessed.checkEquality(listUser)==3)
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
}
