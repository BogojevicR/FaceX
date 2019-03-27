package app.gui;

import javax.swing.AbstractListModel;

import app.helper.OfflineDatabase;
import app.model.PersonOfflineModel;

class ListData extends AbstractListModel {
	
	  String[] strings = new String[OfflineDatabase.persons.size()];

	  public ListData() {

	    for(int i = 0; i < OfflineDatabase.persons.size(); i++) {
	    	strings[i] = OfflineDatabase.persons.get(i).getFirst_name() + " " + OfflineDatabase.persons.get(i).getLast_name() ;
	    }
	  }

	  public int getSize() {
	    return strings.length;
	  }

	  public Object getElementAt(int index) {
	    return strings[index];
	  }
}