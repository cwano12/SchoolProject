package com.wano.utils;

import com.wano.models.TreatmentCenter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Chris on 2/11/2018.
 */
public class DistanceCalculator {

    public static boolean calcDistance(String radius, TreatmentCenter treatmentCenter, double lat, double lon) {
        double radiusDistance = Double.valueOf(radius);

        double latitude = treatmentCenter.getLatitude();
        double longitude = treatmentCenter.getLongitude();
        double theta = lon - longitude;
        double distance = Math.sin(deg2rad(lat)) * Math.sin(deg2rad(latitude))
                + Math.cos(deg2rad(lat)) * Math.cos(deg2rad(latitude)) * Math.cos(deg2rad(theta));
        distance = Math.acos(distance);
        distance = rad2deg(distance);
        distance = distance * 60 * 1.1515;

        boolean flag = false;
        if (distance <= radiusDistance) {
            flag = true;
        }
        return flag;
    }

    public static List<TreatmentCenter> compareDistance(List<TreatmentCenter> treatmentCenters, double lat, double lon) {
        List<Double> distances = new ArrayList<>();
        List<TreatmentCenter> treatmentCenterList = new ArrayList<>();

        treatmentCenters.forEach(treatmentCenter -> {
            double latitude = treatmentCenter.getLatitude();
            double longitude = treatmentCenter.getLongitude();
            double theta = lon - longitude;
            double distance = Math.sin(deg2rad(lat)) * Math.sin(deg2rad(latitude))
                    + Math.cos(deg2rad(lat)) * Math.cos(deg2rad(latitude)) * Math.cos(deg2rad(theta));
            distance = Math.acos(distance);
            distance = rad2deg(distance);
            distance = distance * 60 * 1.1515;
            distances.add(distance);

            double minDistance = Collections.min(distances);
            if (distance == minDistance) {
                if(treatmentCenterList.size() > 0) {
                    treatmentCenterList.clear();
                    treatmentCenterList.add(treatmentCenter);
                } else {
                    treatmentCenterList.add(treatmentCenter);
                }

            }
        });

        return treatmentCenterList;
    }

    /**
     * Conversion method from degree to radian
     *
     * @param deg
     * @return radian
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * Conversion method from radian to degree
     *
     * @param rad
     * @return degree
     */
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    /**
     * This method gives an approximate lat long degrees to be added for
     * determining the boundary values of mix and max to be checked in the
     * query. For safe side adding a 50% extra to the miles. Calculation based
     * on 1 degree corresponds to 70 miles which is the max value.
     *
     * @param radius
     * @return degree
     */
    public static double getDeltaDegrees(String radius) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        Double radiusDistance = Double.valueOf(radius);
        radiusDistance = radiusDistance + (radiusDistance * 0.5);
        double degrees = radiusDistance / 70;

        return Double.valueOf(decimalFormat.format(degrees));
    }
}
