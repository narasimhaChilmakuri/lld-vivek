package solutions;

import java.util.ArrayList;
import java.util.List;

class User {
    String email;
    String phone;
    User(String email, String phone) { this.email = email; this.phone = phone; }
}

class SmtpMailer {
    void send(String templ, String to, String body) {
        System.out.println("[SMTP] template=" + templ + " to=" + to + " body=" + body);
    }
}

class TwilioClient {
    void sendOTP(String phone, String code) {
        System.out.println("[Twilio] OTP " + code + " -> " + phone);
    }
}

interface INotificationChannel{
    void send(User user);
}



class MailNotification implements INotificationChannel{

    private SmtpMailer smtpMailer = new SmtpMailer();

    @Override
    public void send(User user) {
        smtpMailer.send("Welcome",user.email,"Welcome OnBoard!");
    }
}

class SmsNotification implements INotificationChannel{

    private TwilioClient twilioClient = new TwilioClient();
    @Override
    public void send(User user) {
        twilioClient.sendOTP(user.phone,"123456");
    }
}

class SignUpService{

    List<INotificationChannel> notificationChannels;

    public SignUpService(List<INotificationChannel> notificationChannels){
        this.notificationChannels = notificationChannels;
    }

    boolean signUp(User user){

        if(user.email==null && user.phone==null){
            return false;
        }

        for(INotificationChannel notificationChannel:notificationChannels){
            notificationChannel.send(user);
        }

        return true;
    }

}


public class NotifyDIPOCPSolution {
    public static void main(String[] args) {

        List<INotificationChannel> notificationChannels = new ArrayList<>();
        notificationChannels.add(new MailNotification());
        notificationChannels.add(new SmsNotification());

        SignUpService signUpService = new SignUpService(notificationChannels);

        signUpService.signUp(new User("user@example.com", "+15550001111"));


    }
}
