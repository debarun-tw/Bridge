package com.thoughtworks.kanjuice.restService.repositories;

import com.thoughtworks.kanjuice.restService.models.Device;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class AllDevicesTest {

    @Mock
    private Sort sort;
    @Mock
    private Pageable pageable;
    @Mock
    private DevicesMongoRepository devicesMongoRepository;
    @Mock
    private MongoTemplate mongoTemplate;
    @Mock
    private Device device;

    private String deviceID;
    private List<Device> devices;
    private AllDevices allDevices;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        deviceID = "deviceID";
        devices = new ArrayList<>();
        allDevices = new AllDevices(devicesMongoRepository, mongoTemplate);
    }

    @Test
    public void shouldDelegateSavingADevice() {
        allDevices.save(device);

        verify(devicesMongoRepository).save(device);
    }

    @Test
    public void shouldDelegateSavingACollectionOfDevices() {
        allDevices.save(devices);

        verify(devicesMongoRepository).save(devices);
    }

    @Test
    public void shouldDelegateFindingADevice() {
        allDevices.findOne(deviceID);

        verify(devicesMongoRepository).findOne(deviceID);
    }

    @Test
    public void shouldDelegateCheckingIfADeviceExists() {
        allDevices.exists(deviceID);

        verify(devicesMongoRepository).exists(deviceID);
    }

    @Test
    public void shouldDelegateFindingAllDevices() {
        allDevices.findAll();

        verify(devicesMongoRepository).findAll();
    }

    @Test
    public void shouldDelegateFindingAllFlightPlansByDeviceIDs() {
        List<String> DeviceIDs = new ArrayList<>();

        allDevices.findAll(DeviceIDs);

        verify(devicesMongoRepository).findAll(DeviceIDs);
    }

    @Test
    public void shouldDelegateFindingAllDevicesInASortingOrder() {
        allDevices.findAll(sort);

        verify(devicesMongoRepository).findAll(sort);
    }

    @Test
    public void shouldDelegateFindingAllDevicesInPages() {
        allDevices.findAll(pageable);

        verify(devicesMongoRepository).findAll(pageable);
    }

    @Test
    public void shouldDelegateCountingDevices() {
        allDevices.count();

        verify(devicesMongoRepository).count();
    }

    @Test
    public void shouldDelegateDeleteById() {
        allDevices.delete(deviceID);

        verify(devicesMongoRepository).delete(deviceID);
    }

    @Test
    public void shouldDelegateDeleteByDevice() {
        allDevices.delete(device);

        verify(devicesMongoRepository).delete(device);
    }

    @Test
    public void shouldDelegateDeletingACollectionOfDevices() {
        allDevices.delete(devices);

        verify(devicesMongoRepository).delete(devices);
    }

    @Test
    public void shouldDelegateDeletingAllDevices() {
        allDevices.deleteAll();

        verify(devicesMongoRepository).deleteAll();
    }

    @Test
    public void shouldDelegateInsertingADevice() {
        allDevices.insert(device);

        verify(devicesMongoRepository).insert(device);
    }

    @Test
    public void shouldDelegateInsertingMultipleDevices() {
        allDevices.insert(devices);

        verify(devicesMongoRepository).insert(devices);
    }
}