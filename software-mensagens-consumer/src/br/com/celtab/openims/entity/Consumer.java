package br.com.celtab.openims.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="consumer")
public class Consumer {
	@Id
	@SequenceGenerator(name="consumer_seq", sequenceName="consumer_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,	generator="consumer_seq")
	private Long id;
	@Column(unique=true)	
	private String username;
	@ManyToMany( cascade=CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Groups> groups = new ArrayList<Groups>();


	public Consumer(){}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Groups> getGroups() {
		return groups;
	}
	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}


	@Override
	public String toString() {
		return "Consumer [id=" + id + ", username=" + username + ", groups=" + groups + "]";
	}
	
	
	
	

}
