/**
 * BankJDServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ch.fhnw.www.wi.eai.bankjd;

public class BankJDServiceLocator extends org.apache.axis.client.Service implements ch.fhnw.www.wi.eai.bankjd.BankJDService {

    public BankJDServiceLocator() {
    }


    public BankJDServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BankJDServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BankJDPort
    private java.lang.String BankJDPort_address = "http://localhost:8080/";

    public java.lang.String getBankJDPortAddress() {
        return BankJDPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BankJDPortWSDDServiceName = "BankJDPort";

    public java.lang.String getBankJDPortWSDDServiceName() {
        return BankJDPortWSDDServiceName;
    }

    public void setBankJDPortWSDDServiceName(java.lang.String name) {
        BankJDPortWSDDServiceName = name;
    }

    public ch.fhnw.www.wi.eai.bankjd.BankJD getBankJDPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BankJDPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBankJDPort(endpoint);
    }

    public ch.fhnw.www.wi.eai.bankjd.BankJD getBankJDPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ch.fhnw.www.wi.eai.bankjd.BankJDPortBindingStub _stub = new ch.fhnw.www.wi.eai.bankjd.BankJDPortBindingStub(portAddress, this);
            _stub.setPortName(getBankJDPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBankJDPortEndpointAddress(java.lang.String address) {
        BankJDPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ch.fhnw.www.wi.eai.bankjd.BankJD.class.isAssignableFrom(serviceEndpointInterface)) {
                ch.fhnw.www.wi.eai.bankjd.BankJDPortBindingStub _stub = new ch.fhnw.www.wi.eai.bankjd.BankJDPortBindingStub(new java.net.URL(BankJDPort_address), this);
                _stub.setPortName(getBankJDPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BankJDPort".equals(inputPortName)) {
            return getBankJDPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.fhnw.ch/wi/eai/bankjd/", "BankJDService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.fhnw.ch/wi/eai/bankjd/", "BankJDPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BankJDPort".equals(portName)) {
            setBankJDPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
