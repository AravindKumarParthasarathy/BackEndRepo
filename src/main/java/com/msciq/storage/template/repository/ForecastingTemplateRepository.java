package com.msciq.storage.template.repository;

import com.msciq.storage.model.ForecastingTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForecastingTemplateRepository extends JpaRepository<ForecastingTemplate, Long> {

    public static final String GET_ALL_FORECASTING_TEMPLATES = " select templates from ForecastingTemplate templates";

    @Query(value = GET_ALL_FORECASTING_TEMPLATES)
    List<ForecastingTemplate> getAllForecastingTemplates();

}
