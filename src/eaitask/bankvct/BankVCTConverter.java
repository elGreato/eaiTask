package eaitask.bankvct;


import java.util.ArrayList;
import java.util.Locale;

import ch.sic.ibantool.RecordIban;
import eaitask.IntegrationProcessor;
import eaitask.Utils;
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
				c.setStreetname(Utils.trim(c.getStreetname()));
				c.setCustomername(Utils.trim(c.getCustomername()));
				c.setState(Utils.trim(c.getState()));
				c.setTown(Utils.trim(c.getTown()));
				c.setZip(Utils.trim(c.getZip()));
				TargetCustomer targetCustomer = createTargetCustomer(c);
				TargetAccount targetAccount = createTargetAccount(c);
				addToTargetSystem(targetCustomer, targetAccount);
			}

		}

	}


	private TargetCustomer createTargetCustomer(BankVCTAccount c) {

		int id = c.getCustomerID();
		String name = c.getCustomername();
		String[] nameParts = name.split(" ");
		String lastName = new String();
		String firstName = new String();
		for (int i = 0; i < nameParts.length; i++) {
			if(nameParts[i].equals("van")||nameParts[i].equals("von")||nameParts[i].equals("de")|| i+1 == nameParts.length)
			{
				for(int j = i;j< nameParts.length;j++)
				{
					lastName += nameParts[j] + " ";
				}
				break;
			}
			else if(!nameParts[i].equals("Dr.")&&!nameParts[i].equals("Prof."))
			{
				firstName += nameParts[i] + " ";
			}
		}
		firstName= Utils.trim(firstName);
		lastName = Utils.trim(lastName);
		String address = c.getStreetname() + ", " + c.getZip() + " " + c.getTown();

		String countryCode = new String();
		while (countryCode.equals("")||countryCode == null)
		{
			for(String cc : Locale.getISOCountries())
			{
				if((new Locale("",cc)).getDisplayCountry(new Locale("de","CH")).toLowerCase().equals(c.getState().toLowerCase())||
						(new Locale("",cc)).getDisplayCountry(new Locale("en_US","CH")).toLowerCase().equals(c.getState().toLowerCase()))
				{
					countryCode = cc;
					break;
				}
			}
			if((countryCode == null||countryCode.equals("")) && c.getState().startsWith("The "))
			{
				c.setState(c.getState().substring(c.getState().indexOf(" ")+1));
			}
			else
			{
				break;
			}
		}

		TargetCustomer customer = new TargetCustomer(firstName, lastName, address, countryCode);
		customer.setCid(id);

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

		TargetAccount ta = new TargetAccount(id, iban.Iban.toString(), (float) (Math.round(accountBalance*IntegrationProcessor.dollarExchangeRate*100))/100, type);

		return ta;

	}

}
