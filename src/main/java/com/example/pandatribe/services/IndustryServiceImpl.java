package com.example.pandatribe.services;

import com.example.pandatribe.feign.contracts.EveInteractor;
import com.example.pandatribe.models.industry.SystemCostIndexes;
import com.example.pandatribe.services.contracts.IndustryService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IndustryServiceImpl implements IndustryService {
    private final EveInteractor eveInteractor;
    @Override
    @Cacheable("cacheCostIndexes")
    public List<SystemCostIndexes> getSystemCostIndexes() {
      return eveInteractor.getSystemCostIndexes();
    }
}
