package com.msciq.storage.template.service.Impl;

import com.msciq.storage.model.ForecastingTemplate;
import com.msciq.storage.template.repository.ForecastingTemplateRepository;
import com.msciq.storage.template.service.ForecastingTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastingTemplateServiceImpl implements ForecastingTemplateService {

    @Autowired
    private ForecastingTemplateRepository forecastingTemplateRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForecastingTemplate> getAllForecastingTemplates() {
        return forecastingTemplateRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForecastingTemplate> addForecastingTemplate(List<ForecastingTemplate> templates) {
        return forecastingTemplateRepository.saveAll(templates);
    }
}
