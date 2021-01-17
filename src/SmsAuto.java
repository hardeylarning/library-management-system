
/**
 *
 * @author HARDEXICO
 */
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javax.swing.JOptionPane;
public class SmsAuto {
     public static final String ACCOUNT_SID =
            "ACc54ab1fbc242c0d0fd6364b77100c9d7";
    public static final String AUTH_TOKEN =
            "5c85f7d0335c34e8502b0ae06c6a5a19";
    public static void sendSms(String phone_no, String text_message){
        try{
              Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(phone_no), // to
                        new PhoneNumber("+13345184896"), // from
                        text_message)
                .create();
//       JOptionPane.showMessageDialog(null, message.getErrorMessage());
//        JOptionPane.showMessageDialog(null, "Message Sent Successfully to "+phone_no);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
   
          
        }
    }
    

