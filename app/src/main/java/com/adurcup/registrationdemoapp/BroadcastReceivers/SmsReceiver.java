package com.adurcup.registrationdemoapp.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.adurcup.registrationdemoapp.services.OtpVerificationService;

/**
 * Created by kshivang on 08/04/16.
 *
 * this receiver automatically detect message with sender name with "ADURCP"
 * generally message come with sender name "LM-ADURCP" or "DM-ADURCP"
 *
 * then search for "is " regex code and then start reading OTP
 * then invoke OTPverificationservice
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Message Res", Toast.LENGTH_SHORT).show();
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                java.lang.Object[] pdusObj = (java.lang.Object[])bundle.get("pdus");
                for (java.lang.Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage;
                    /**
                     * SmsMessage.createFromPdu(byte[], format) is new change in Marshmallow
                     */
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = bundle.getString("format");
                        currentMessage = SmsMessage.createFromPdu((byte[])aPdusObj, format);
                    } else {
                        currentMessage = SmsMessage.createFromPdu((byte[])aPdusObj);
                    }

                    String senderAddress = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                    // if the SMS is not from our gateway, ignore the message
                    if (!senderAddress.toLowerCase().contains("adurcp")) {
                        return;
                    }else {
                    }

                    // verification code from sms
                    String verificationCode = getVerificationCode(message);

                    Log.e(TAG, "OTP received:" + verificationCode);

                    Intent httpIntent = new Intent(context, OtpVerificationService.class);
                    httpIntent.putExtra("otp", verificationCode);
                    context.startService(httpIntent);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Getting the OTP from sms message body
     * 'is ' is the separator of OTP from the message
     *
     * @param message
     * @return
     */
    private String getVerificationCode(String message) {
        String code = null;
        int index = message.indexOf("is ");

        if (index != -1) {
            int start = index + 3;
            int length = 6;
            code = message.substring(start, start + length);
            return code;
        }

        return code;
    }
}
