package com.movie.services.impl;

import com.movie.models.Rate;
import com.movie.models.keys.RateId;
import com.movie.repositories.RateRepository;
import com.movie.services.RateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;
    public RateServiceImpl(RateRepository rateRepository) {
        this.rateRepository=rateRepository;
    }
    @Override
    public Rate saveRate(Rate rate) {
        return rateRepository.save(rate);
    }

    @Override
    public List<Rate> getAllRates() {
        return rateRepository.findAll();
    }

    @Override
    public Optional<Rate> getRateById(RateId id) {
        return rateRepository.findById(id);
    }

    @Override
    public Rate updateRate(Rate updateRate) {
        return rateRepository.save(updateRate);
    }

    @Override
    public void deleteRate(RateId id) {
        rateRepository.deleteById(id);
    }
}
