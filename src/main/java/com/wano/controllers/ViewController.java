package com.wano.controllers;

import com.wano.models.TreatmentCenter;
import com.wano.services.TreatmentCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by 600158489 on 3/19/2018.
 */
@Controller
public class ViewController {

    ModelAndView modelAndView = new ModelAndView();

    @Autowired
    TreatmentCenterService treatmentCenterService;

    @RequestMapping("/")
    public ModelAndView loadHomePage() {
       //modelAndView.setViewName("index");
        modelAndView.setViewName("/admin/adminIndex");
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView loginAdminUser() {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/admin")
    public ModelAndView loadAdminPage() {
        modelAndView.setViewName("/admin/adminIndex");
        return modelAndView;
    }

    @RequestMapping("/modify")
    public ModelAndView loadModifyTreatmentCenterPage() {
        modelAndView.setViewName("/admin/modifyTreatmentCenter");
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView loadAddTreatmentCenterPage() {
        modelAndView.setViewName("/admin/addTreatmentCenter");
        return modelAndView;
    }

    @RequestMapping("/update/{id}")
    public ModelAndView loadUpdateTreatmentCenterPage(@PathVariable("id") int id) {
        TreatmentCenter treatmentCenter = treatmentCenterService.getTreatmentCenter(id);
        modelAndView.addObject(treatmentCenter);
        modelAndView.setViewName("/admin/updateTreatmentCenter");
        return modelAndView;
    }
}
