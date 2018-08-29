# Emailer
Component to do email functionality.

## Configuration
Edit src/main/resources Mailer.properties

## How to call
* Put Mailer.properties in classpath
* Instantiate EmailSender sender = new EmailSender("Mailer.properties");
* sender.sendEmail("<sender1>,<sender2>", "<Subject>", "<Content>"));
