package com.thoughtworks.kanjuice.restService.models;

import com.thoughtworks.kanjuice.restService.config.BeanConfig;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Devices extends ArrayList<Device>{

    public Devices(){
    }

    public Devices(Device... devices){ this(asList(devices));}

    public Devices(List<Device> devices) {
        super(devices);
    }

    public String toJson(){
        return BeanConfig.jsonMapper().toJson(this);
    }
}
