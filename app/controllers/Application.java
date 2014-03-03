package controllers;

import play.*;
import play.db.jpa.JPA;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

	public static void index() {
		List<Object> results = JPA.em().createNativeQuery("SELECT COUNT(description) FROM assays;").getResultList();
		System.out.println(results);
	}

}