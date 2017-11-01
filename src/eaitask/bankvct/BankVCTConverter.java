package eaitask.bankvct;

import java.util.ArrayList;

import eaitask.targetsystem.TargetAccount;
import eaitask.targetsystem.TargetCustomer;

public class BankVCTConverter {
	private ArrayList<BankVCTAccount> vctAccounts;
	private ArrayList<TargetCustomer> targetUsers;
	private ArrayList<TargetAccount> targetAccounts;
	public void convert(ArrayList<BankVCTAccount> vctAccounts, ArrayList<TargetCustomer> targetUsers, ArrayList<TargetAccount> targetAccounts)
	{
		this.vctAccounts = vctAccounts;
		this.targetUsers = targetUsers;
		this.targetAccounts = targetAccounts;
	}
}
