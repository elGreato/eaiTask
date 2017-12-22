package eaitask;

import java.rmi.RemoteException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.rpc.holders.FloatWrapperHolder;
import javax.xml.rpc.holders.IntegerWrapperHolder;
import javax.xml.rpc.holders.LongWrapperHolder;
import javax.xml.rpc.holders.StringHolder;

import java.sql.Connection;

import ch.fhnw.www.wi.eai.bankjd.BankJD;
import ch.fhnw.www.wi.eai.bankjd.BankJDProxy;
import eaitask.bankjd.BankJDSavings;
import eaitask.bankjd.BankJDSavingsConverter;
import eaitask.bankjd.BankJDTransaction;
import eaitask.bankjd.BankJDTransactionConverter;
import eaitask.bankvct.BankVCTAccount;
import eaitask.bankvct.BankVCTConverter;
import eaitask.targetsystem.Status;
import eaitask.targetsystem.TargetAccount;
import eaitask.targetsystem.TargetCustomer;

public class IntegrationProcessor {
	private String dbUsername = "root";
	private String dbPassword = "root";
	
	private ArrayList<BankJDSavings> jdSavings;
	private ArrayList<BankJDTransaction> jdTransactions;
	private ArrayList<BankVCTAccount> vctAccounts;
	
	private ArrayList<TargetCustomer> targetCustomers;
	private ArrayList<TargetAccount> targetAccounts;
	
	private ArrayList<TargetCustomer> customersForManualCheck;
	
	public static double dollarExchangeRate = 0.9761d;
	public static double euroExchangeRate = 1.1613d; 
	
	public void executeIntegration() {
		
		jdSavings = new ArrayList<BankJDSavings>();
		jdTransactions = new ArrayList<BankJDTransaction>();
		vctAccounts = new ArrayList<BankVCTAccount>();
		
		retrieveBankJDData();
		retrieveBankVCTData();
		
		targetCustomers = new ArrayList<TargetCustomer>();
		targetAccounts = new ArrayList<TargetAccount>();
		customersForManualCheck = new ArrayList<TargetCustomer>();
		
		BankVCTConverter vctConverter = new BankVCTConverter();
		vctConverter.convert(vctAccounts, targetCustomers, targetAccounts);
		BankJDSavingsConverter jdSavingsConverter = new BankJDSavingsConverter();
		jdSavingsConverter.convert(jdSavings, targetCustomers, targetAccounts, 
				jdTransactions.get(1).getBIC(),
				vctAccounts.get(vctAccounts.size()-1).getCustomerID()+1,
				vctAccounts.get(vctAccounts.size()-1).getCustomerID(),
				customersForManualCheck);		
		BankJDTransactionConverter jdTransactionConverter = new BankJDTransactionConverter();
		jdTransactionConverter.convert(jdTransactions,targetCustomers, targetAccounts,
				targetCustomers.get(targetCustomers.size()-1).getCid()+1,
				vctAccounts.get(vctAccounts.size()-1).getCustomerID(),
				customersForManualCheck);
		
		Collections.sort((List<TargetAccount>)targetAccounts);
		
		calculateStatus();
		
		printCustomers();
		printAccounts();
		
		printCustomersToManuallyAssign();
		
		

	}

		
	
	private void printCustomersToManuallyAssign() {
		System.out.println("********************");
		System.out.println("MANUAL CHECK FOR CUSTOMERS (2/3 HITS)");
		System.out.println("********************");
		System.out.println("***********");
		System.out.println();
		int index = 0;
		for(TargetCustomer tu: customersForManualCheck)
		{
			if(index % 2 == 0)
			{ 
				System.out.println("Problem no.: " + (index/2+1));
				System.out.println();
				tu.print();
				System.out.println();
				
			}
			else
			{
				tu.print();
				System.out.println();
				System.out.println("***********");
				System.out.println();
			}
			index++;
			
		}
		System.out.println("********************");
		System.out.println();
		
	}



	private void calculateStatus() {
		for(TargetCustomer tu:targetCustomers)
		{
			int noAccounts = 0;
			int totalBalance = 0;
			for(TargetAccount ta:targetAccounts)
			{
				if(ta.getCid() == tu.getCid())
				{
					noAccounts++;
					totalBalance += ta.getAccountbalance();
				}
				else if(ta.getCid()>tu.getCid())
				{
					break;
				}
				
			}
			if(noAccounts >1 || tu.getStatus() == null)
			{
				if (totalBalance < 10000)
					tu.setStatus(Status.BRONZE);
				else if (totalBalance >= 10000 && totalBalance < 1000000)
					tu.setStatus(Status.SILBER);
				else
					tu.setStatus(Status.GOLD);
			}
		}
		
	}



	private void printAccounts() {
		System.out.println("********************");
		System.out.println("ACCOUNTS TABLE");
		System.out.println("********************");
		System.out.println("***********");
		for(TargetAccount ta: targetAccounts)
		{
			ta.print();
			System.out.println("***********");
		}
		System.out.println("********************");
		System.out.println();
		
	}
	private void printCustomers() {
		System.out.println("********************");
		System.out.println("CUSTOMER TABLE");
		System.out.println("********************");
		System.out.println("***********");
		for(TargetCustomer tu: targetCustomers)
		{
			tu.print();
			System.out.println("***********");
		}
		System.out.println("********************");
		System.out.println();
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
				BankVCTAccount vctacc = new BankVCTAccount(customerID, customerName, streetName, zip, town, country, typeOfCustomer,accountNumber, total, clearing);
				vctAccounts.add(vctacc);
			}
			rs.close();
			cn.close();
			
		} catch (SQLException e) {
			System.out.println("Failed to connect to database");
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
			
		}
		
	}

}
