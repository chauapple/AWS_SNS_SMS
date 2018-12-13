package com.faber.aws_sns_sms;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Le Dinh Tuan
 * @email ledinhtuan@fabercompany.co.jp
 */
public class SMSSend {

    //SMS message can contain 160 GSM characters, 140 ASCII characters, or 70 UCS-2 characters.
    //The total size limit for a single SMS Publish action is 1,600 characters.
    //https://docs.aws.amazon.com/sns/latest/api/API_Publish.html
    //https://docs.aws.amazon.com/sns/latest/api/API_MessageAttributeValue.html
    
    public static void main(String[] args) {
        // Your Credentials
        String ACCESS_KEY = "YOUR_ACCESS_KEY";
        String SECRET_KEY = "YOUR_SECRET_KEY";

        AmazonSNSClient snsClient = new AmazonSNSClient(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
        snsClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
        
        Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
            .withStringValue("MIERUCA") //The sender ID shown on the device.
            .withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
            .withStringValue("0.50") //Sets the max price to 0.50 USD.
            .withDataType("Number"));
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
            .withStringValue("Promotional") //Sets the type to promotional.
            .withDataType("String"));

        String message = "Hello Chau";
        String phoneNumber = "+81-804332-8238";  // Ex: +91XXX4374XX
        sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
    }

    // Send SMS to a Phone Number
    public static void sendSMSMessage(AmazonSNSClient snsClient,
        String message, String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = snsClient.publish(new PublishRequest()
            .withMessage(message)
            .withPhoneNumber(phoneNumber)
            .withMessageAttributes(smsAttributes));
        System.out.println(result); // Prints the message ID. 
    }

}
