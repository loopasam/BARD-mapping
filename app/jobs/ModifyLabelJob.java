package jobs;

import java.util.List;

import models.BardTerm;
import play.Logger;
import play.jobs.Job;

public class ModifyLabelJob extends Job {
	
	public void doJob() {
		Logger.info("Starting job...");
		List<BardTerm> assayTerms = BardTerm.find("byRoot", "ASSAY TYPE").fetch();
		for (BardTerm bardTerm : assayTerms) {
			bardTerm.modifiedLabel = bardTerm.label.replace(" assay", "");
			bardTerm.save();
		}
		
		List<BardTerm> methodTerms = BardTerm.find("byRoot", "DETECTION METHOD").fetch();
		for (BardTerm bardTerm : methodTerms) {
			bardTerm.modifiedLabel = bardTerm.label.replace(" method", "");
			bardTerm.save();
		}

		Logger.info("Job done.");
	}

}
