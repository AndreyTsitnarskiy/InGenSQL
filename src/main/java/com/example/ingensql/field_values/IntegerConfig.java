package com.example.ingensql.field_values;

import java.util.Random;

public class IntegerConfig implements Values{

    private IntegerConfig integerConfig;
    @Override
    public void getValuesConfig(GenType genType) {
        if(genType.equals("RANDOM")){

        } else if (genType.equals("RANDOM_RANGE")){
        }
    }
}
