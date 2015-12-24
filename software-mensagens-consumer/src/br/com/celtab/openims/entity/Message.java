package br.com.celtab.openims.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name="message_producer")
@NamedQueries({
    @NamedQuery(name="latestMessages",
                query="SELECT m FROM Message m ORDER BY m.id DESC")
//    ,
//    @NamedQuery(name="Country.findByName",
//                query="SELECT c FROM Country c WHERE c.name = :name"),
}) 
public class Message implements Serializable{
	
	@Id
	@SequenceGenerator(name="message_producer_seq", sequenceName="message_producer_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,	generator="message_producer_seq")
	private Long id;
	@Column
	private String type;
	@Column
	private String title;
	@Column
	private String message;
//	@Column
//	private Long sentBy;
	@ManyToMany( cascade=CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Groups> destination = new ArrayList<Groups>();
	
	
	
	public Message() {
		super();
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Groups> getDestination() {
		return destination;
	}
	public void setDestination(List<Groups> destination) {
		this.destination = destination;
	}

	
	
	@Override
	public String toString() {
		return "Message [id=" + id + ", type=" + type + ", title=" + title + ", message=" + message + ", destination="
				+ destination + "]";
	}
	
	

}
