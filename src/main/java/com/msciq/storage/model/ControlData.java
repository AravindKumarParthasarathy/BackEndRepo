package com.msciq.storage.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ControlData implements Serializable {

    private String bu;
    private String location;
}
