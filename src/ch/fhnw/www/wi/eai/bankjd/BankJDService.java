/**
 * BankJDService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ch.fhnw.www.wi.eai.bankjd;

public interface BankJDService extends javax.xml.rpc.Service {
    public java.lang.String getBankJDPortAddress();

    public ch.fhnw.www.wi.eai.bankjd.BankJD getBankJDPort() throws javax.xml.rpc.ServiceException;

    public ch.fhnw.www.wi.eai.bankjd.BankJD getBankJDPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
