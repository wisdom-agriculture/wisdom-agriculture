package com.yaohy.intelligentfarmingbackend.constant;

public enum SensorStatusConstant {

    SERVICE_ERROR("ServiceError","ServiceError"),
    SET_SLEEP("SetSleep","Sleep"),
    DEVICE_ONLINE("DeviceOnline","Online");


    private String name;
    private String status;

    SensorStatusConstant(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
