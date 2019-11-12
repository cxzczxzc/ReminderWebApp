package ca.sheridancollege.services;

import java.io.File;
import java.io.FileInputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SftpService {
  
	public void Sftp(String filename) 
	{
		 String SFTPHOST = "142.55.32.48";
	        int SFTPPORT = 22;
	        String SFTPUSER = "ahmasaad";
	        String SFTPPASS = "tRwM5A!dXQg";
	        String SFTPWORKINGDIR = "/home/ahmasaad/public_html/javaproject";

	        Session session = null;
	        Channel channel = null;
	        ChannelSftp channelSftp = null;

	        try {
	            JSch jsch = new JSch();
	            session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
	            session.setPassword(SFTPPASS);
	            java.util.Properties config = new java.util.Properties();
	            config.put("StrictHostKeyChecking", "no");
	            session.setConfig(config);
	            session.connect();
	            channel = session.openChannel("sftp");
	            channel.connect();
	            channelSftp = (ChannelSftp) channel;
	            channelSftp.cd(SFTPWORKINGDIR);
	            File f = new File("/Users/xcode/Desktop/"+filename);
	            channelSftp.put(new FileInputStream(f), f.getName());
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

	}

