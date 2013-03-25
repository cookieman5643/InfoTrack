package com.jmt.infotrack;

import java.util.ArrayList;
import java.util.List;

public class DataObj {

	/*
	 * IMPORTANT NOTE: You do not want to replace an empty data value with
	 * nothing when you are updating already existing data objects. This is fine
	 * for this object handling class, but in DBAdapter, make sure to only set
	 * the value if the object is not null and has nothing in it. SEE
	 * "setData()" FOR MORE INFO.
	 */

	// Fields of a DataObj
	public String dateCreated, dateModified, dateViewed;
	public String category, subcategory; // deprecated
	public String path, container, type; // Necessary initial variables
	public String data1, data2, data3, data4, data5;
	public String extra1, extra2;

	public DataObj() {
		setAllDates("dC", "dM", "dV");
		setAllDesignations("pa", "contain", "typ");
		setData("d1", "d2", "d3", "d4", "d5");
		setExtras("e1", "e2");
		
		setAllCategories("cat", "subcat");
	}// end Constructor

	/*
	 * ###########################################################
	 * #################### DATES ################################
	 * ###########################################################
	 */
	public void setDate(String modified, String viewed) {
		dateModified = modified;
		dateViewed = viewed;
	}// end setDate

	public void setAllDates(String created, String modified, String viewed) {
		dateCreated = created;
		setDate(modified, viewed);
	}// end setAllDates

	public String getDCreated() {
		return dateCreated;
	}// end getDCcreated

	public String getDModified() {
		return dateModified;
	}// end getDCcreated

	public String getDViewed() {
		return dateViewed;
	}// end getDCcreated

	/*
	 * ############################################################
	 * #################### DESIGNATION ###########################
	 * ############################################################
	 */
	public void setAllDesignations(String pa, String contain, String typ) {
		path = pa;
		container = contain;
		type = typ;
	}// end setAllDesignations

	public void setPath(String pa) {
		path = pa;
	}// end setPath()

	public void setContainer(String contain) {
		container = contain;
	}// end setContainer()

	public void setType(String typ) {
		type = typ;
	}// end setType()

	public String getPath() {
		return path;
	}// end getPath()

	public String getContainer() {
		return container;
	}// end getContainer()

	public String getType() {
		return type;
	}// end getType()

	/*
	 * ############################################################
	 * #################### DATA ##################################
	 * ############################################################
	 */

	public void setData(String d1, String d2, String d3, String d4, String d5) {
		data1 = d1;
		data2 = d2;
		data3 = d3;
		data4 = d4;
		data5 = d5;
		/*
		 * IMPORTANT NOTE: it is possible that you only want to update e.g. d3
		 * with other data values set to null. This should NOT update the
		 * database with null values for other data points.
		 */
	}// end setData

	public ArrayList<String> getData() {
		ArrayList<String> a = new ArrayList<String>(5);
		a.add(data1);
		a.add(data2);
		a.add(data3);
		a.add(data4);
		a.add(data5);
		return a;
	}// end getData()

	// Assuming asking for data 1-5, return position in array of 0-4
	public String getDataAt(int v) {
		ArrayList<String> beep = getData();
		return beep.get(v - 1);
	}// end getDataAt()

	/*
	 * ############################################################
	 * ########################### Extras #########################
	 * ############################################################
	 */
	public void setExtras(String e1, String e2) {
		extra1 = e1;
		extra2 = e2;

	}// end setData

	public ArrayList<String> getExtras() {
		ArrayList<String> a = new ArrayList<String>(5);
		a.add(extra1);
		a.add(extra2);
		return a;
	}// end getData()

	// Assuming asking for data 1-2, return position in array of 0-1
	public String getExtrasAt(int v) {
		ArrayList<String> beep = getExtras();
		return beep.get(v - 1);
	}// end getDataAt()

	// ##########################DEPRECATED####################

	/*
	 * ####### Category #########
	 */

	public void setCategory(String cat) {
		category = cat;
	}// end setCategory()

	public void setSubCategory(String subcat) {
		subcategory = subcat;
	}// end setSubCategory()

	public void setAllCategories(String cat, String subcat) {
		setCategory(cat);
		setSubCategory(subcat);
	}// end setAllCategoryes

	public String getCategory() {
		return category;
	}// end getCategory()

	public String getSubCategory() {
		return subcategory;
	}// end getSubCategory()

}// end Class
