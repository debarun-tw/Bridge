package com.thoughtworks.kanjuice.restService.services;

import com.thoughtworks.kanjuice.restService.config.JsonMapper;
import com.thoughtworks.kanjuice.restService.exceptions.InvalidOrderTypeException;
import com.thoughtworks.kanjuice.restService.gateway.JuiceGateway;
import com.thoughtworks.kanjuice.restService.models.Order;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

    private final JuiceGateway juiceGateway;
    private final DeviceService deviceService;

    @Autowired
    public OrderService(JuiceGateway juiceGateway, DeviceService deviceService){
        this.juiceGateway = juiceGateway;
        this.deviceService = deviceService;
    }

    public boolean createOrderForTea(Order order) {
        return true;
    }

    public boolean createOrderForJuice(Order order) {
        String GCM_TOKEN = deviceService.findDeviceByID(order.getDeviceID()).getGcmToken();
        return juiceGateway.notify(order.getUserID(), GCM_TOKEN);
    }

    public String createOrder(Order order) throws InvalidOrderTypeException {
        boolean status = false;
        JSONObject responseObject = new JSONObject();

        switch (order.getType()){
            case "tea":
                LOGGER.info("Request for Tea from User ID: {}", order.getUserID());
                status = createOrderForTea(order);
                break;
            case "juice":
                LOGGER.info("Request for Juice from User ID: {}", order.getUserID());
                status = createOrderForJuice(order);
                break;
            default:
                LOGGER.error("Bad Request for Order, Invalid type");
        }

        if(status) {
            responseObject.put("status", "success");
            return new JsonMapper().toJson(responseObject);
        }
        else
            responseObject.put("status", "failure");
            return new JsonMapper().toJson(responseObject);
        }
}
