package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import play.db.jpa.JPA;
import play.db.jpa.Model;

//An annotation is defined as an assay associated with a BARD term.
@Entity
public class Annotation extends Model {

	public Annotation(Integer assay_id, BardTerm term) {
		this.term = term;
		this.assay_id = assay_id;
	}

	@OneToOne
	public BardTerm term;
	public int assay_id;
}
