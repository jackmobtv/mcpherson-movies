package net.mcphersonmovies.shared;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailAddress;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import io.github.cdimascio.dotenv.Dotenv;
import net.mcphersonmovies.shared.Validators;
import org.jsoup.Jsoup;

import java.sql.SQLException;

public class AzureEmail {
    public static EmailClient getEmailClient() {
//        String connectionString = Dotenv.load().get("AZURE_EMAIL_CONNECTION");
        String connectionString = "";
        try {
            connectionString = Config.getEnv("AZURE_EMAIL_CONNECTION");
        } catch(IllegalStateException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

        EmailClient emailClient = new EmailClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        return emailClient;
    }

    public static String sendEmail(String emailOut, String subject, String bodyHTML){
        String error = "";
        if(emailOut == null || !Validators.isValidEmail(emailOut)) {
            error += "<br>Invalid email address: " + emailOut;
        }
        if(subject == null || subject.isEmpty()) {
            error += "<br>Subject is required";
        }
        if(bodyHTML == null || bodyHTML.isEmpty()) {
            error += "<br>Body is required";
        }

        if(!error.isEmpty()) {
            return error;
        }

        EmailClient emailClient = null;

        try {
            emailClient = getEmailClient();
        } catch (IllegalArgumentException ex) {
            error += "<br>" + ex.getMessage();
            return error;
        }

        String body = Jsoup.parse(bodyHTML).text();
        EmailMessage message = null;

        try {
            message = new EmailMessage()
                    .setSenderAddress(Config.getEnv("AZURE_EMAIL_FROM"))
                    .setToRecipients(emailOut)
                    .setSubject(subject)
                    .setBodyPlainText(body)
                    .setBodyHtml(bodyHTML);
        } catch (Exception ex) {
            error += "<br>" + ex.getMessage();
            return error;
        }

//        SyncPoller<EmailSendResult, EmailSendResult> poller = null;

        try{
//            poller = emailClient.beginSend(message);
            emailClient.beginSend(message);
        } catch(RuntimeException ex){
            error = ex.getMessage();
            return error;
        }

//        PollResponse<EmailSendResult> response = poller.waitForCompletion();

        //System.out.println("Operation Id: " + response.getValue().getId());

        return error;
    }
}
