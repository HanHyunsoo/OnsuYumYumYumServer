package com.onsuyum.restaurant.domain.service;

import com.onsuyum.common.exception.MenuNotFoundException;
import com.onsuyum.restaurant.domain.model.Menu;
import com.onsuyum.restaurant.domain.model.Restaurant;
import com.onsuyum.restaurant.domain.repository.MenuRepository;
import com.onsuyum.restaurant.dto.request.MenuRequest;
import com.onsuyum.restaurant.dto.response.MenuResponse;
import com.onsuyum.storage.domain.model.ImageFile;
import com.onsuyum.storage.domain.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantService restaurantService;
    private final ImageFileService imageFileService;

    @Transactional
    public MenuResponse save(Long id, MenuRequest dto) {
        Restaurant restaurant = restaurantService.findEntityById(id);

        Menu menu = dtoToMenu(restaurant, dto);
        menu = menuRepository.save(menu);

        return menu.toResponseDTO();
    }

    @Transactional
    public List<MenuResponse> saveAll(Long id, List<MenuRequest> dtos) {
        return saveAllWithRequest(id, dtos, false);
    }

    @Transactional
    public List<MenuResponse> saveAllWithRequest(Long id, List<MenuRequest> dtos, boolean checkRequest) {
        Restaurant restaurant = restaurantService.findEntityById(id);

        if (checkRequest) {
            restaurantService.validTime(restaurant);
            restaurantService.validIsRequest(restaurant);
        }

        List<Menu> menus = dtos.stream()
                .map(dto -> dtoToMenu(restaurant, dto))
                .collect(Collectors.toList());

        menus = (List<Menu>) menuRepository.saveAll(menus);

        return menus.stream()
                .map(Menu::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAllByRestaurantId(Long id) {
        return findAllByRestaurantIdWithRequest(id, false);
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAllByRestaurantIdWithRequest(Long id, boolean checkRequest) {
        Restaurant restaurant = restaurantService.findEntityById(id);

        if (checkRequest) {
            restaurantService.validIsRequest(restaurant);
        }

        List<Menu> menus = menuRepository.findAllByRestaurant(restaurant);

        return menus.stream()
                .map(Menu::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MenuResponse findById(Long id) {
        return findEntityById(id).toResponseDTO();
    }

    @Transactional
    public MenuResponse update(Long id, MenuRequest dto) {
        Menu menu = findEntityById(id);
        ImageFile menuImage = menu.getMenuImage();
        if (dto.getMenuImage() != null) {
            imageFileService.delete(menuImage.getId());
            menuImage = imageFileService.save(dto.getMenuImage());
        }

        menu.update(
                dto.getName(),
                dto.getPrice(),
                dto.getDescription(),
                menuImage
        );

        menu = menuRepository.save(menu);

        return menu.toResponseDTO();
    }

    @Transactional
    public List<MenuResponse> updateAll(List<MenuRequest> dtos) {
        return dtos.stream()
                .map(dto -> update(dto.getId(), dto))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        Menu menu = findEntityById(id);

        menuRepository.delete(menu);
    }

    @Transactional
    public void deleteAllByIds(List<Long> ids) {
        List<Menu> menus = ids.stream()
                .map(this::findEntityById)
                .collect(Collectors.toList());

        menuRepository.deleteAll(menus);
    }

    @Transactional
    public void deleteAllByRestaurantId(Long id) {
        Restaurant restaurant = restaurantService.findEntityById(id);
        menuRepository.deleteAllByRestaurant(restaurant);
    }

    private Menu dtoToMenu(Restaurant restaurant, MenuRequest dto) {
        ImageFile menuImage = null;
        if (dto.getMenuImage() != null) {
            menuImage = imageFileService.save(dto.getMenuImage());
        }

        return Menu.builder()
                .restaurant(restaurant)
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .menuImage(menuImage)
                .build();
    }

    // Service Layer 내에서 사용 가능한 메서드

    Menu findEntityById(Long id) {
        return menuRepository.findById(id).orElseThrow(MenuNotFoundException::new);
    }
}
