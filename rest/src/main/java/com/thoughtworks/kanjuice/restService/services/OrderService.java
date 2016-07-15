package com.thoughtworks.kanjuice.restService.services;

import com.thoughtworks.kanjuice.restService.config.JsonMapper;
import com.thoughtworks.kanjuice.restService.exceptions.InvalidOrderTypeException;
import com.thoughtworks.kanjuice.restService.gateway.AdjuvantGateway;
import com.thoughtworks.kanjuice.restService.gateway.JuiceGateway;
import com.thoughtworks.kanjuice.restService.models.Order;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final JuiceGateway juiceGateway;
    private final DeviceService deviceService;
    private final UserService userService;
    private AdjuvantGateway adjuvantGateway;

    @Autowired
    public OrderService(JuiceGateway juiceGateway, DeviceService deviceService, UserService userService, AdjuvantGateway adjuvantGateway){
        this.juiceGateway = juiceGateway;
        this.deviceService = deviceService;
        this.userService = userService;
        this.adjuvantGateway = adjuvantGateway;
    }

    public boolean createOrderForTea(Order order) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, ShortBufferException, UnsupportedEncodingException, InvalidKeyException {
        return adjuvantGateway.createOrderForTea(order);
    }

    public boolean createOrderForJuice(Order order) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        String GCM_TOKEN = deviceService.findDeviceByID(order.getDeviceID()).getGcmToken();
        String userID = adjuvantGateway.getUserFromOrder(order).getEmpId();
        if(userID.equals(null))
         return false;
        return juiceGateway.notify(userID, GCM_TOKEN);
    }

    public String createOrder(Order order) throws InvalidOrderTypeException, KeyManagementException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, ShortBufferException, NoSuchPaddingException {
        boolean status = false;
        JSONObject responseObject = new JSONObject();

        switch (order.getType()){
            case "tea":
                LOGGER.info("Request for Tea from Card ID: {}", order.getCardID());
                status = createOrderForTea(order);
                break;
            case "juice":
                LOGGER.info("Request for Juice from Card ID: {}", order.getCardID());
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

    public String createOrder(String type, String deviceID, String userID) throws IOException, NoSuchAlgorithmException, InvalidOrderTypeException, KeyManagementException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, ShortBufferException, IllegalBlockSizeException {
        Order createOrderFromParameters = new Order(type, deviceID, userID);
        return createOrder(createOrderFromParameters);
    }
}
