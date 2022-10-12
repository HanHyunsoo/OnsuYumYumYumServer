package com.onsuyum.babful.domain.service;

import com.onsuyum.babful.domain.model.BabfulMenu;
import com.onsuyum.babful.domain.repository.BabfulMenuRepository;
import com.onsuyum.babful.dto.request.BabfulMenuRequest;
import com.onsuyum.babful.dto.response.BabfulMenuResponse;
import com.onsuyum.common.exception.BabfulMenuDateAlreadyExistsException;
import com.onsuyum.common.exception.BabfulMenuNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BabfulMenuService {

    private final BabfulMenuRepository babfulMenuRepository;

    @Transactional
    public BabfulMenuResponse save(BabfulMenuRequest request) {
        validMenuDate(request.getMenuDate());

        BabfulMenu babfulMenu = BabfulMenu.builder()
                .menuDate(request.getMenuDate())
                .foods(request.getFoods())
                .deliciousFood(request.getDeliciousFood())
                .build();

        return babfulMenu.toResponseDTO();
    }

    @Transactional(readOnly = true)
    public Page<BabfulMenuResponse> findAll(Pageable pageable, boolean isOldData) {
        LocalDate nowDate = LocalDate.now();

        Page<BabfulMenu> babfulMenuPage;
        if (isOldData) {
            babfulMenuPage = babfulMenuRepository.findAllByMenuDateLessThan(pageable, nowDate);
        } else {
            babfulMenuPage = babfulMenuRepository.findAllByMenuDateGreaterThanEqual(pageable, nowDate);
        }

        return babfulMenuPage.map(BabfulMenu::toResponseDTO);
    }

    @Transactional
    public BabfulMenuResponse updateById(Long id, BabfulMenuRequest request) {
        BabfulMenu babfulMenu = findEntityById(id);

        babfulMenu.update(request.getMenuDate(), request.getFoods(), request.getDeliciousFood());

        return babfulMenuRepository.save(babfulMenu).toResponseDTO();
    }

    @Transactional
    public void deleteById(Long id) {
        BabfulMenu babfulMenu = findEntityById(id);
        babfulMenuRepository.delete(babfulMenu);
    }

    @Transactional(readOnly = true)
    BabfulMenu findEntityById(Long id) {
        return babfulMenuRepository.findById(id)
                .orElseThrow(BabfulMenuNotFoundException::new);
    }

    private void validMenuDate(LocalDate date) {
        if (babfulMenuRepository.existsByMenuDate(date)) {
            throw new BabfulMenuDateAlreadyExistsException();
        }
    }
}
