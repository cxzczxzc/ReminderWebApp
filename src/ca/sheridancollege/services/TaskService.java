package ca.sheridancollege.services;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ca.sheridancollege.beans.Tasks;
import ca.sheridancollege.dao.Dao;

public class TaskService implements Job
{
	private TwilioService twilio = new TwilioService();
	private Dao dao = new Dao();
	private TwiMLService twiml = new TwiMLService();
	private SftpService sftp = new SftpService();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String taskId = dataMap.getString("taskId");
		List<Tasks> taskList = dao.getTaskById(Integer.parseInt(taskId));
		Tasks task = taskList.get(0);
		String message = "Hey " + task.getName() +", your task is due. The task description is "+ task.getDescription() + ". The priority of this task is " + task.getPriority();
        if(task.getPriority().equals("High") || task.getPriority().equals("Very High")) 
        {
        	message = "Hey " + task.getName() +", your task is due. The task description is "+ task.getDescription() + ". The priority of this task is " + task.getPriority() + ".Stop slacking and get to work!" ; 
        } 
		String phoneNumber = task.getPhoneNumber();
		String filename = taskId + ".xml";
		//generate TwiML
		twiml.generateTwiML(message, filename);
		//upload to server
		sftp.Sftp(filename);
		//send sms
		twilio.sendSMS(phoneNumber,message);
		//call
		System.out.println(filename);
		System.out.println(message);
		try {
			Thread.sleep(10000);
			twilio.call(phoneNumber, message, filename);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	

}
