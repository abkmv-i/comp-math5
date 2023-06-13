package com.lab5spring;


import lombok.Getter;
import lombok.Setter;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
public class Form {
    private ArrayList<Double> inputData;
    private double x;
    private int numberOfFunction;

    public Form(ArrayList<Double> inputData, double x, int numberOfFunction) {
        this.inputData = inputData;
        this.x = x;
        this.numberOfFunction = numberOfFunction;
    }

    public Form() {

    }
}
