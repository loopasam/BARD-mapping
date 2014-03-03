package jobs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.Logger;
import play.jobs.Job;
import play.libs.WS;
import play.libs.WS.HttpResponse;

public class UpdateBardDictionaryJob extends Job {

	public void doJob() {
		Logger.info("Updating the dico...");
		Logger.info("Updating terms related to ASSAY TYPE...");
		update(8);
		Logger.info("Updating terms related to DETECTION METHOD...");
		update(15);
	}

	private void update(int i) {
		HttpResponse res = WS.url("http://bard.nih.gov/api/v17.3/cap/dictionary/" + i).get();
		JsonObject json = res.getJson().getAsJsonObject();
		int elementId = json.get("elementId").getAsInt();
		String label = json.get("label").getAsString();
		Logger.info(label + " - " + elementId);
		
		//Checks the DB if an entry with the same elementId exists already
			//If yes, check if the label is the same
				//if no, then update and log the update
			//If no, create and save a new entry
		
		//Get all the children and call update for each element in the list of children
		//Get the list of children (http://bard.nih.gov/api/v17.3/cap/dictionary/8/children), extract the children IDs
	}

}
