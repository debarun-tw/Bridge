package com.thoughtworks.kanjuice.restService.controllers;

import com.thoughtworks.kanjuice.restService.config.BeanConfig;
import com.thoughtworks.kanjuice.restService.config.JsonMapper;
import com.thoughtworks.kanjuice.restService.models.Order;
import com.thoughtworks.kanjuice.restService.models.OrderBuilder;
import com.thoughtworks.kanjuice.restService.services.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        mvc = MockMvcBuilders
                .standaloneSetup(new OrderController(orderService))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(BeanConfig.jsonMapper()))
                .build();
    }

    @Test
    public void shouldCreateOrder() throws Exception {

        Order order = new OrderBuilder().withDefaults().withType("tea").build();

        mvc.perform(MockMvcRequestBuilders.post("/order")
                .content(new JsonMapper().toJson(order))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andReturn();

        verify(orderService).createOrder(any());
    }

}