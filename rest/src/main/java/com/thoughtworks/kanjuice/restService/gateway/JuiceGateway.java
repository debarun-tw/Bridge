package com.thoughtworks.kanjuice.restService.gateway;

import com.google.android.gcm.server.InvalidRequestException;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.thoughtworks.kanjuice.restService.config.ServiceConfig;
import com.thoughtworks.kanjuice.restService.services.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Service
public class JuiceGateway {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

    private ServiceConfig serviceConfig;

    @Autowired
    public JuiceGateway(ServiceConfig serviceConfig){
        this.serviceConfig = serviceConfig;
    }

    public boolean notify(String userID, String gcmRegId){
        final String GCM_API_KEY = serviceConfig.getAuthKey();
        final int retries = 3;
        Sender sender = new Sender(GCM_API_KEY);
        Message msg = new Message.Builder().addData("message",userID).build();

        try {
                Result result = sender.send(msg, gcmRegId, retries);

                if (StringUtils.isEmpty(result.getErrorCodeName())) {
                    LOGGER.info("GCM Notification is sent successfully {}", result.toString());
                    return true;
                }

                LOGGER.error("Error occurred while sending push notification :" + result.getErrorCodeName());

        } catch (InvalidRequestException e) {
            LOGGER.error("Invalid Request");
        } catch (IOException e) {
            LOGGER.error("IO Exception");
        }
        return false;
    }
}
