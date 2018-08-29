package mailer;

import org.junit.Assert;
import org.junit.Test;

public class EmailSenderTest {
	
	EmailSender emailSender;

	

	@Test
	public void testSendEmail() {
		emailSender = new EmailSender("Mailer.properties");
		Assert.assertTrue(emailSender.sendEmail("<sender1>,<sender2>", "This is for testing email", "Test"));
	}

}
