package com.devshowcase.service;

import com.devshowcase.dto.TechnologyRequest;
import com.devshowcase.dto.TechnologyResponse;
import com.devshowcase.exception.BusinessException;
import com.devshowcase.model.Technology;
import com.devshowcase.repository.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologyService {
    private final TechnologyRepository technologyRepository;

    @Transactional
    public TechnologyResponse create(TechnologyRequest request) {
        String name = request.name().trim();
        if (technologyRepository.existsByNameIgnoreCase(name)) {
            throw new BusinessException("Esta tecnologia já foi cadastrada");
        }
        return toResponse(technologyRepository.save(Technology.builder().name(name).build()));
    }

    @Transactional(readOnly = true)
    public List<TechnologyResponse> findAll() {
        return technologyRepository.findAll().stream().map(this::toResponse).toList();
    }

    private TechnologyResponse toResponse(Technology technology) {
        return new TechnologyResponse(technology.getId(), technology.getName());
    }
}
