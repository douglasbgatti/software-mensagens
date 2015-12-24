package br.com.celtab.openims.entity;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.Gson;

import br.com.celtab.openims.dao.GenericDAOImpl;

@Entity
@Table(name="groups")
public class Groups implements Serializable{

	@Id
	@SequenceGenerator(name="groups_seq", sequenceName="groups_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,	generator="groups_seq")
	private Long id;
	@Column(unique=true)
	private String title;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "group_of_groups", 
		joinColumns = { @JoinColumn(name = "group_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "id") })
	private List<Groups> nodes = new ArrayList<Groups>();
	
	
	public Groups(){}
	
	public Groups(String title) {
		super();
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Groups> getNodes() {
		return nodes;
	}

	public void setNodes(List<Groups> nodes) {
		this.nodes = nodes;
	}

	public void addNodes(Groups nodes) {
		this.nodes.add(nodes);
	}

	@Override
	public String toString() {
		return "Groups [id=" + id + ", title=" + title + ", nodes=" + nodes + "]";
	}

	
public static void main(String[] args) throws JAXBException{
		GenericDAOImpl<Groups, Long> groupsDAO = new GenericDAOImpl<Groups, Long>(Groups.class);
		groupsDAO.startEntityManager();
		
		Groups group1= new Groups("Group1");
		Groups group2= new Groups("Group2");
		Groups group3= new Groups("Group_3");
		Groups group4= new Groups("Group_4");
		Groups group5= new Groups("Group_5");
		Groups group6= new Groups("Group_6");
		Groups group7= new Groups("Group_7");
		Groups group8= new Groups("Group_8");
		
		group1.addNodes(group2);
		group1.addNodes(group3);
		group3.addNodes(group4);
		group5.addNodes(group6);
		group5.addNodes(group7);
		group5.addNodes(group8);
		group4.addNodes(group5);
		
		
		groupsDAO.save(group1);
		groupsDAO.save(group2);
		groupsDAO.save(group3);
		groupsDAO.save(group4);
		groupsDAO.save(group5);
		groupsDAO.save(group6);
		groupsDAO.save(group7);
		groupsDAO.save(group8);
		
		
	
		groupsDAO.commitTransaction();
	
		
//		List<GroupOfGroups> list = gogDAO.findAll();
//		for (GroupOfGroups groupOfGroups : list) {
//			System.out.println(groupOfGroups);
//		}

		
		
	}



	
}
