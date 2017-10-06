package eaitask;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.xml.rpc.holders.FloatWrapperHolder;
import javax.xml.rpc.holders.IntegerWrapperHolder;
import javax.xml.rpc.holders.LongWrapperHolder;
import javax.xml.rpc.holders.StringHolder;

import ch.fhnw.www.wi.eai.bankjd.BankJD;
import ch.fhnw.www.wi.eai.bankjd.BankJDProxy;
import eaitask.bankjd.BankJDSavings;
import eaitask.bankjd.BankJDTransaction;
import eaitask.bankvct.BankVCTAccount;

public class IntegrationProcessor {
	private ArrayList<BankJDSavings> jdSavings;
	private ArrayList<BankJDTransaction> jdTransactions;
	private ArrayList<BankVCTAccount> vctAccount;
	
	public void executeIntegration() {
		
		jdSavings = new ArrayList<BankJDSavings>();
		jdTransactions = new ArrayList<BankJDTransaction>();
		vctAccount = new ArrayList<BankVCTAccount>();

		retrieveBankJDData();
		retrieveBankVCTData();
		
	}
	private void retrieveBankVCTData() {
		// TODO Auto-generated method stub
		
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
			
			StringHolder bic = new StringHolder();
			
			bankJDSource.retrieveSavings("", name, firstname, lastname, street, zipandtown, interestrate, accountnumber, accountstatus);
			
			BankJDSavings jdTrans = new BankJDSavings(firstname.value, lastname.value, street.value, zipandtown.value, 
					interestrate.value, accountnumber.value, accountstatus.value);
			jdSavings.add(jdTrans);
			System.out.println("Done: " + lastname.value);
			
		}
		
	}

}
