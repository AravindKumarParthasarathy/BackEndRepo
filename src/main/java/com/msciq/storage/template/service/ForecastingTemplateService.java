package com.msciq.storage.template.service;

import com.msciq.storage.model.ForecastingTemplate;

import java.util.List;

public interface ForecastingTemplateService {

    /**
     * This method returns list of all forecasting templates
     *
     * @return List of ForecastingTemplate Entity
     * @author Sivaranjani DS
     */
    public List<ForecastingTemplate> getAllForecastingTemplates();

    /**
     * This method adds list of template
     *
     * @param templates
     * @return ForecastingTemplate Entity
     * @author Sivaranjani DS
     */
    public List<ForecastingTemplate> addForecastingTemplate(List<ForecastingTemplate> templates);

}
