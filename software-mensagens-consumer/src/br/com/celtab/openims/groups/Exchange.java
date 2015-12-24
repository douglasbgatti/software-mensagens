package br.com.celtab.openims.groups;
import java.util.ArrayList;
import java.util.List;

public class Exchange{
	private String name;
	private String type; //type: topic, queue, fanout,...
	private List<Exchange> subExchangeGroups;
	
	

	@Override
	public String toString() {
		return "Exchange [name=" + name + ", type=" + type
				+ ", subExchangeGroups Size=" + subExchangeGroups.size() + "]";
	}

	public Exchange() {
		this.name = new String();
		this.type = new String();
		this.subExchangeGroups = null;
	}

	public Exchange(String name, String type, List<Exchange> subExchangeGroups) {
		this.name = name;
		this.type = type;
		this.subExchangeGroups = subExchangeGroups;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Exchange> getSubExchangeGroups() {
		return subExchangeGroups;
	}
	public void setSubExchangeGroups(List<Exchange> subExchangeGroups) {
		this.subExchangeGroups = subExchangeGroups;
	}
	
	public List<Exchange> getDummyExchange(){
		List<Exchange> listExchanges = new ArrayList<Exchange>();
		List<Exchange> subListExchanges = new ArrayList<Exchange>();
		List<Exchange> subSubListExchanges = new ArrayList<Exchange>();
		List<Exchange> subSubListExchanges2 = new ArrayList<Exchange>();
		Exchange exchangeGroups = new Exchange();

		//subSubListExchanges.add(new Exchange("subsubGroup1", "topic", new ArrayList<Exchange>()));
		//subSubListExchanges.add(new Exchange("subsubGroup2", "topic", new ArrayList<Exchange>()));
		
//		subSubListExchanges2.add(new Exchange("subsubGroup3", "topic", null));
//		subSubListExchanges2.add(new Exchange("subsubGroup4", "topic", null));
		
		
		//subListExchanges.add(new Exchange("subGroup1", "topic", subSubListExchanges));
		//subListExchanges.add(new Exchange("subGroup2", "topic", subSubListExchanges2));
		//subListExchanges.add(new Exchange("subGroup3", "topic", null));
		
		exchangeGroups.setName("Group1");
		exchangeGroups.setType("topic");
		exchangeGroups.setSubExchangeGroups(new ArrayList<Exchange>());
		//exchangeGroups.setSubExchangeGroups(subListExchanges);
		
		listExchanges.add(exchangeGroups);	
		
		return listExchanges;		
	}
	
	

	
	
}
