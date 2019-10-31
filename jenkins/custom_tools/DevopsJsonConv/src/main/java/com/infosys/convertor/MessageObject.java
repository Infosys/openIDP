package com.infosys.convertor;

import java.util.ArrayList;

public class MessageObject {
	
	private ArrayList<String> messagesforPunit = new ArrayList<>();
	
	public MessageObject() {
		this.messagesforPunit = new ArrayList<>();
	}
	
	public MessageObject(ArrayList<String> messagesforPunit) {
		this.messagesforPunit = messagesforPunit;
	}

	public ArrayList<String> getMessagesforPunit() {
		return messagesforPunit;
	}

	public void setMessagesforPunit(ArrayList<String> messagesforPunit) {
		this.messagesforPunit = messagesforPunit;
	}
	
	

}
