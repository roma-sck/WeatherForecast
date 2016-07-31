package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class Main {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "kultprosvet.com.wheatherforecast.db");
        createEntities(schema);
        new DaoGenerator().generateAll(schema, "D:\\git\\wheatherforecast\\app\\src\\main\\java");
    }

    private static void createEntities(Schema schema) {
        Entity city = schema.addEntity("CityDb");
        city.addIdProperty();
        city.addStringProperty("name").index();
        city.addDoubleProperty("latitude").codeBeforeField("//latitude");
        city.addDoubleProperty("longitude").codeBeforeField("//comment");

//        Entity forecast = schema.addEntity("ForecastDb");
//        forecast.addIdProperty();
//        forecast.addFloatProperty("max_temp");
//        forecast.addFloatProperty("min_temp");
//        forecast.addStringProperty("description");
//        Property cityId = forecast.addLongProperty("city_id").index().getProperty();
//
//        city.addToMany(forecast,cityId,"forecasts");
    }
}
