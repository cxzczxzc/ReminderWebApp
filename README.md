# ReminderWebApp
Appointment Reminder Application built using Java Spring MVC and Twilio.

## What is the use of this application?
This is a web application that can serve as a personal assistant for scheduling tasks and reminders. 
The application takes the user to a web portal where they can create, add, modify or remove existing appointments or reminders. 

When it is time for an appointment, the user gets a phone call and a text message containing the description of the task or reminder specified by them.

## What technologies does this application use?
* **Twilio API** - For phone calls and text messages.
* **Remote SFTP Server** - For uploading TwiML files. These files are read by the Twilio text-to-speech engine during the phone calls.
* **JSch** - To faciliate file upload to a remote server.
* **Quartz Scheduler** - To schedule appointments. It has two main components - job and trigger. The job is what has to be done and the 
  trigger is what tells the scheduler to start the job. It runs as a singleton throughout the lifecycle of the application.

* **MySQL** - Database that stores appointment information.

## How does the application function?

The application's web application component is built using Spring MVC. Here's what happens when a user first uses this application:
1. The user goes to the application homepage. They are presented with the option to register a new phone number.
2. The user enters their phone number. Twilio triggers a call to the user's cell phone which contains a verification code.
  This verification code is entered by the user in the application. A request with the validation code is made to Twilio upon submitting the code. 
  The number gets registered to receive phone calls and text messages through this application.
3. The user creates Tasks and adds a description to them.
4. The description from the task is converted to a TwiML file and SFTPd to a server.
5. The task is scheduled to be run at the user specified time by the Quartz Scheduler.
6. When the time arrives, Quartz triggers the task and a SMS and phone call is sent to the user, which contain the details about the task.

## How can this application be scaled and improved?
A few pointers to scale this application are:-
* Convert it to a Spring Boot Application and create a Docker image for this application.
* Take out the configuration parameters (Twilio credentials, SFTP server information etc.) and load them via configuration files.
* Add authentication / authorization to be able to serve multiple users.
