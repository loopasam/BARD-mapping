package jobs;

import java.util.List;
import java.util.Map;

import models.Annotation;
import models.BardTerm;

import play.Logger;
import play.db.jpa.JPA;
import play.jobs.Job;

public class NaiveAnnotationJob extends Job {

	public void doJob() {
		Logger.info("starting naive annotations...");

		List<BardTerm> terms = BardTerm.findAll();
		int total = terms.size();
		int current = 1;

		for (BardTerm term : terms) {
			System.out.println(current + "/" + total + " - " + term.label);
			current++;
			List<Integer> results = JPA.em().createNativeQuery("SELECT assay_id FROM assays where description ILIKE '%" + term.label + "%';").getResultList();
			System.out.println("#results: " + results.size());
			int counter = 0;
			for (Integer assay_id : results) {
				counter++;
				if (counter%50==0) {
					Annotation.em().flush(); 
					Annotation.em().clear();
				}
				new Annotation(assay_id, term).save();
			}
		}
	}

}
