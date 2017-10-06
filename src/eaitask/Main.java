package eaitask;

import java.rmi.RemoteException;

import ch.fhnw.www.wi.eai.bankjd.BankJD;
import ch.fhnw.www.wi.eai.bankjd.BankJDProxy;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BankJD bankJDData = new BankJDProxy();
		try {
			bankJDData.printSavings();
			bankJDData.printTransaction();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
