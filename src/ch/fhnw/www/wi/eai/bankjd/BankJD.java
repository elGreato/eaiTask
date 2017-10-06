/**
 * BankJD.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ch.fhnw.www.wi.eai.bankjd;

public interface BankJD extends java.rmi.Remote {
    public java.lang.String[] listSavingsLastname() throws java.rmi.RemoteException;
    public void retrieveTransaction(java.lang.String queryFirstname, java.lang.String queryLastname, javax.xml.rpc.holders.StringHolder firstname, javax.xml.rpc.holders.StringHolder lastname, javax.xml.rpc.holders.StringHolder address, javax.xml.rpc.holders.StringHolder country, javax.xml.rpc.holders.IntegerWrapperHolder ranking, javax.xml.rpc.holders.StringHolder ibannumber, javax.xml.rpc.holders.FloatWrapperHolder accountstatus, javax.xml.rpc.holders.StringHolder bic) throws java.rmi.RemoteException;
    public java.lang.String[] listeTransactionLastname() throws java.rmi.RemoteException;
    public void printSavings() throws java.rmi.RemoteException;
    public void retrieveSavings(java.lang.String queryFirstname, java.lang.String queryLastname, javax.xml.rpc.holders.StringHolder fname, javax.xml.rpc.holders.StringHolder lname, javax.xml.rpc.holders.StringHolder street, javax.xml.rpc.holders.StringHolder zipTown, javax.xml.rpc.holders.FloatWrapperHolder interestrate, javax.xml.rpc.holders.LongWrapperHolder accountnumber, javax.xml.rpc.holders.LongWrapperHolder accountstatus) throws java.rmi.RemoteException;
    public void printTransaction() throws java.rmi.RemoteException;
}
