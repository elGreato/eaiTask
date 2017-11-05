package eaitask.bankvct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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

		readData();

		for (BankVCTAccount c : vctAccounts) {
			if (c.getTypeofcustomer().equalsIgnoreCase("firma")) {
				TargetCustomer targetCustomer = createTargetCustomer(c);
				TargetAccount targetAccount = createTargetAccount(c);
				addToTargetSystem(targetCustomer, targetAccount);
			}

		}

	}

	// Read VCT accounts -- can be removed later
	private void readData() {

		String csv = "/BankVCT.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csv));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] result = line.split(cvsSplitBy);
				BankVCTAccount account = new BankVCTAccount(Integer.parseInt(result[0]), result[1], result[2], result[3], 
						result[4], result[5], result[6], Long.parseLong(result[7]), Float.parseFloat(result[8]), Long.parseLong(result[9]));
				
				vctAccounts.add(account);
				

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
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

		// get the tool six class ... we need IBAN here for country code
		ch.sic.ibantool.Main ibanClass = new ch.sic.ibantool.Main();
		ch.sic.ibantool.RecordIban iban;

		iban = ibanClass.IBANConvert(new StringBuffer(Long.valueOf(c.getClearing()).toString()),
				new StringBuffer(Long.valueOf(c.getAccountnumber()).toString()));

		Status status;
		if (c.getTotal() < 10000)
			status = Status.BRONZE;
		else if (c.getTotal() >= 10000 && c.getTotal() < 1000000)
			status = Status.SILBER;
		else
			status = Status.GOLD;

		String countryCode = iban.toString().substring(0, 2);

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

		TargetAccount ta = new TargetAccount(id, iban.toString(), (float) (accountBalance*IntegrationProcessor.dollarExchangeRate), type);

		return ta;

	}

}
