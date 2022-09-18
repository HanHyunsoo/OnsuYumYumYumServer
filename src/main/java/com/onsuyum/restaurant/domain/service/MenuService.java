package com.onsuyum.restaurant.domain.service;

import com.onsuyum.restaurant.domain.model.Menu;
import com.onsuyum.restaurant.domain.model.Restaurant;
import com.onsuyum.restaurant.domain.repository.MenuRepository;
import com.onsuyum.restaurant.dto.request.MenuRequest;
import com.onsuyum.storage.domain.model.ImageFile;
import com.onsuyum.storage.domain.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantService restaurantService;
    private final ImageFileService imageFileService;

    @Transactional
    public Menu save(Restaurant restaurant, MenuRequest dto) {
        Menu menu = dtoToMenu(restaurant, dto);

        return menuRepository.save(menu);
    }

    @Transactional
    public List<Menu> saveAll(Restaurant restaurant, List<MenuRequest> dtos) {
        List<Menu> menus = dtos.stream()
                .map(dto -> dtoToMenu(restaurant, dto))
                .collect(Collectors.toList());

        return (List<Menu>) menuRepository.saveAll(menus);
    }

    @Transactional
    public List<Menu> saveAllWithRequest(Restaurant restaurant, List<MenuRequest> dtos) {
        if (!restaurant.isRequest()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이 음식점에 대해 권한이 없습니다.");
        }

        LocalDateTime modifiedDate = restaurant.getModifiedDate();
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(modifiedDate, now);
        if (duration.getSeconds() > 300) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "음식점에 대해 접근할 수 있는 시간(5분)이 지났습니다.");
        }

        return saveAll(restaurant, dtos);
    }

    @Transactional(readOnly = true)
    public List<Menu> findAllByRestaurantId(Long id) {
        Restaurant restaurant = restaurantService.findById(id);
        return menuRepository.findAllByRestaurant(restaurant);
    }

    @Transactional(readOnly = true)
    public Menu findById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("메뉴(pk = %d) 정보가 DB에 존재하지 않습니다.", id)
                        )
                );
    }

    @Transactional
    public Menu update(Long id, MenuRequest dto) {
        Menu menu = findById(id);
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

        return menuRepository.save(menu);
    }

    @Transactional
    public List<Menu> updateAll(List<MenuRequest> dtos) {
        return dtos.stream()
                .map(dto -> update(dto.getId(), dto))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        Menu menu = findById(id);

        menuRepository.delete(menu);
    }

    @Transactional
    public void deleteAllByIds(List<Long> ids) {
        List<Menu> menus = ids.stream()
                .map(this::findById)
                .collect(Collectors.toList());

        menuRepository.deleteAll(menus);
    }

    @Transactional
    public void deleteAllByRestaurantId(Long id) {
        Restaurant restaurant = restaurantService.findById(id);
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
}
