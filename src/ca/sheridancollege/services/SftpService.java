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
		 String SFTPHOST = "<IP for SFTP Server>";
	        int SFTPPORT = 22;
	        String SFTPUSER = "<Enter username>";
	        String SFTPPASS = "<Enter Password to SFTP Server>";
	        String SFTPWORKINGDIR = "<Working directory path on the SFTP Server>";

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
	            File f = new File("/Users/saad/Desktop/"+filename);
	            channelSftp.put(new FileInputStream(f), f.getName());
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

	}

