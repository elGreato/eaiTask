package eaitask.bankjd;

import java.util.ArrayList;

import eaitask.targetsystem.TargetAccount;
import eaitask.targetsystem.TargetUser;

public class BankJDSavingsConverter {
	ArrayList<BankJDSavings> jdSavings;
	ArrayList<TargetUser> targetUsers;
	ArrayList<TargetAccount> targetAccounts;
	
	public void convert(
			ArrayList<BankJDSavings> jdSavings,
			ArrayList<TargetUser> targetUsers,
			ArrayList<TargetAccount> targetAccounts) {
		this.jdSavings = jdSavings;
		this.targetUsers = targetUsers;
		this.targetAccounts = targetAccounts;
		
		for(BankJDSavings account: jdSavings)
		{
			TargetUser targetUser = createTargetUser(account);
			TargetAccount targetAccount = createTargetAccount(account);
			addToTargetSystem(targetUser, targetAccount);
		}
	}

	private void addToTargetSystem(TargetUser targetUser,
			TargetAccount targetAccount) {
		boolean userExists = false;
		getID(targetUser, targetAccount, userExists);
		
		targetAccounts.add(targetAccount);
		if(!userExists)
		{
			targetUsers.add(targetUser);
		}
	}

	private void getID(TargetUser targetUser, TargetAccount targetAccount,
			boolean userExists) {
		// TODO Auto-generated method stub
		
	}

	private TargetAccount createTargetAccount(BankJDSavings account) {
		// TODO Auto-generated method stub
		return null;
	}

	private TargetUser createTargetUser(BankJDSavings account) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
