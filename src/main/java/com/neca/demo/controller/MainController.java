package com.neca.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neca.demo.form.PersonForm;
import com.neca.demo.model.Person;

@Controller
public class MainController {
	
	private static List<Person> persons = new ArrayList<Person>();
	
	static {
		persons.add(new Person("Tom", "Henks", 46));
		persons.add(new Person("Bred", "Pit", 49));
	}
	
	@Value("${welcome.message}")
	private String message;

	@Value("${error.message}")
	private String errorMessage;
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", message);
		mv.setViewName("index");
		
		return mv;
	}
	
	@RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
	public ModelAndView personList() {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("persons", persons);
		mv.setViewName("personList");
		
		return mv;
	}
	
	@RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
	public ModelAndView addPerson(PersonForm personForm) {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("personForm", personForm);
		mv.setViewName("addPerson");
		
		return mv;
	}
	
	@RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
	public String savePerson(Model model, @ModelAttribute("personForm") PersonForm personFrom) {

		String firstName = personFrom.getFirstName();
		String lastName = personFrom.getLastName();
		int age = personFrom.getAge();
		
		if(firstName != null && firstName.length() > 0 && lastName != null && lastName.length() > 0 && age != 0 && age > 0) {
			Person newPerson = new Person(firstName, lastName, age);
			persons.add(newPerson);
			return "redirect:/personList";
		}
		
		model.addAttribute("errorMessaage", errorMessage);
		return "addPerson";
	}
	
	@RequestMapping(value = {"/error"}, method = RequestMethod.GET)
	public String error(Model model) {
		
		model.addAttribute("errorMessage", errorMessage);
		
		return "error";
	}
	
}
