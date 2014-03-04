package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class BardTerm extends Model {

	public String label;
	public int elementId;
	public String modifiedLabel;
	public String root;

	public BardTerm(int elementId, String label, String root) {
		this.label = label;
		this.elementId = elementId;
		this.root = root;
	}
}