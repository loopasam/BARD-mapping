package jobs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.BardTerm;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.Logger;
import play.jobs.Job;
import play.libs.WS;
import play.libs.WS.HttpResponse;

public class UpdateBardDictionaryJob extends Job {

	String root;
	Set<String> statuses = new HashSet<String>();

	public void doJob() {
		Logger.info("Updating the dico...");
		Logger.info("Updating terms related to ASSAY TYPE...");
		root = "ASSAY TYPE";
		update(8);
		Logger.info("Updating terms related to DETECTION METHOD...");
		root = "DETECTION METHOD";
		update(15);
		Logger.info("Dictionary updated.");
		
		for (String status : statuses) {
			System.out.println("Status: " + status);
		}
	}

	private void update(int i) {
		HttpResponse res = WS.url("http://bard.nih.gov/api/v17.3/cap/dictionary/" + i).get();
		JsonObject json = res.getJson().getAsJsonObject();
		int elementId = json.get("elementId").getAsInt();
		String label = json.get("label").getAsString();
		String status = json.get("elementStatus").getAsString();
		boolean synoms = json.get("synonyms").isJsonNull();
		//TODO - check synonyms
		if(synoms == false){
			System.out.println("---syno: " + synoms);
		}else{
			System.out.println("**no syno: " + synoms);
		}
		statuses.add(status);
		Logger.info(elementId + " - " + label);

		BardTerm term = BardTerm.find("byElementId", elementId).first();
		if(term != null){
			if(!term.label.equals(label)){
				Logger.info("Label changed for BARD term " + term.elementId + ". From '" + term.label + "' into '" + label + "'.");
				term.label = label;
				term.save();
			}else{
				Logger.info("Term " + term.elementId + " exists already.");
			}
			if(status.equals("Retired")){
				term.delete();
			}
		}else{
			if(!status.equals("Retired")){
				new BardTerm(elementId, label, root).save();
			}
		}

		HttpResponse childrenRes = WS.url("http://bard.nih.gov/api/v17.3/cap/dictionary/" + i + "/children").get();
		JsonArray jsonArray = childrenRes.getJson().getAsJsonArray();
		for (JsonElement jsonElement : jsonArray) {
			int childId = jsonElement.getAsJsonObject().get("elementId").getAsInt();
			update(childId);
		}
	}

}
