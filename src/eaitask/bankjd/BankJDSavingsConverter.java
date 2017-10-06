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
	}
}
