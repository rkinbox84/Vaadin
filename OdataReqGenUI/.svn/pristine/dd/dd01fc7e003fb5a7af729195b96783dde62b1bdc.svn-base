package com.sap.sf.odata.meta;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Parses the metadata information retrieved from odata api into metadata
 * objects
 * 
 * @author ganeshkumar.venugopalan@sap.com
 */
public class MetadataParser {

	private Element datasetElement;
	private Element dataElement;
	private Element nameSpace;

	Logger logger = null;
	
	public MetadataParser() {
		logger = Logger.getLogger("MetaDataParser");
	}

	public List<EntityMetadata> getMetadata(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(xmlFile);
		return getMetadata(doc);
	}

	public List<EntityMetadata> getMetatdata(String xmlString)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
		return getMetadata(doc);
	}

	public EcMetadata getMetadataAsSingleObject(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(xmlFile);
		return getMetadataAsSingleObject(doc);
	}

	public EcMetadata getMetadataAsSingleObject(String xmlString)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
		return getMetadataAsSingleObject(doc);
	}

	private EcMetadata getMetadataAsSingleObject(Document doc) throws XPathExpressionException {
		EcMetadata ecMetadata = new EcMetadata();

		ecMetadata.setLstEntityMetadata(getMetadata(doc));

		return ecMetadata;
	}

	private List<EntityMetadata> getMetadata(Document doc) throws XPathExpressionException {
		Element edmxNode = doc.getDocumentElement();
		Node dataServicesNode = edmxNode.getElementsByTagName("edmx:DataServices").item(0);

		NodeList listSchemas = ((Element) dataServicesNode).getElementsByTagName("Schema");

		datasetElement = null;
		dataElement = null;

		for (int i = 0; i < listSchemas.getLength(); i++) {
			Node node = listSchemas.item(i);
			Element e = (Element) node;

			String nameSpaceName = e.getAttribute("Namespace");

			if ("SFODataSet".equals(nameSpaceName)) {
				datasetElement = e;
			} else if ("SFOData".equals(nameSpaceName)) {
				dataElement = e;
			}
		}

		List<EntityMetadata> lstMetadata = getMetadata(datasetElement, doc);

		// updateMetadataEntityType(doc, lstMetadata);

		return lstMetadata;
	}

	private void updateMetadataEntityType(Document doc, List<EntityMetadata> lstMetadata)
			throws XPathExpressionException {
		for (int i = 0; i < lstMetadata.size(); i++) {
			EntityMetadata metaData = lstMetadata.get(i);
			String metadataName = metaData.getEntitySetname();
			String metadataTypeName = metaData.getEntityTypeName();

			EntityType et = getEntityType(doc, metadataName);
			// Add entity type to metadata
			metaData.setEntityType(et);

			// BAODataLog.info(i+")Metadata "+metaData.getEntityTypeName()+"
			// processed");
		}

	}

	private EntityType getEntityType(Document doc, String metadataName) throws XPathExpressionException {
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		XPathExpression expr = xpath.compile("//EntityType[@Name=\"" + metadataName + "\"]");
		Element entityTypeElement = (Element) expr.evaluate(doc, XPathConstants.NODE);
		
		Map<String,Object> keys = new HashMap<String,Object>();
		

		String etName = entityTypeElement.getAttribute("Name");

		EntityType et = new EntityType();
		et.setEntityTypeName(etName);
		et.setEntityNamespace("SFOData"); // TODO: Need to revisit whether we
											// need to have namespace

		// Get the Keys
		Element keyElement = (Element) entityTypeElement.getElementsByTagName("Key").item(0);
		NodeList propertyReferences = keyElement.getElementsByTagName("PropertyRef");
		
	

		for (int ctr = 0; ctr < propertyReferences.getLength(); ctr++) {
			et.getKeys().add(((Element) propertyReferences.item(ctr)).getAttribute("Name"));
			
			Element PropertyRef = (Element)propertyReferences.item(ctr);
			int attribsize = PropertyRef.getAttributes().getLength();

			for (int i = 0; i < attribsize; i++) {
				Node element = PropertyRef.getAttributes().item(i);
				et.allAttributes.put(element.getNodeName(), element.getNodeValue());
				}
			}

		// Get Properties and add it to entitytype
		NodeList nlProperties = entityTypeElement.getElementsByTagName("Property");
		for (int ctr = 0; ctr < nlProperties.getLength(); ctr++) {
			getEntityAttribute(doc, et, nlProperties, ctr, entityTypeElement);			
			
		}

		// Get Nav Properties and add it ot entityType
		NodeList navProperties = entityTypeElement.getElementsByTagName("NavigationProperty");
		int nlPropLength = navProperties.getLength();
		for (int ctr = 0; ctr < navProperties.getLength(); ctr++) {
			getNavigationalAttribute(doc, et, navProperties, ctr, entityTypeElement);
		}

		for (int ctr = 0; ctr < et.getAttributeList().size(); ctr++) {
			EntityAttribute eaSelected = et.getAttributeList().get(ctr);
			Collections.sort(et.getNavigationAttributeList());
			int index = Collections.binarySearch(et.getNavigationAttributeList(),
					new EntityNavigationAttribute(eaSelected.getPropertyName() + "Nav", null, null, null, null, null));
			if (index >= 0) {
				EntityNavigationAttribute ena = et.getNavigationAttributeList().get(index);
				eaSelected.setNavigationlAttribute(ena);
			}
		}

		return et;
	}

	private void getNavigationalAttribute(Document doc, EntityType et, NodeList navProperties, int ctr,
			Element entityTypeElement) {
		Element navAttributeElement = (Element) navProperties.item(ctr);

		if (navAttributeElement == null) {
			return;
		}

		String name = navAttributeElement.getAttribute("Name");
		String relationship = navAttributeElement.getAttribute("Relationship");
		String fromRole = navAttributeElement.getAttribute("FromRole");
		String toRole = navAttributeElement.getAttribute("ToRole");
		String fieldControl = navAttributeElement.getAttribute("sap:field-control");
		String label = navAttributeElement.getAttribute("sap:label");
		int maxLength = Integer.parseInt((navAttributeElement.getAttribute("MaxLength") != null
				&& navAttributeElement.getAttribute("MaxLength").trim().length() > 0)
						? navAttributeElement.getAttribute("MaxLength") : "-1");
		boolean required = Boolean.parseBoolean(navAttributeElement.getAttribute("sap:required"));
		boolean insertable = Boolean.parseBoolean(navAttributeElement.getAttribute("sap:creatable"));
		boolean updatable = Boolean.parseBoolean(navAttributeElement.getAttribute("sap:updatable"));
		boolean upsertable = Boolean.parseBoolean(navAttributeElement.getAttribute("sap:upsertable"));
		boolean selectable = Boolean.parseBoolean(navAttributeElement.getAttribute("sap:visible"));
		boolean sortable = Boolean.parseBoolean(navAttributeElement.getAttribute("sap:sortable"));
		boolean filterable = Boolean.parseBoolean(navAttributeElement.getAttribute("sap:filterable"));
		boolean visible = Boolean.parseBoolean(navAttributeElement.getAttribute("sap:visible"));
		String pickListId = navAttributeElement.getAttribute("sap:picklist");
		String fromRoleEntitytype = null;
		String toRoleEntitytype = null;

		if (fromRole != null && toRole != null) {
			// Get the associationset name from the
			// relationship.[SFOData.cust_toLegalEntity_of_FOBusinessUnit ==>
			// cust_toLegalEntity_of_FOBusinessUnit]
			String associationsetName = relationship.substring(relationship.indexOf(".") + 1, relationship.length());

			// Find the Associationset from First Schema
			Element entityContainerElement = (Element) datasetElement.getElementsByTagName("EntityContainer").item(0);

			try {
				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();
				XPathExpression expr;
				expr = xpath.compile("//AssociationSet[@Name=\"" + associationsetName + "\"]");
				Element associationsetElement = (Element) expr.evaluate(doc, XPathConstants.NODE);

				NodeList nl = associationsetElement.getElementsByTagName("End");
				for (int i = 0; i < nl.getLength(); i++) {
					Node nd = nl.item(i);
					String entytySet = nd.getAttributes().getNamedItem("EntitySet").getNodeValue();
					String role = nd.getAttributes().getNamedItem("Role").getNodeValue();
					if (role.equals(fromRole)) {
						fromRoleEntitytype = entytySet;
					} else if (role.equals(toRole)) {
						toRoleEntitytype = entytySet;
					}
				}

			} catch (XPathExpressionException e) {
			} catch (Exception e) {
			}

		}
		
		Map<String,Object> allAttributes = new HashMap<String,Object>();
		Element property = (Element)navProperties.item(ctr);
		int attribsize = property.getAttributes().getLength();

		for (int i = 0; i < attribsize; i++) {
			Node element = property.getAttributes().item(i);
			allAttributes.put(element.getNodeName(), element.getNodeValue());
			}
		

		EntityNavigationAttribute ena = new EntityNavigationAttribute(name, relationship, fromRole, toRole,
				fieldControl, label, required, insertable, updatable, selectable, upsertable, sortable, filterable,
				visible, maxLength);
		
		ena.setAllAttributes(allAttributes);
		
		if (pickListId != null)
			ena.setPickListId(pickListId);

		if (fromRoleEntitytype != null)
			ena.setFromRoleEntityset(fromRoleEntitytype);

		if (toRoleEntitytype != null)
			ena.setToRoleEntityset(toRoleEntitytype);
		
		et.addEntityNavigationAttribute(ena);

	}

	private void getEntityAttribute(Document doc, EntityType et, NodeList nlProperties, int ctr,
			Element entityTypeElement) throws XPathExpressionException {
		
		Map<String,Object> allAttributes = new HashMap<String,Object>();
		Element propertyElement = (Element) nlProperties.item(ctr);
		if (propertyElement == null)
			return;

		String name = propertyElement.getAttribute("Name");
		String type = propertyElement.getAttribute("Type");
		boolean nullable = Boolean.parseBoolean(propertyElement.getAttribute("maxLength"));
		int maxLength = Integer.parseInt((propertyElement.getAttribute("MaxLength") != null
				&& propertyElement.getAttribute("MaxLength").trim().length() > 0)
						? propertyElement.getAttribute("MaxLength") : "-1");
		boolean required = Boolean.parseBoolean(propertyElement.getAttribute("sap:required"));
		boolean insertable = Boolean.parseBoolean(propertyElement.getAttribute("sap:creatable"));
		boolean updatable = Boolean.parseBoolean(propertyElement.getAttribute("sap:updatable"));
		boolean upsertable = Boolean.parseBoolean(propertyElement.getAttribute("sap:upsertable"));
		boolean selectable = Boolean.parseBoolean(propertyElement.getAttribute("sap:visible"));
		boolean sortable = Boolean.parseBoolean(propertyElement.getAttribute("sap:sortable"));
		boolean filterable = Boolean.parseBoolean(propertyElement.getAttribute("sap:filterable"));
		String pickListId = propertyElement.getAttribute("sap:picklist");
		String label = propertyElement.getAttribute("sap:label");
		boolean visible = Boolean.parseBoolean(propertyElement.getAttribute("sap:visible"));

		String etName = entityTypeElement.getAttribute("Name");

		Element property = (Element)nlProperties.item(ctr);
		int attribsize = property.getAttributes().getLength();

		for (int i = 0; i < attribsize; i++) {
			Node element = property.getAttributes().item(i);
			allAttributes.put(element.getNodeName(), element.getNodeValue());
			}
		
		
		EntityAttribute ea = new EntityAttribute(name, type, label, maxLength, nullable, required, insertable,
				updatable, selectable, upsertable, sortable, filterable, pickListId, visible);
		
		ea.setAllAttributes(allAttributes);
		et.addEntityAttribute(ea);

	}

	private List<EntityMetadata> getMetadata(Element datasetElement, Document doc) throws XPathExpressionException {
		List<EntityMetadata> lstMetadata = new ArrayList<EntityMetadata>();
		Element entityContainerElement = (Element) datasetElement.getElementsByTagName("EntityContainer").item(0); // getFirstChild();

		NodeList entitySetList = entityContainerElement.getElementsByTagName("EntitySet"); // getChildNodes();

		logger.info("###Total EntitySet Elemets:"+  entitySetList.getLength());
		
		for (int i = 0; i < entitySetList.getLength(); i++) {
			Element entitySetItem = (Element) entitySetList.item(i);
			// if (!entitySetItem.getTagName().equals("EntitySet")) continue;
			EntityMetadata metaData = prepareEntityMetadataFromXmlElement(entitySetItem);
			metaData.setEntityType(getEntityType(doc, metaData.getEntitySetname()));

			lstMetadata.add(metaData);
		}

		return lstMetadata;
	}

	private EntityMetadata prepareEntityMetadataFromXmlElement(Element entitySetItem) {
		String name = entitySetItem.getAttribute("Name");
		String type = entitySetItem.getAttribute("EntityType");
		String label = entitySetItem.getAttribute("sap:label");

		EntityMetadata metaData = new EntityMetadata(name, type, label);
		NodeList documentationNodeList = entitySetItem.getElementsByTagName("Documentation");

		if (documentationNodeList.getLength() > 0) {
			Element documentElement = (Element) documentationNodeList.item(0); // Assuming
																				// there's
																				// only
																				// one
																				// documentation
																				// node
			String summary = documentElement.getElementsByTagName("Summary").getLength() > 0
					? documentElement.getElementsByTagName("Summary").item(0).getTextContent() : null;
			String longDescription = documentElement.getElementsByTagName("LongDescription").getLength() > 0
					? documentElement.getElementsByTagName("LongDescription").item(0).getTextContent() : null;
			NodeList nltagCollections = ((Element) documentElement.getElementsByTagName("sap:tagcollection").item(0))
					.getElementsByTagName("sap:tag");

			EntityDocumentation documentation = new EntityDocumentation(summary, longDescription);
			for (int i = 0; i < nltagCollections.getLength(); i++) {
				documentation.addTagCollection(nltagCollections.item(i).getTextContent());
			}
			metaData.setDocumentation(documentation);
		}

		return metaData;
	}

	/**
	 * Method will return List of FunctionImport objects
	 * 
	 * @param xmlString
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public List<FunctionImport> getFunctionImportElementList(String xmlString)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

		List<FunctionImport> fiList = new ArrayList<FunctionImport>();

		// get the SFODataSet elements

		Element sfoDataSetElements = getNameSpaceElements(doc,"SFODataSet");

		// getFunctionImport elements
		NodeList fiNodeList = getFunctionImportElements(sfoDataSetElements);

		// For each FunctionImport Item prepare FunctionImport objects
		for (int i = 0; i < fiNodeList.getLength(); i++) {
			Element entitySetItem = (Element) fiNodeList.item(i);
			// Create FunctionImport Object
			FunctionImport fiData = prepareFunctionImportMetaFromXmlElement(entitySetItem);
			// Add all FunctionImport Parameters to the FunctionImport
			addFIParameterData(doc, fiData);
			// Add final object to the List
			fiList.add(fiData);

		}

		// Return the List of FunctionImport in the given Metadata xml
		return fiList;

	}

	/**
	 * Method to add all FunctionImport parameters to the FunctionImport Object
	 * 
	 * @param doc
	 * @param fiData
	 * @throws XPathExpressionException
	 */
	private void addFIParameterData(Document doc, FunctionImport fiData) throws XPathExpressionException {
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		XPathExpression expr = xpath.compile("//FunctionImport[@Name=\"" + fiData.getFunctionImportName() + "\"]");
		Element entityTypeElement = (Element) expr.evaluate(doc, XPathConstants.NODE);

		// Get the parameters and add to the Function Import Object
		NodeList nlProperties = entityTypeElement.getElementsByTagName("Parameter");
		for (int ctr = 0; ctr < nlProperties.getLength(); ctr++) {
			FunctionImportParameter fip = getFIParameter(doc, nlProperties, ctr);
			fiData.addfunctionImportParameters(fip);
		}

	}

	/**
	 * Method will return the FunctionImportParameter object for the given count
	 * 
	 * @param doc
	 * @param nlProperties
	 * @param ctr
	 * @return
	 * @throws XPathExpressionException
	 */
	private FunctionImportParameter getFIParameter(Document doc, NodeList nlProperties, int ctr)
			throws XPathExpressionException {
		String[] definedAttributes = { "Name", "Type" };
		Map<String, Object> allAttributes = new HashMap<String, Object>();

		Element propertyElement = (Element) nlProperties.item(ctr);
		if (propertyElement == null)
			return null;

		String name = propertyElement.getAttribute(definedAttributes[0]);
		String type = propertyElement.getAttribute(definedAttributes[1]);

		// Code to find out the new/additional attribute List
		int attribsize = propertyElement.getAttributes().getLength();

		for (int i = 0; i < attribsize; i++) {
			Node element = propertyElement.getAttributes().item(i);
			if (Arrays.asList(definedAttributes).contains(element.getNodeName())) {
				allAttributes.put(element.getNodeName(), element.getNodeValue());
			}
		}

		// finding the new/additional attribute List ends here

		FunctionImportParameter fip = new FunctionImportParameter(name, type, allAttributes);
		return fip;

	}

	private FunctionImport prepareFunctionImportMetaFromXmlElement(Element entitySetItem) {

		String[] definedAttributes = { "Name", "ReturnType", "EntitySet", "m:HttpMethod" };

		Map<String, Object> allElements = new HashMap<String, Object>();

		String functionImportName = entitySetItem.getAttribute(definedAttributes[0]);
		String functionImportReturnType = entitySetItem.getAttribute(definedAttributes[1]);
		String functionImportEntitySet = entitySetItem.getAttribute(definedAttributes[2]);
		String functionImportHttpMethod = entitySetItem.getAttribute(definedAttributes[3]);

		// Code to find out the new/additional attribute List
		int attribsize = entitySetItem.getAttributes().getLength();

		for (int i = 0; i < attribsize; i++) {
			Node element = entitySetItem.getAttributes().item(i);
			if (Arrays.asList(definedAttributes).contains(element.getNodeName())) {
				allElements.put(element.getNodeName(), element.getNodeValue());
			}
		}

		// finding the new/additional attribute List ends here

		FunctionImport functionImportData = new FunctionImport(functionImportName, functionImportReturnType,
				functionImportEntitySet, functionImportHttpMethod, allElements);

		return functionImportData;
	}

	public Element getNameSpaceElements(Document doc, String nsElementName) {

		Element edmxNode = doc.getDocumentElement();
		Node dataServicesNode = edmxNode.getElementsByTagName("edmx:DataServices").item(0);

		NodeList listSchemas = ((Element) dataServicesNode).getElementsByTagName("Schema");


		nameSpace=null;
		
		

		for (int i = 0; i < listSchemas.getLength(); i++) {
			Node node = listSchemas.item(i);
			Element e = (Element) node;

			String nameSpaceName = e.getAttribute("Namespace");

			if (nsElementName.equals(nameSpaceName)) {
				nameSpace = e;
			} else if (nsElementName.equals(nameSpaceName)) {
				nameSpace = e;
			}
		}
		
		return nameSpace;

	}

	public NodeList getFunctionImportElements(Element datasetElement) {
		List<EntityMetadata> lstMetadata = new ArrayList<EntityMetadata>();
		Element entityContainerElement = (Element) datasetElement.getElementsByTagName("EntityContainer").item(0); // getFirstChild();

		NodeList entitySetList = entityContainerElement.getElementsByTagName("FunctionImport"); // getChildNodes();

		return entitySetList;

	}
	
	//Code to get Association Elements

	public List<Association> getAssociationElementList(String xmlString)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		logger.info("Association DB started...");
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

		logger.info("Association DB Completed...");
		
		List<Association> associationList = new ArrayList<Association>();

		// get the SFODataSet elements

		Element sfoDataElements = getNameSpaceElements(doc,"SFOData");

		// getFunctionImport elements
		NodeList associationNodeList = getAssociationElements(sfoDataElements);

		logger.info("Total Association Elements:"+ associationNodeList.getLength());
		
		logger.info("Started Preparing Association Element List..");
		// For each FunctionImport Item prepare FunctionImport objects
		for (int i = 0; i < associationNodeList.getLength(); i++) {
			Element associationItem = (Element) associationNodeList.item(i);
			// Create FunctionImport Object
			Association associationData = prepareAssociationMetaFromXmlElement(associationItem);
			// Add all FunctionImport Parameters to the FunctionImport
			addAssociationTypes(doc, associationData);
			// Add final object to the List
			associationList.add(associationData);

		}

		logger.info("Completed Preparing Association Element List..");
		// Return the List of FunctionImport in the given Metadata xml
		return associationList;

	}
	
	
	private void addAssociationTypes(Document doc, Association associationData) throws XPathExpressionException {
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		XPathExpression expr = xpath.compile("//Association[@Name=\"" + associationData.getAssociationName() + "\"]");
		Element entityTypeElement = (Element) expr.evaluate(doc, XPathConstants.NODE);

		// Get the parameters and add to the Function Import Object
		NodeList nlProperties = entityTypeElement.getElementsByTagName("End");
		for (int ctr = 0; ctr < nlProperties.getLength(); ctr++) {
			AssociationType at = getEndTypes(doc, nlProperties, ctr);
			associationData.addassociationType(at);
			
		}

	}
	
	private AssociationType getEndTypes(Document doc, NodeList nlProperties, int ctr)
			throws XPathExpressionException {
		String[] definedAttributes = { "Type", "Multiplicity","Role" };
		Map<String, Object> allAttributes = new HashMap<String, Object>();

		Element propertyElement = (Element) nlProperties.item(ctr);
		if (propertyElement == null)
			return null;

		String type = propertyElement.getAttribute(definedAttributes[0]);
		String multi = propertyElement.getAttribute(definedAttributes[1]);
		String role = propertyElement.getAttribute(definedAttributes[2]);

		// Code to find out the new/additional attribute List
		int attribsize = propertyElement.getAttributes().getLength();

		for (int i = 0; i < attribsize; i++) {
			Node element = propertyElement.getAttributes().item(i);
			if (Arrays.asList(definedAttributes).contains(element.getNodeName())) {
				allAttributes.put(element.getNodeName(), element.getNodeValue());
			}
		}

		// finding the new/additional attribute List ends here

		AssociationType at = new AssociationType(type, multi,role, allAttributes);
		return at;

	}
	
	public NodeList getAssociationElements(Element datasetElement) {
		List<EntityMetadata> lstMetadata = new ArrayList<EntityMetadata>();
		NodeList associationList = datasetElement.getElementsByTagName("Association"); 
		return associationList;

	}
	
	private Association prepareAssociationMetaFromXmlElement(Element entitySetItem) {



		Map<String, Object> allElements = new HashMap<String, Object>();

		String associationName = entitySetItem.getAttribute("Name");


		// Code to find out the new/additional attribute List
		int attribsize = entitySetItem.getAttributes().getLength();

		for (int i = 0; i < attribsize; i++) {
			Node element = entitySetItem.getAttributes().item(i);
				allElements.put(element.getNodeName(), element.getNodeValue());

		}

		Association association = new Association(associationName,allElements);

		return association;
	}
	
	// Code for Getting ComplexType Elements
	
	public List<ComplexType> getComplexTypeElementList(String xmlString)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

		List<ComplexType> complexTypeList = new ArrayList<ComplexType>();

		// get the SFODataSet elements

		Element sfoDataElements = getNameSpaceElements(doc,"SFOData");

		// getFunctionImport elements
		NodeList complexTypeNodeList = getComplexTypeElements(sfoDataElements);

		// For each FunctionImport Item prepare FunctionImport objects
		for (int i = 0; i < complexTypeNodeList.getLength(); i++) {
			Element complexTypeElement = (Element) complexTypeNodeList.item(i);
			// Create FunctionImport Object
			ComplexType ct = preparecomplexTypeObjFromXmlElement(complexTypeElement);
			// Add all FunctionImport Parameters to the FunctionImport
			addComplexTypeProperties(doc, ct);
			// Add final object to the List
			complexTypeList.add(ct);

		}

		// Return the List of FunctionImport in the given Metadata xml
		return complexTypeList;

	}
	
	
	private void addComplexTypeProperties(Document doc, ComplexType ct) throws XPathExpressionException {
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		XPathExpression expr = xpath.compile("//ComplexType[@Name=\"" + ct.getComplexTypeName() + "\"]");
		Element complexTypeElement = (Element) expr.evaluate(doc, XPathConstants.NODE);

		// Get the parameters and add to the Function Import Object
		NodeList nlProperties = complexTypeElement.getElementsByTagName("Property");
		for (int ctr = 0; ctr < nlProperties.getLength(); ctr++) {
			EntityAttribute ea = getCTProperties(doc, nlProperties, ctr);
			ct.addCTProperty(ea);
			
		}

	}
	
	private EntityAttribute getCTProperties(Document doc, NodeList nlProperties, int ctr)
			throws XPathExpressionException {
		Map<String,Object> allAttributes = new HashMap<String,Object>();
		Element propertyElement = (Element) nlProperties.item(ctr);
		if (propertyElement == null)
			return null;

		String name = propertyElement.getAttribute("Name");
		String type = propertyElement.getAttribute("Type");
		boolean nullable = Boolean.parseBoolean(propertyElement.getAttribute("Nullable"));
		boolean required = Boolean.parseBoolean(propertyElement.getAttribute("sap:required"));
		boolean creatable = Boolean.parseBoolean(propertyElement.getAttribute("sap:creatable"));
		boolean updatable = Boolean.parseBoolean(propertyElement.getAttribute("sap:updatable"));
		boolean upsertable = Boolean.parseBoolean(propertyElement.getAttribute("sap:upsertable"));
		boolean visible = Boolean.parseBoolean(propertyElement.getAttribute("sap:visible"));
		boolean sortable = Boolean.parseBoolean(propertyElement.getAttribute("sap:sortable"));
		boolean filterable = Boolean.parseBoolean(propertyElement.getAttribute("sap:filterable"));
		String label = propertyElement.getAttribute("sap:label");


		Element property = (Element)nlProperties.item(ctr);
		int attribsize = property.getAttributes().getLength();

		for (int i = 0; i < attribsize; i++) {
			Node element = property.getAttributes().item(i);
			allAttributes.put(element.getNodeName(), element.getNodeValue());
			}
		
		
		EntityAttribute ea = new EntityAttribute(name,type, nullable, required, creatable, updatable, upsertable,
				visible, sortable,filterable, label);
		
		ea.setAllAttributes(allAttributes);

		return ea;


	}
	
	public NodeList getComplexTypeElements(Element datasetElement) {
		List<EntityMetadata> lstMetadata = new ArrayList<EntityMetadata>();
		NodeList associationList = datasetElement.getElementsByTagName("ComplexType"); 
		return associationList;

	}
	
	private ComplexType preparecomplexTypeObjFromXmlElement(Element entitySetItem) {



		Map<String, Object> allElements = new HashMap<String, Object>();

		String complexTypeName = entitySetItem.getAttribute("Name");


		// Code to find out the new/additional attribute List
		int attribsize = entitySetItem.getAttributes().getLength();

		for (int i = 0; i < attribsize; i++) {
			Node element = entitySetItem.getAttributes().item(i);
				allElements.put(element.getNodeName(), element.getNodeValue());

		}

		ComplexType ct = new ComplexType(complexTypeName,allElements);

		return ct;
	}
	
	//Code to get EntitySet Elements
	
	public List<EntitySet> getEntitySetElementList(String xmlString)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

		List<EntitySet> esList = new ArrayList<EntitySet>();

		// get the SFODataSet elements

		Element sfoDataElements = getNameSpaceElements(doc,"SFODataSet");

		// getFunctionImport elements
		NodeList entitySetNodeList = getEntitySetElements(sfoDataElements);

		// For each FunctionImport Item prepare FunctionImport objects
		for (int i = 0; i < entitySetNodeList.getLength(); i++) {
			Element entitySetElement = (Element) entitySetNodeList.item(i);
			// Create FunctionImport Object
			EntitySet eSet = prepareEntitySetObjFromXmlElement(entitySetElement);
			esList.add(eSet);
		}

		// Return the List of FunctionImport in the given Metadata xml
		return esList;

	}
	
	
	public NodeList getEntitySetElements(Element datasetElement) {
		List<EntityMetadata> lstMetadata = new ArrayList<EntityMetadata>();
		Element entityContainerElement = (Element) datasetElement.getElementsByTagName("EntityContainer").item(0); // getFirstChild();

		NodeList entitySetList = entityContainerElement.getElementsByTagName("EntitySet"); // getChildNodes();

		return entitySetList;

	}
	
	private EntitySet prepareEntitySetObjFromXmlElement(Element entitySetItem) {



		Map<String, Object> allElements = new HashMap<String, Object>();

		String entitySetName = entitySetItem.getAttribute("Name");


		// Code to find out the new/additional attribute List
		int attribsize = entitySetItem.getAttributes().getLength();

		for (int i = 0; i < attribsize; i++) {
			Node element = entitySetItem.getAttributes().item(i);
				allElements.put(element.getNodeName(), element.getNodeValue());

		}

		EntitySet esObj = new EntitySet(entitySetName,allElements);

		return esObj;
	}
}
