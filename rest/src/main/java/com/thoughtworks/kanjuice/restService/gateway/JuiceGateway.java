package com.thoughtworks.kanjuice.restService.gateway;

import com.thoughtworks.kanjuice.restService.config.ServiceConfig;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Service
public class JuiceGateway {
    private static final Logger LOGGER = LoggerFactory.getLogger(JuiceGateway.class);

    private ServiceConfig serviceConfig;
    private RestTemplate restTemplate;

    @Autowired
    public JuiceGateway(RestTemplate restTemplate, ServiceConfig serviceConfig){
        this.restTemplate = restTemplate;
        this.serviceConfig = serviceConfig;
    }

    public boolean notify(String userID, String cardID, String gcmRegId) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        String url = "https://gcm-http.googleapis.com/gcm/send";
        final String GCM_API_KEY = serviceConfig.getAuthKey();

        String messageToBeSent = cardID.concat(",").concat(userID);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "key=" + GCM_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setPragma("no-cache");
        headers.setCacheControl("no-cache");
        headers.set("X-Content-Type-Options","nosniff");

        JSONObject payload = new JSONObject();
        JSONObject message = new JSONObject();

        message.put("title", "Thoughtworks, Kanjuice");
        message.put("message", messageToBeSent);

        LOGGER.info("Sending following payload dor notification : {}", messageToBeSent);

        payload.put("to", gcmRegId);
        payload.put("data", message);

        HttpEntity<String> entity = new HttpEntity<String>(payload.toString(), headers);

        ResponseEntity<String> loginResponse = restTemplate
                .exchange(url, HttpMethod.POST, entity, String.class);

        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            JSONObject responseJson = new JSONObject(loginResponse.getBody());
            LOGGER.info("GCM Request sent, Response : {}", responseJson.toString());
            if(Integer.parseInt(responseJson.get("success").toString()) > 0) {
                LOGGER.info("Notification sent Successfully");
                return true;
            }
        } else{
            LOGGER.error(" Error while sending request : {}", loginResponse.getBody());
        }
        return false;
    }
}
