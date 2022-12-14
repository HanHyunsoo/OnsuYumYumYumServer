package com.onsuyum.babful.domain.service;

import com.onsuyum.babful.domain.model.BabfulMenu;
import com.onsuyum.babful.domain.repository.BabfulMenuRepository;
import com.onsuyum.babful.dto.request.BabfulMenuRequest;
import com.onsuyum.babful.dto.response.BabfulMenuResponse;
import com.onsuyum.common.exception.BabfulMenuDateAlreadyExistsException;
import com.onsuyum.common.exception.BabfulMenuNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        babfulMenuRepository.save(babfulMenu);

        return babfulMenu.toResponseDTO();
    }

    @Transactional
    public List<BabfulMenuResponse> saveAll(List<BabfulMenuRequest> requests) {
        List<BabfulMenu> babfulMenus = new ArrayList<>();

        requests.forEach(request -> {
            validMenuDate(request.getMenuDate());

            BabfulMenu babfulMenu = BabfulMenu.builder()
                    .menuDate(request.getMenuDate())
                    .foods(request.getFoods())
                    .deliciousFood(request.getDeliciousFood())
                    .build();

            babfulMenus.add(babfulMenu);
        });

        babfulMenuRepository.saveAll(babfulMenus);

        return babfulMenus.stream().map(BabfulMenu::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<BabfulMenuResponse> findAll(Pageable pageable) {
        Page<BabfulMenu> babfulMenuPage = babfulMenuRepository.findAll(pageable);

        return babfulMenuPage.map(BabfulMenu::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<BabfulMenuResponse> findAll(Pageable pageable, boolean isOldData) {
        LocalDate nowDate = LocalDate.now();

        Page<BabfulMenu> babfulMenuPage;
        if (isOldData) {
            babfulMenuPage = babfulMenuRepository.findAllByMenuDateLessThan(pageable, nowDate);
        } else {
            babfulMenuPage = babfulMenuRepository.findAllByMenuDateGreaterThanEqual(pageable,
                    nowDate);
        }

        return babfulMenuPage.map(BabfulMenu::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public BabfulMenuResponse findById(Long id) {

        BabfulMenu babfulMenu = findEntityById(id);

        return babfulMenu.toResponseDTO();
    }

    @Transactional
    public BabfulMenuResponse updateById(Long id, BabfulMenuRequest request) {
        validMenuDate(request.getMenuDate());

        BabfulMenu babfulMenu = findEntityById(id);

        babfulMenu.update(request.getMenuDate(), request.getFoods(), request.getDeliciousFood());

        return babfulMenuRepository.save(babfulMenu)
                .toResponseDTO();
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
            throw new BabfulMenuDateAlreadyExistsException(date);
        }
    }
}
