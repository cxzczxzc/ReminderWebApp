package ca.sheridancollege.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries({
	@NamedQuery(name="Tasks.byId", query="from Tasks where id=:id"),
    @NamedQuery(name="Tasks.name", query="from Tasks WHERE name = :name")
}) 

public class Tasks implements Serializable{
	@Id
	@GeneratedValue
	private int id;
	private String phoneNumber;
	private String name;
	private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd,HH:mm a")
	private String  dueDate;
	private String priority;
	private String[] priorities = new String[] { "Low", "Medium", "High", "Very High"};
	
	public Tasks(String phoneNumber, String name, String description, String dueDate, String priority) {
		super();
	   this.phoneNumber = phoneNumber;
	   this.name = name;
	   this.description = description;
	   this.dueDate = dueDate;
	   this.priority = priority;
	}
	
}