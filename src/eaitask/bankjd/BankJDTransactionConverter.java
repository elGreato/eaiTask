package eaitask.bankjd;

import java.util.ArrayList;

import eaitask.IntegrationProcessor;
import eaitask.targetsystem.TargetAccount;
import eaitask.targetsystem.TargetCustomer;
import eaitask.targetsystem.TypeOfAccount;

public class BankJDTransactionConverter {
	private ArrayList<BankJDTransaction> jdTransactions;
	private ArrayList<TargetCustomer> targetCustomers;
	private ArrayList<TargetAccount> targetAccounts;
	private int nextID;
	public void convert(
			ArrayList<BankJDTransaction> jdTransactions,
			ArrayList<TargetCustomer> targetCustomers,
			ArrayList<TargetAccount> targetAccounts,
			int nextID) {
		this.jdTransactions = jdTransactions;
		this.targetCustomers = targetCustomers;
		this.targetAccounts = targetAccounts;
		this.nextID = nextID;
		for(BankJDTransaction account: jdTransactions)
		{
			TargetCustomer targetCustomer = createTargetUser(account);
			TargetAccount targetAccount = createTargetAccount(account);
			addToTargetSystem(targetCustomer, targetAccount);
		}
	}



	private TargetAccount createTargetAccount(BankJDTransaction account) {
		
		TargetAccount targetAccount = new TargetAccount(account.getIbannumber(), (float) (account.getAccountstatus()*IntegrationProcessor.dollarExchangeRate), TypeOfAccount.TRANSACTION);
		return targetAccount;
	}

	private TargetCustomer createTargetUser(BankJDTransaction account) {
		TargetCustomer targetCustomer = new TargetCustomer(account.getFirstname(), account.getLastname(),account.getAddress(),account.getCountry());
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
}
