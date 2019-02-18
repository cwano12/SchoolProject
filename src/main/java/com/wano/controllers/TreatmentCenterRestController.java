package com.wano.controllers;

import com.wano.models.TreatmentCenter;
import com.wano.services.TreatmentCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2/11/2018.
 */
@RequestMapping("/rest")
@RestController
public class TreatmentCenterRestController {

    @Autowired
    private TreatmentCenterService treatmentCenterService;

    @RequestMapping("/{id}")
    public TreatmentCenter findOneTreatmentCenter(@PathVariable int id) {
        TreatmentCenter treatmentCenter = treatmentCenterService.getTreatmentCenter(id);
        return treatmentCenter;
    }

    @RequestMapping("/all")
    public List<TreatmentCenter> findAllTreatmentCenters() {
        List<TreatmentCenter> treatmentCenters = treatmentCenterService.getAllTreatmentCenters();
        return treatmentCenters;
    }

    @RequestMapping("/zip/{zip}/{radius}")
    public List<TreatmentCenter> findTreatmentCentersByZip(@PathVariable String zip, @PathVariable String radius) {
        List<TreatmentCenter> treatmentCenters = treatmentCenterService.getTreatmentCentersByZip(zip, radius);
        return treatmentCenters;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addTreatmentCenter(@RequestBody TreatmentCenter treatmentCenter) {
        treatmentCenterService.insertTreatmentCenter(treatmentCenter);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void updateTreatmentCenter(@RequestBody TreatmentCenter treatmentCenter) {
        System.out.println("In Put method " + treatmentCenter.toString());
        treatmentCenterService.updateTreatmentCenter(treatmentCenter);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeTreatmentCenter(@PathVariable int id) {
        treatmentCenterService.deleteTreatmentCenter(id);
    }

}
