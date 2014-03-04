package jobs;

import java.util.List;

import models.Annotation;
import models.BardTerm;
import play.Logger;
import play.db.jpa.JPA;
import play.jobs.Job;

public class NaiveModifiedAnnotationJob extends Job {

	public void doJob() {
		Logger.info("starting naive annotations with modified labels...");

		List<BardTerm> terms = BardTerm.findAll();
		int total = terms.size();
		int current = 1;

		for (BardTerm term : terms) {
			System.out.println(current + "/" + total + " - " + term.modifiedLabel);
			current++;
			List<Integer> results = JPA.em().createNativeQuery("SELECT assay_id FROM assays where description ILIKE '%" + term.modifiedLabel + "%';").getResultList();
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
		Logger.info("Job done.");
	}
}
