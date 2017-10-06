package ch.fhnw.www.wi.eai.bankjd;

public class BankJDProxy implements ch.fhnw.www.wi.eai.bankjd.BankJD {
  private String _endpoint = null;
  private ch.fhnw.www.wi.eai.bankjd.BankJD bankJD = null;
  
  public BankJDProxy() {
    _initBankJDProxy();
  }
  
  public BankJDProxy(String endpoint) {
    _endpoint = endpoint;
    _initBankJDProxy();
  }
  
  private void _initBankJDProxy() {
    try {
      bankJD = (new ch.fhnw.www.wi.eai.bankjd.BankJDServiceLocator()).getBankJDPort();
      if (bankJD != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bankJD)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bankJD)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bankJD != null)
      ((javax.xml.rpc.Stub)bankJD)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ch.fhnw.www.wi.eai.bankjd.BankJD getBankJD() {
    if (bankJD == null)
      _initBankJDProxy();
    return bankJD;
  }
  
  public java.lang.String[] listSavingsLastname() throws java.rmi.RemoteException{
    if (bankJD == null)
      _initBankJDProxy();
    return bankJD.listSavingsLastname();
  }
  
  public void retrieveTransaction(java.lang.String queryFirstname, java.lang.String queryLastname, javax.xml.rpc.holders.StringHolder firstname, javax.xml.rpc.holders.StringHolder lastname, javax.xml.rpc.holders.StringHolder address, javax.xml.rpc.holders.StringHolder country, javax.xml.rpc.holders.IntegerWrapperHolder ranking, javax.xml.rpc.holders.StringHolder ibannumber, javax.xml.rpc.holders.FloatWrapperHolder accountstatus, javax.xml.rpc.holders.StringHolder bic) throws java.rmi.RemoteException{
    if (bankJD == null)
      _initBankJDProxy();
    bankJD.retrieveTransaction(queryFirstname, queryLastname, firstname, lastname, address, country, ranking, ibannumber, accountstatus, bic);
  }
  
  public java.lang.String[] listeTransactionLastname() throws java.rmi.RemoteException{
    if (bankJD == null)
      _initBankJDProxy();
    return bankJD.listeTransactionLastname();
  }
  
  public void printSavings() throws java.rmi.RemoteException{
    if (bankJD == null)
      _initBankJDProxy();
    bankJD.printSavings();
  }
  
  public void retrieveSavings(java.lang.String queryFirstname, java.lang.String queryLastname, javax.xml.rpc.holders.StringHolder fname, javax.xml.rpc.holders.StringHolder lname, javax.xml.rpc.holders.StringHolder street, javax.xml.rpc.holders.StringHolder zipTown, javax.xml.rpc.holders.FloatWrapperHolder interestrate, javax.xml.rpc.holders.LongWrapperHolder accountnumber, javax.xml.rpc.holders.LongWrapperHolder accountstatus) throws java.rmi.RemoteException{
    if (bankJD == null)
      _initBankJDProxy();
    bankJD.retrieveSavings(queryFirstname, queryLastname, fname, lname, street, zipTown, interestrate, accountnumber, accountstatus);
  }
  
  public void printTransaction() throws java.rmi.RemoteException{
    if (bankJD == null)
      _initBankJDProxy();
    bankJD.printTransaction();
  }
  
  
}