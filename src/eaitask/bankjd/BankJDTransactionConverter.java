package eaitask.bankjd;

import java.util.ArrayList;

import eaitask.targetsystem.TargetAccount;
import eaitask.targetsystem.TargetUser;

public class BankJDTransactionConverter {
	ArrayList<BankJDTransaction> jdTransactions;
	ArrayList<TargetUser> targetUsers;
	ArrayList<TargetAccount> targetAccounts;
	
	public void convert(
			ArrayList<BankJDTransaction> jdTransactions,
			ArrayList<TargetUser> targetUsers,
			ArrayList<TargetAccount> targetAccounts) {
		this.jdTransactions = jdTransactions;
		this.targetUsers = targetUsers;
		this.targetAccounts = targetAccounts;
	}
}
