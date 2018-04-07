package com.wano.controllers;

import com.wano.models.TreatmentCenter;
import com.wano.services.TreatmentCenterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * Created by 600158489 on 2/14/2018.
 */
@Controller
@RequestMapping("/treatment")
public class TreatmentCenterController {

    ModelAndView modelAndView = new ModelAndView();
    Logger logger = Logger.getLogger(TreatmentCenterController.class);

    @Autowired
    private TreatmentCenterService treatmentCenterService;

    @RequestMapping("/{id}")
    public ModelAndView findOneTreatmentCenter(@PathVariable int id) {
        TreatmentCenter treatmentCenter = treatmentCenterService.getTreatmentCenter(id);
        modelAndView.addObject("centers", treatmentCenter);
        modelAndView.setViewName("results");
        return modelAndView;
    }

    @RequestMapping("/all")
    public ModelAndView getAllTreatmentCenters() {
        List<TreatmentCenter>  treatmentCenters = treatmentCenterService.getAllTreatmentCenters();
        modelAndView.addObject("centers", treatmentCenters);
        modelAndView.setViewName("results");
        return modelAndView;
    }

    @RequestMapping("/search")
    public ModelAndView searchTreatmentCenters(@ModelAttribute("treatmentCenter") TreatmentCenter treatmentCenter) {
        List<TreatmentCenter> treatmentCenters = treatmentCenterService.searchTreatmentCenters(treatmentCenter);
        modelAndView.addObject("success", "");
        modelAndView.addObject("centers", treatmentCenters);
        modelAndView.setViewName("admin/adminResults");
        return modelAndView;
    }

    @RequestMapping("/zip")
    public ModelAndView findTreatmentCentersByZip(@RequestParam("zip") String zip, @RequestParam("radius") String radius) {
        List<TreatmentCenter> treatmentCenters = treatmentCenterService.getTreatmentCentersByZip(zip, radius);
        modelAndView.addObject("centers", treatmentCenters);
        modelAndView.setViewName("results");
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView addTreatmentCenter(@ModelAttribute("treatmentCenter") TreatmentCenter treatmentCenter) {
        String error = validateTreatmentCenter(treatmentCenter);
        String success = null;

        if(!error.isEmpty()) {
            modelAndView.addObject("error", error);
            System.out.println(error);
            modelAndView.addObject(treatmentCenter);
        } else {
            //treatmentCenterService.insertTreatmentCenter(treatmentCenter);
            success = "Added treatment center: " + treatmentCenter.getName();
            logger.info("Inserted treatment center: " + treatmentCenter);
            modelAndView.addObject("success", success);
        }
        modelAndView.setViewName("admin/addTreatmentCenter");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateTreatmentCenter(@ModelAttribute("treatmentCenter") TreatmentCenter treatmentCenter) {
        String error = validateTreatmentCenter(treatmentCenter);
        String success = null;

        if(!error.isEmpty()) {
            modelAndView.addObject("error", error);
            System.out.print(error);
            modelAndView.addObject(treatmentCenter);
            modelAndView.setViewName("admin/updateTreatmentCenter");
        } else {
            //addTreatmentCenter(treatmentCenter);
            success = "Updated treatment center: " + treatmentCenter.getName();
            logger.info("Updated treatment center with id " + treatmentCenter.getId());
            modelAndView.addObject("success", success);
            modelAndView.setViewName("admin/adminResults");
        }

        return modelAndView;
    }

    @RequestMapping("/delete/")
    public ModelAndView deleteTreatmentCenter(@RequestParam("id") int id) {
        //treatmentCenterService.deleteTreatmentCenter(id);
        String success = "Deleted treatment center with id " + id;
        logger.info("Deleted treatment center with id " + id);
        modelAndView.addObject("success", success);
        modelAndView.setViewName("admin/adminResults");
        return modelAndView;
    }

    private String validateTreatmentCenter(TreatmentCenter treatmentCenter) {
        String error = "";
        if(treatmentCenter.getName().isEmpty()) {
            error = "Name field is blank!! Please enter a valid name.";
        } else if (treatmentCenter.getName().length() < 4) {
            error = "Name should be at least 4 characters.";
        } else if(treatmentCenter.getAddress().isEmpty()) {
            error = "Address field is blank!! Please enter a valid address.";
        } else if(treatmentCenter.getAddress().length() < 5){
            error = "Address should be at least 5 characters.";
        }else if(treatmentCenter.getCity().isEmpty()) {
            error = "City field is blank!! Please enter a valid city.";
        } else if(treatmentCenter.getCity().length() < 2) {
            error = "City should be at least 2 characters";
        } else if(!treatmentCenter.getCity().matches("[A-Z][A-Za-z ]+")) {
            error = "Please enter a valid city.";
        } else if(treatmentCenter.getState().isEmpty()) {
            error = "State field is blank!! Please enter a valid state.";
        } else if(treatmentCenter.getState().length() != 2) {
            error = "Please enter the 2 character abbreviation for state";
        } else if(!treatmentCenter.getState().matches("[A-Z][A-Z]")) {
            error = "Please enter capital letters for state";
        } else if(treatmentCenter.getZip().isEmpty()) {
            error = "Zip field is blank!! Please enter a valid zip code.";
        } else if(treatmentCenter.getZip().length() != 5) {
            error = "Please enter 5 digits for zip.";
        } else if(!treatmentCenter.getZip().matches("[0-9]+")) {
            error = "Please enter numbers for zip.";
        } else if(treatmentCenter.getPhoneNumber().isEmpty()) {
            error = "Phone number field is blank!! Please enter a valid phone number.";
        } else if(treatmentCenter.getPhoneNumber().length() != 10) {
            error = "Please enter 10 digits for phone number.";
        } else if(!treatmentCenter.getPhoneNumber().matches("[0-9]+")) {
            error = "Plese enter numbers for phone number.";
        }
        return error;
    }



}
