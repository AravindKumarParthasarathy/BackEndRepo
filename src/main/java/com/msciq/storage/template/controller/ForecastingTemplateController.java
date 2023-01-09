package com.msciq.storage.template.controller;

import com.msciq.storage.common.Constants;
import com.msciq.storage.common.SuccessMessage;
import com.msciq.storage.model.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.msciq.storage.model.ForecastingTemplate;
import com.msciq.storage.template.service.ForecastingTemplateService;

import java.util.List;

@RestController
@RequestMapping(value = "/template")
@Validated
public class ForecastingTemplateController {

    @Autowired
    ForecastingTemplateService forecastingTemplateService;

    /**
     * Gets all forecasting templates.
     *
     * @return List of ForecastingTemplate entity
     * @author Sivaranjani DS
     */
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('Fun_Forecast_Template:READ') or hasAuthority('Key_Forecast_Template:READ')")
    public SuccessResponse<List<ForecastingTemplate>> getAllForecastingTemplates() {
        List<ForecastingTemplate> forecastingTemplates = forecastingTemplateService.getAllForecastingTemplates();
        return new SuccessResponse<List<ForecastingTemplate>>(String.format(SuccessMessage.SUCCESS, Constants.TEMPLATE)
                , forecastingTemplates, null, HttpStatus.OK);
    }

    /**
     * This method is used to add a new Template.
     *
     * @param templates
     * @return ForecastingTemplate Entity.
     * @author Sivaranjani DS
     */
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('Fun_Forecast_Template:CREATE') or hasAuthority('Key_Forecast_Template:CREATE')")
    public SuccessResponse<List<ForecastingTemplate>> addForecastingTemplates(@RequestBody List<ForecastingTemplate> templates) {
        return new SuccessResponse<List<ForecastingTemplate>>
                (String.format(SuccessMessage.SUCCESSFULLY_SAVED, Constants.TEMPLATE),
                        forecastingTemplateService.addForecastingTemplate(templates),
                        null,
                        HttpStatus.CREATED);
    }

}
