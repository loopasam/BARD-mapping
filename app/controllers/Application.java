package controllers;

import play.*;
import play.db.jpa.JPA;
import play.mvc.*;
import play.test.Fixtures;

import java.util.*;

import jobs.ModifyLabelJob;
import jobs.NaiveAnnotationJob;
import jobs.NaiveModifiedAnnotationJob;
import jobs.UpdateBardDictionaryJob;

import models.*;

public class Application extends Controller {

	public static void index() {
		//		List<Object> results = JPA.em().createNativeQuery("SELECT COUNT(description) FROM assays;").getResultList();
		//		System.out.println(results);
		render();
	}

	public static void updateDico(){
		new UpdateBardDictionaryJob().now();
		index();
	}

	public static void inspectDico(){
		List<BardTerm> terms = BardTerm.findAll();
		render(terms);
	}

	public static void naiveAnnotations(){
		new NaiveAnnotationJob().now();
		index();
	}

	public static void modifyLabels(){
		new ModifyLabelJob().now();
		index();
	}

	public static void naiveModifiedAnnotations() {
		new NaiveModifiedAnnotationJob().now();
		index();
	}

	public static void deleteAnnotations() {
		Fixtures.delete(Annotation.class);
		index();
	}
	
	public static void randomAnnotation() {
		Annotation annotation = Annotation.find("order by random()").first();
		String description = (String) JPA.em().createNativeQuery("SELECT description FROM assays where assays.assay_id = " + annotation.assay_id).getResultList().get(0);
		render(annotation, description);
	}

}