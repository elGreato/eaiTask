package eaitask.bankvct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;

import ch.sic.ibantool.RecordIban;
import eaitask.IntegrationProcessor;
import eaitask.targetsystem.Status;
import eaitask.targetsystem.TargetAccount;
import eaitask.targetsystem.TargetCustomer;
import eaitask.targetsystem.TypeOfAccount;

public class BankVCTConverter {
	private ArrayList<BankVCTAccount> vctAccounts;
	private ArrayList<TargetCustomer> targetCustomers;
	private ArrayList<TargetAccount> targetAccounts;

	public void convert(ArrayList<BankVCTAccount> vctAccounts, ArrayList<TargetCustomer> targetUsers,
			ArrayList<TargetAccount> targetAccounts) {

		this.vctAccounts = vctAccounts;
		this.targetCustomers = targetUsers;
		this.targetAccounts = targetAccounts;


		for (BankVCTAccount c : vctAccounts) {
			if (!c.getTypeofcustomer().equalsIgnoreCase("firma")) {
				c.setCustomername(removeSymbols(c.getCustomername()));
				TargetCustomer targetCustomer = createTargetCustomer(c);
				TargetAccount targetAccount = createTargetAccount(c);
				addToTargetSystem(targetCustomer, targetAccount);
			}

		}

	}


	private String removeSymbols(String input) {
		return input;
	}


	private TargetCustomer createTargetCustomer(BankVCTAccount c) {

		int id = c.getCustomerID();
		String name = c.getCustomername();
		String[] nameParts = name.split(" ");
		String lastName = nameParts[nameParts.length - 1];
		String firstName = "";
		for (int i = 0; i < nameParts.length - 1; i++) {
			firstName += nameParts[i] + " ";
		}

		String address = c.getStreetname() + ", " + c.getZip() + " " + c.getTown();

		Status status;
		if (c.getTotal() < 10000)
			status = Status.BRONZE;
		else if (c.getTotal() >= 10000 && c.getTotal() < 1000000)
			status = Status.SILBER;
		else
			status = Status.GOLD;
		String countryCode = new String();
		for(String cc : Locale.getISOCountries())
		{
			if((new Locale("",cc)).getDisplayCountry(new Locale("de","CH")).toLowerCase().equals(c.getState().toLowerCase())||
					(new Locale("",cc)).getDisplayCountry(new Locale("en_US","CH")).toLowerCase().equals(c.getState().toLowerCase()))
			{
				countryCode = cc;
			}
		}

		TargetCustomer customer = new TargetCustomer(id, firstName, lastName, address, countryCode, status);

		return customer;
	}

	private void addToTargetSystem(TargetCustomer targetCustomer, TargetAccount targetAccount) {
		targetCustomers.add(targetCustomer);
		targetAccounts.add(targetAccount);

	}

	private TargetAccount createTargetAccount(BankVCTAccount c) {

		int id = c.getCustomerID();

		ch.sic.ibantool.Main ibanClass = new ch.sic.ibantool.Main();
		ch.sic.ibantool.RecordIban iban;
		iban = ibanClass.IBANConvert(new StringBuffer(Long.valueOf(c.getClearing()).toString()),
				new StringBuffer(Long.valueOf(c.getAccountnumber()).toString()));

		float accountBalance = c.getTotal();
		TypeOfAccount type = TypeOfAccount.TRANSACTION;

		TargetAccount ta = new TargetAccount(id, iban.Iban.toString(), (float) (accountBalance*IntegrationProcessor.dollarExchangeRate), type);

		return ta;

	}

}
