/*
 *  (c) Copyright 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 *  (c) Copyright 2003, Plugged In Software 
 *
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.

 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
   $Id: ARP.java,v 1.1 2009/06/29 08:55:46 castagna Exp $
   AUTHOR:  Jeremy J. Carroll
   with modification from PI Software
*/
/*
 * ARP.java
 *
 * Created on June 22, 2001, 6:20 AM
    *
    *  *
    *Possible options
    *
    * embedded RDF
    *
    *
    *
 */

package com.hp.hpl.jena.rdf.arp;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.rdf.arp.impl.RDFXMLParser;


/** Another RDF Parser.
 * To load an RDF file:
 * <ol>
 * <li>Create an ARP.</li>
 * <li>Set its handlers, by calling the {@link #getHandlers}
 * method, and then.</li>
 * <ul>
 * <li>Setting the statement handler.</li>
 * <li>Optionally setting the other handlers.</li>
 * </ul>
 * 
 * </li> 
 * <li>Call a load method.</li>
 * </ol>
 * <p>
 * Xerces is used for parsing the XML.
 * The SAXEvents generated by Xerces are then
 * analysed as RDF by ARP.
 * Errors may occur
 * in either the XML or the RDF part, see 
 * {@link ARPHandlers#setErrorHandler} for details
 * of how to distinguish between them.</p>
 * <p>
 * For very large files, ARP does not use any additional
 * memory except when either the {@link ExtendedHandler#discardNodesWithNodeID}
 * returns false or when the {@link AResource#setUserData} method has been
 * used. In these cases ARP needs to remember the <code>rdf:nodeID</code>
 * usage through the file life time. </p>
 * <p>See <a href="../../../../../../../ARP/index.html">ARP documentation</a> for more information.</p>
 * @author  Jeremy Carroll with contributions from Simon Raboczi
 *   and Andrew Newman
 */
public class ARP implements ARPConfig 
{

    final private RDFXMLParser arpf;
    
/** Creates a new RDF Parser.
 * Can parse one file at a time.
 */    
    public ARP()  {
        arpf =  RDFXMLParser.create();
    }
	/**
 * When parsing a file, this returns a Locator giving the
 * position of the last XML event processed by ARP.
 * This may return null or misleading results before any
 * tokens have been processed.
 * @return Locator
 */
    public Locator getLocator() {
        return arpf.getLocator();
    }
    
/** Load RDF/XML from a Reader.
 * @param in The input XML document.
 * @param xmlBase The base URI for the document.
 * @throws SAXException More serious error during XML or RDF processing; or thrown from the ErrorHandler.
 * @throws IOException Occurring during XML processing.
 */    
    public void load(Reader in,String xmlBase) throws SAXException, IOException {
        InputSource inputS = new InputSource(in);
        inputS.setSystemId(xmlBase);
//        arpf.initParse(xmlBase,"");
         arpf.parse(inputS);
    }
    void load(InputSource is)  throws SAXException, IOException {

//        arpf.initParse("","");
        arpf.parse(is);
    }
/** Load RDF/XML from an InputStream.
 * @param in The input XML document.
 * @param xmlBase The base URI for the document.
 * @throws SAXException More serious error during XML or RDF processing; or thrown from the ErrorHandler.
 * @throws IOException Occurring during XML processing.
 */    
    public void load(InputStream in,String xmlBase) throws SAXException, 
IOException {
        //load(new InputStreamReader(in),xmlBase);
        InputSource inputS = new InputSource(in);
        inputS.setSystemId(xmlBase);
//        arpf.initParse(xmlBase,"");
        arpf.parse(inputS, xmlBase);
    }
/** Load RDF/XML from an InputStream,  leaving relative URIs as relative.
 * @param in The input XML document.
 * @throws SAXException More serious error during XML or RDF processing; or thrown from the ErrorHandler.
 * @throws IOException Occurring during XML processing.
 */   
    public void load(InputStream in) 
     throws SAXException, IOException {
        load(in,"");
    }
/** Load RDF/XML from a Reader, leaving relative URIs as relative.
 * @param in The input XML document.
 * @throws SAXException More serious error during XML or RDF processing; or thrown from the ErrorHandler.
 * @throws IOException Occurring during XML processing.
 */    
    public void load(Reader in) throws SAXException, IOException {
        load(in,"");
    }
    /**
     * The handlers used during parsing.
     * The handlers can be changed by calling this method
     * and then using the <code>set..Handler</code> methods
     * in {@link ARPHandlers}.
     * The handlers can be copied onto another ARP instance
     * using the {@link #setHandlersWith} method.
     * @see ARPHandlers#setStatementHandler(StatementHandler)
     * @see ARPHandlers#setErrorHandler(ErrorHandler)
     * @see ARPHandlers#setExtendedHandler(ExtendedHandler)
     * @see ARPHandlers#setNamespaceHandler(NamespaceHandler)
     * @see #setHandlersWith
     * @return The handlers used during parsing.
     */
    public ARPHandlers getHandlers() {
    	return arpf.getHandlers();
    }
    /**
     * Copies the handlers from the argument
     * to be used by this instance.
     * To make further modifications it is necessary
     * to call {@link #getHandlers} to retrieve this
     * instance's copy of the handler information.
     * @param handlers The new values to use.
     */
    public void setHandlersWith(ARPHandlers handlers){
    	arpf.setHandlersWith(handlers);
    }
    /**
     * The options used during parsing.
     * The options can be changed by calling this method
     * and then using the <code>set..</code> methods
     * in {@link ARPOptions}.
     * The options can be copied onto another ARP instance
     * using the {@link #setOptionsWith} method.
     * @see ARPOptions#setDefaultErrorMode()
     * @see ARPOptions#setLaxErrorMode()
     * @see ARPOptions#setStrictErrorMode()
     * @see ARPOptions#setStrictErrorMode(int)
     * @see ARPOptions#setEmbedding(boolean)
     * @see ARPOptions#setErrorMode(int, int)
     * 
     * @see #setOptionsWith
     * @return The handlers used during parsing.
     */
    
    public ARPOptions getOptions(){
    	return arpf.getOptions();
    }

    /**
     * Copies the options from the argument
     * to be used by this instance.
     * To make further modifications it is necessary
     * to call {@link #getOptions} to retrieve this
     * instance's copy of the options.
     * @param opts The new values to use.
     */
    public void setOptionsWith(ARPOptions opts){
    	arpf.setOptionsWith(opts);
    }

    void setBadStatementHandler(StatementHandler sh) {
        arpf.setBadStatementHandler(sh);
        
    }
}
