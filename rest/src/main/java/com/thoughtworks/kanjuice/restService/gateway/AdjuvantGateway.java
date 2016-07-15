package com.thoughtworks.kanjuice.restService.gateway;

import com.thoughtworks.kanjuice.restService.config.ServiceConfig;
import com.thoughtworks.kanjuice.restService.models.Order;
import com.thoughtworks.kanjuice.restService.models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdjuvantGateway {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdjuvantGateway.class);

    private ServiceConfig serviceConfig;
    private RestTemplate restTemplate;

    @Autowired
    public AdjuvantGateway(RestTemplate restTemplate, ServiceConfig serviceConfig){
        this.restTemplate = restTemplate;
        this.serviceConfig = serviceConfig;
    }

    public User getUserFromOrder(Order order){

        String url = serviceConfig.getAdjuvantURL();
        String endpoint = "/api/users/internalNumber/";
        final String API_KEY = serviceConfig.getAdjuvantAuthorizationKey();
        String finalUrl = url.concat(endpoint).concat(order.getCardID());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        LOGGER.info("Hitting this URL : {}", finalUrl );

        try {

            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<User> userResponse = restTemplate
                    .exchange(finalUrl, HttpMethod.GET, entity, User.class);

            if (userResponse.getStatusCode() == HttpStatus.OK) {
                JSONObject responseJson = new JSONObject(userResponse.getBody());
                LOGGER.info("User sent successfully : {}", responseJson.toString());
                return userResponse.getBody();
            } else {
                LOGGER.error(" Error while sending request : {}", userResponse.getBody());
                return null;
            }

        }catch (Exception e){
            e.printStackTrace();
            return new User();
        }


    }

    public boolean createOrderForTea(Order order){


        String url = serviceConfig.getAdjuvantURL();
        String endpoint = "/api/orders";

        final String API_KEY = serviceConfig.getAdjuvantAuthorizationKey();

        JSONObject payload = new JSONObject();
        JSONArray drinks = new JSONArray();
        JSONObject tea = new JSONObject();
        tea.put("name", "CTL");
        tea.put("quantity", 1);
        drinks.put(tea);

        payload.put("drinks", drinks);
        payload.put("isSwipe", true);
        if(getUserFromOrder(order).getEmpId().equals(null))
            return false;

        payload.put("employeeId", getUserFromOrder(order).getEmpId());
        payload.put("employeeName", getUserFromOrder(order).getEmployeeName());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        LOGGER.info("URL : {}", url);
        LOGGER.info("Headers: {}", headers.toString());
        LOGGER.info("Payload: {}", payload.toString());

        HttpEntity<String> entity = new HttpEntity<String>(payload.toString(), headers);

        ResponseEntity<String> orderResponse = restTemplate
                .exchange(url.concat(endpoint), HttpMethod.POST, entity, String.class);

        if (orderResponse.getStatusCode() == HttpStatus.OK) {
            JSONObject responseJson = new JSONObject(orderResponse.getBody());
            LOGGER.info("Order Request sent successfully : {}", responseJson.toString());
            return true;
        } else{
            LOGGER.error(" Error while sending request : {}", orderResponse.getBody());
        }

        return false;
    }






}
