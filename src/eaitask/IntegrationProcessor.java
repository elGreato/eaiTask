package eaitask;

import java.rmi.RemoteException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.rpc.holders.FloatWrapperHolder;
import javax.xml.rpc.holders.IntegerWrapperHolder;
import javax.xml.rpc.holders.LongWrapperHolder;
import javax.xml.rpc.holders.StringHolder;

import java.sql.Connection;

import ch.fhnw.www.wi.eai.bankjd.BankJD;
import ch.fhnw.www.wi.eai.bankjd.BankJDProxy;
import eaitask.bankjd.BankJDSavings;
import eaitask.bankjd.BankJDTransaction;
import eaitask.bankvct.BankVCTAccount;

public class IntegrationProcessor {
	private String dbUsername = "root";
	private String dbPassword = "root";
	
	private ArrayList<BankJDSavings> jdSavings;
	private ArrayList<BankJDTransaction> jdTransactions;
	private ArrayList<BankVCTAccount> vctAccounts;
	
	public void executeIntegration() {
		
		jdSavings = new ArrayList<BankJDSavings>();
		jdTransactions = new ArrayList<BankJDTransaction>();
		vctAccounts = new ArrayList<BankVCTAccount>();

		retrieveBankJDData();
		retrieveBankVCTData();
		
	}
	private void retrieveBankVCTData() {
		try {
			Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankvct", dbUsername, dbPassword);
			Statement stmt = cn.createStatement();
			ResultSet rs = stmt.executeQuery("Select CustomerID, CustomerName, StreetName, ZIP,Town, Country, TypeOfCustomer, AccountNumber, Total, Clearing from account");
			while(rs.next())
			{
				int customerID = rs.getInt("CustomerID");
				String customerName = rs.getString("CustomerName");
				String streetName = rs.getString("StreetName"); 
				String zip = rs.getString("ZIP");
				String town = rs.getString("Town"); 
				String country = rs.getString("Country"); 
				String typeOfCustomer = rs.getString("TypeOfCustomer"); 
				long accountNumber = rs.getLong("AccountNumber"); 
				float total = rs.getFloat("Total"); 
				long clearing = rs.getLong("Clearing");
				System.out.println(accountNumber);
				BankVCTAccount vctacc = new BankVCTAccount(customerID, customerName, streetName, zip, town, country, typeOfCustomer,accountNumber, total, clearing);
				vctAccounts.add(vctacc);
			}
			rs.close();
			cn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void retrieveBankJDData() {
		BankJD bankJDSource = new BankJDProxy();
		try
		{
			retrieveTransactions(bankJDSource);
			retrieveSavings(bankJDSource);
		} 
		catch(RemoteException e)
		{
			System.out.println("Could not retrieve webservice data.");
		}

		
		
	}

	private void retrieveTransactions(BankJD bankJDSource) throws RemoteException {
		String[] namesTransaction = bankJDSource.listeTransactionLastname();
		for(String name: namesTransaction)
		{
			StringHolder firstname = new StringHolder();
			StringHolder lastname = new StringHolder();
			StringHolder address = new StringHolder();
			StringHolder country = new StringHolder();
			IntegerWrapperHolder ranking = new IntegerWrapperHolder();
			StringHolder ibannumber = new StringHolder();
			FloatWrapperHolder accountstatus = new FloatWrapperHolder();
			StringHolder bic = new StringHolder();
			
			bankJDSource.retrieveTransaction("", name, firstname, lastname, address, country, ranking, ibannumber, accountstatus, bic);
			
			BankJDTransaction jdTrans = new BankJDTransaction(firstname.value, lastname.value, address.value, country.value, 
					ranking.value, ibannumber.value, accountstatus.value, bic.value);
			jdTransactions.add(jdTrans);
			System.out.println("Done: " + lastname.value);
			
		}
		
	}
	
	private void retrieveSavings(BankJD bankJDSource) throws RemoteException {
		String[] namesSavings = bankJDSource.listSavingsLastname();
		for(String name: namesSavings)
		{
			StringHolder firstname = new StringHolder();
			StringHolder lastname = new StringHolder();
			StringHolder street = new StringHolder();
			StringHolder zipandtown = new StringHolder();
			FloatWrapperHolder interestrate = new FloatWrapperHolder();
			LongWrapperHolder accountnumber = new LongWrapperHolder();
			LongWrapperHolder accountstatus = new LongWrapperHolder();
			
			bankJDSource.retrieveSavings("", name, firstname, lastname, street, zipandtown, interestrate, accountnumber, accountstatus);
			
			BankJDSavings jdTrans = new BankJDSavings(firstname.value, lastname.value, street.value, zipandtown.value, 
					interestrate.value, accountnumber.value, accountstatus.value);
			jdSavings.add(jdTrans);
			System.out.println("Done: " + lastname.value);
			
		}
		
	}

}
