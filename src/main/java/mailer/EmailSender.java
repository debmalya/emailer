package mailer;

import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	private static Properties properties;

	private ReentrantLock lock = new ReentrantLock();

	private static final Logger logger = Logger.getLogger("EmailSender");

	/**
	 * Default constructor, it reads properties file and read all the properties
	 * related to mail configuration.
	 */
	public EmailSender(String mailerPropertyFileName) {

		try {
			lock.tryLock();
			properties = new Properties();
			properties.load(EmailSender.class.getClassLoader().getResourceAsStream(mailerPropertyFileName));
			logger.log(Level.INFO, "Properties initialized properly");
		} catch (Exception exc) {
			logger.log(Level.SEVERE, exc.getMessage(), exc);
		} finally {
			lock.unlock();
		}

	}

	/**
	 * 
	 * @param to
	 * @param content
	 * @return
	 */
	public boolean sendEmail(String to, String content, String subject) {
		if (to == null) {
			throw new IllegalArgumentException("No recipient specified");
		}
		boolean success = false;

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty("mail.smtp.from"),
						properties.getProperty("password"));

			}
		});

		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.from")));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(content);

			Transport.send(message);
			success = true;
			logger.log(Level.INFO, "Mail sent successfully");

		} catch (MessagingException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return success;
	}

}
