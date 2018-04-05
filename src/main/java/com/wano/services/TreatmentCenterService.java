package com.wano.services;

import com.google.code.geocoder.model.GeocoderResult;
import com.wano.models.TreatmentCenter;
import com.wano.repository.TreatmentCenterRepository;
import com.wano.utils.AddressConverter;
import com.wano.utils.DistanceCalculator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 600158489 on 2/11/2018.
 */
@Service
public class TreatmentCenterService {

    DistanceCalculator distanceCalculator = new DistanceCalculator();
    AddressConverter addressConverter = new AddressConverter();

    Logger logger = Logger.getLogger(TreatmentCenterService.class);

    @Autowired
    private TreatmentCenterRepository treatmentCenterRepository;

    public List<TreatmentCenter> getAllTreatmentCenters() {
        List<TreatmentCenter> treatmentCenters = new ArrayList<>();
        treatmentCenterRepository.findAll().forEach(treatmentCenters :: add);
        return treatmentCenters;
    }

    public List<TreatmentCenter> getTreatmentCentersByZip(String zip, String radius) {
        if(!zip.equals("")) { // if zip is not empty, search by zip first
            List<TreatmentCenter> treatmentCenters = treatmentCenterRepository.findByZip(zip);
            if (!treatmentCenters.isEmpty()) {
                return treatmentCenters;
            } else { // if search does not yield any results, return results within radius
                treatmentCenters = getTreatmentCentersByDistance(zip, radius, getAllTreatmentCenters());
                if (!treatmentCenters.isEmpty()) {
                    return treatmentCenters;
                } else { // if radius search does not return results, show closest center
                    treatmentCenters = getClosestTreatmentCenter(zip, getAllTreatmentCenters());
                    return treatmentCenters;
                }
            }
        } else { // return all treatment centers
            List<TreatmentCenter> treatmentCenters = getAllTreatmentCenters();
            return treatmentCenters;
        }
    }

    public TreatmentCenter getTreatmentCenter(int id) {
        TreatmentCenter treatmentCenter = treatmentCenterRepository.findOne(id);
        return treatmentCenter;
    }

    // admin search method
    public List<TreatmentCenter> searchTreatmentCenters(TreatmentCenter treatmentCenter) {

        // using matcher to search by example
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id", "latitude", "longitude")
                .withIgnoreCase();

        treatmentCenter = convertEmptyStringsToNull(treatmentCenter);

        logger.info("Searching for treatment centers with the following criteria: " + treatmentCenter);

        List<TreatmentCenter> treatmentCenters = treatmentCenterRepository.findAll(Example.of(treatmentCenter, matcher));
        return treatmentCenters;
    }

    public void updateTreatmentCenter(TreatmentCenter treatmentCenter) { insertTreatmentCenter(treatmentCenter); }

    public void insertTreatmentCenter(TreatmentCenter treatmentCenter) {

        treatmentCenter.getLongitude();
        treatmentCenter.getLatitude();
        //treatmentCenterRepository.save(treatmentCenter);
    }

    public void deleteTreatmentCenter(int id) { treatmentCenterRepository.delete(id); }

    // finds all treatment centers within user provided radius
    private List<TreatmentCenter> getTreatmentCentersByDistance(String zip, String radius, List<TreatmentCenter> treatmentCenterList) {

        List<GeocoderResult> results = addressConverter.getLatAndLong(zip);
        double lat = results.get(0).getGeometry().getLocation().getLat().doubleValue();
        double lon = results.get(0).getGeometry().getLocation().getLng().doubleValue();

        List<TreatmentCenter> treatmentCenters = new ArrayList<>();
        treatmentCenterList.forEach(treatmentCenter -> {
            if (distanceCalculator.calcDistance(radius, treatmentCenter, lat, lon)) {
                treatmentCenters.add(treatmentCenter);
            }
        });
        return treatmentCenters;
    }

    // finds the closest treatment center regardless of search radius
    private List<TreatmentCenter> getClosestTreatmentCenter(String zip, List<TreatmentCenter> treatmentCenterList) {
        List<GeocoderResult> results = addressConverter.getLatAndLong(zip);
        double lat = results.get(0).getGeometry().getLocation().getLat().doubleValue();
        double lon = results.get(0).getGeometry().getLocation().getLng().doubleValue();

        List<TreatmentCenter> treatmentCenters = distanceCalculator.compareDistance(treatmentCenterList, lat, lon);

        return treatmentCenters;
    }

    private TreatmentCenter convertEmptyStringsToNull(TreatmentCenter treatmentCenter) {
        if(treatmentCenter.getCity().isEmpty()) { treatmentCenter.setCity(null); }
        if(treatmentCenter.getState().isEmpty()) { treatmentCenter.setState(null); }
        if(treatmentCenter.getAddress().isEmpty()) { treatmentCenter.setAddress(null); }
        if(treatmentCenter.getName().isEmpty()) { treatmentCenter.setName(null); }
        if(treatmentCenter.getZip().isEmpty()) { treatmentCenter.setZip(null); }
        if(treatmentCenter.getPhoneNumber().isEmpty()) { treatmentCenter.setPhoneNumber(null); }

        return treatmentCenter;
    }
}
