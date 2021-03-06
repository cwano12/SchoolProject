package com.wano.utils;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by Chris on 2/12/2018.
 */
public class AddressConverter {

    private Logger logger = Logger.getLogger(AddressConverter.class);

    // method to convert zips and adresses to lat and long values
    public List<GeocoderResult> getLatAndLong(String addressOrZip) {

        Geocoder geocoder = new Geocoder();
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(addressOrZip).getGeocoderRequest();
        GeocodeResponse geocodeResponse = null;
        try {
            geocodeResponse = geocoder.geocode(geocoderRequest);
        } catch (IOException e) {
            logger.error("Error trying to get address coordinates " + e);
        }

        List<GeocoderResult> results = null;
        if (geocodeResponse != null) {
            results = geocodeResponse.getResults();
        }
        return results;

    }
}
