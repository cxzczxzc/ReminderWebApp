package ca.sheridancollege.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.sheridancollege.beans.Phone;
import ca.sheridancollege.beans.Tasks;
import ca.sheridancollege.dao.Dao;
import ca.sheridancollege.services.TaskService;
import ca.sheridancollege.services.TwilioService;

import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;


@Controller
public class MainController {
	private Dao dao = new Dao();	
    private TwilioService twilio = new TwilioService();
    private Scheduler scheduler;
	/**
	 * 
	 * @param model
	 * @return first page of the web application
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		Phone phone = new Phone();
		model.addAttribute("phone", phone);
		return "index";
	}
	
	@RequestMapping(value = "/createTask", method = RequestMethod.GET)
	public String createTask(Model model) {
		Tasks task = new Tasks();
		model.addAttribute("task", task);
		return "createTask";
	}
	/**
	 *  method the handles the submission of saveCustomer form action
	 * @param model
	 * @param customer
	 * @return page that displays all phonebook entries
	 */
	@RequestMapping(value = "/saveTask", method = RequestMethod.POST)
	public String saveTask(Model model, @ModelAttribute Tasks task) {
		System.out.println(task.getDueDate().toString());
		dao.saveOrUpdateTask(task);
		List<Tasks> taskList = dao.getTaskList();
		model.addAttribute("taskList", taskList);
		scheduleTask(task);
		return "allTasks";
	}
    /**
     * 
     * @param model
     * @param id
     * @return initial page with values of the customer loaded populated in the fields
     */
	@RequestMapping(value = "/editTask/{id}", method = RequestMethod.GET)
	public String editContact(Model model, @PathVariable int id) {
		List<Tasks> taskList = dao.getTaskById(id);
		updateTaskTrigger(taskList.get(0));
		model.addAttribute("task", taskList.get(0));
		return "createTask";
	}
	
		
	@RequestMapping(value = "/deleteTask/{id}", method = RequestMethod.GET)
	public String deleteContact(Model model, @PathVariable int id) {
	    dao.deleteTaskById(id);
	    deleteJobAndTrigger(id);
		List<Tasks> taskList = dao.getTaskList();
		model.addAttribute("taskList", taskList);
		return "allTasks";
	}
    /**
     * method called to view all the entries in the phonebook
     * @param model
     * @return
     */
	@RequestMapping(value = "/viewTask", method = RequestMethod.GET)
	public String viewAll(Model model) {
		List<Tasks> taskList = dao.getTaskList();
		model.addAttribute("taskList", taskList);
		return "allTasks";
	}
	
	@RequestMapping(value="/getTasksByName/{name}", produces="application/json")
	@ResponseBody
	public Map<String, Object> getTasksByName(@PathVariable String name) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Tasks> task = dao.getTaskByName(name);
		data.put("task", task);
		return data;
	}
	
	
	@RequestMapping(value = "/registerNumber", method = RequestMethod.POST)
	public String registerNumber(Model model, @RequestParam String phoneNumber) 
	{
		String code = twilio.registerNewNumber(phoneNumber);
		Phone phone = new Phone();
		phone.setCode(code);
		phone.setPhoneNumber(phoneNumber);
		model.addAttribute("phone", phone);
        return "index";
	}
	

	public void scheduleTask(Tasks task) 
	{
	    try {
		String taskId = Integer.toString(task.getId());
		String date = task.getDueDate();
		
		Date date1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(date); 
	    JobDetail job =  newJob(TaskService.class).withIdentity("TaskJob-" + taskId)
	                     .usingJobData("taskId", taskId).build();
	    Trigger trigger =
	            newTrigger().withIdentity("TaskTrigger-" + taskId).startAt(date1).build();

			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	public void updateTaskTrigger(Tasks task) 
	{
		String taskId = Integer.toString(task.getId());
        String date = task.getDueDate();
		try {
			Date triggerDate= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(date);
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			TriggerKey key = new TriggerKey("TaskTrigger-"+taskId);
			Trigger oldTrigger = scheduler.getTrigger(key);
			TriggerBuilder<?> tb = oldTrigger.getTriggerBuilder();
			Trigger newTrigger = tb.startAt(triggerDate)
				    .build();
			scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteJobAndTrigger(int id) 
    {
		String taskId = Integer.toString(id);
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			JobKey key = new JobKey("TaskJob-"+taskId);
			scheduler.deleteJob(key);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }
}
