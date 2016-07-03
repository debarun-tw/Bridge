package com.thoughtworks.kanjuice.restService.controllers;

import com.mongodb.MongoException;
import com.thoughtworks.kanjuice.restService.config.BeanConfig;
import com.thoughtworks.kanjuice.restService.config.JsonMapper;
import com.thoughtworks.kanjuice.restService.models.Device;
import com.thoughtworks.kanjuice.restService.models.DeviceBuilder;
import com.thoughtworks.kanjuice.restService.repositories.AllDevices;
import com.thoughtworks.kanjuice.restService.services.DeviceService;
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

public class DeviceControllerTest {
    @Mock
    private DeviceService deviceService;
    @Mock
    private AllDevices allDevices;
    @Mock
    private MongoException mongoException;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(new DeviceController(deviceService))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(BeanConfig.jsonMapper()))
                .build();
    }

    @Test
    public void shouldCreateOrUpdateDevice() throws Exception {

        Device device = new DeviceBuilder().withDefaults().build();

        mvc.perform(MockMvcRequestBuilders.post("/device")
                .content(new JsonMapper().toJson(device))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andReturn();

        verify(deviceService).createOrUpdateDevice(any(Device.class));
    }


}