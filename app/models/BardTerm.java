package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class BardTerm extends Model {
	
	String label;
	int elementId;
	String modifiedLabel;
}