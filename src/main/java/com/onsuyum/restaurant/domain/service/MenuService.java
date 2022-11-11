package com.onsuyum.restaurant.domain.service;

import com.onsuyum.common.exception.MenuImageAlreadyExistsException;
import com.onsuyum.common.exception.MenuImageNotExistsException;
import com.onsuyum.common.exception.MenuNotFoundException;
import com.onsuyum.restaurant.domain.model.Menu;
import com.onsuyum.restaurant.domain.model.Restaurant;
import com.onsuyum.restaurant.domain.repository.MenuRepository;
import com.onsuyum.restaurant.dto.request.JsonMenuRequest;
import com.onsuyum.restaurant.dto.request.MultipartMenuRequest;
import com.onsuyum.restaurant.dto.response.MenuResponse;
import com.onsuyum.restaurant.dto.response.RestaurantMenuResponse;
import com.onsuyum.storage.domain.model.ImageFile;
import com.onsuyum.storage.domain.service.ImageFileService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantService restaurantService;
    private final ImageFileService imageFileService;

    @Transactional
    public MenuResponse save(Long id, MultipartMenuRequest dto) {
        Restaurant restaurant = restaurantService.findEntityById(id);

        Menu menu = dtoToMenu(restaurant, dto);
        menu = menuRepository.save(menu);

        return menu.toResponseDTO();
    }

    @Transactional
    public MenuResponse saveMenuImageById(Long id, MultipartFile requestImageFile) {
        Menu menu = findEntityById(id);
        ImageFile menuImage = menu.getMenuImage();

        if (menuImage != null) {
            throw new MenuImageAlreadyExistsException();
        }

        ImageFile newMenuImage = imageFileService.save(requestImageFile);
        menu.updateImage(newMenuImage);

        return menuRepository.save(menu)
                             .toResponseDTO();
    }

    @Transactional
    public List<MenuResponse> saveAll(Long id, List<MultipartMenuRequest> dtos) {
        return saveAllWithRequest(id, dtos, false);
    }

    @Transactional
    public List<MenuResponse> saveAllWithRequest(Long id, List<MultipartMenuRequest> dtos,
            boolean checkRequest) {
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
    public Page<RestaurantMenuResponse> findAllWithRequest(
            Pageable pageable,
            boolean isRequest,
            Integer price) {
        Page<Menu> menus = menuRepository.findAllByRequestAndPriceGreaterThanEqual(pageable,
                isRequest, price);

        return menus.map(Menu::toResponseWithRestaurantDTO);
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
    public MenuResponse update(Long id, JsonMenuRequest dto) {
        Menu menu = findEntityById(id);

        menu.update(
                dto.getName(),
                dto.getPrice(),
                dto.getDescription()
        );

        menu = menuRepository.save(menu);

        return menu.toResponseDTO();
    }

//    @Transactional
//    public List<MenuResponse> updateAll(List<MenuRequest> dtos) {
//        return dtos.stream()
//                .map(dto -> update(dto.getId(), dto))
//                .collect(Collectors.toList());
//    }

    @Transactional
    public void deleteById(Long id) {
        Menu menu = findEntityById(id);
        imageFileService.delete(menu.getMenuImage()
                                    .getId());

        menuRepository.delete(menu);
    }

    @Transactional
    public void deleteMenuImageById(Long id) {
        Menu menu = findEntityById(id);
        if (menu.getMenuImage() == null) {
            throw new MenuImageNotExistsException();
        }

        imageFileService.delete(menu.getMenuImage()
                                    .getId());
        menu.updateImage(null);
    }

    @Transactional
    public void deleteAllByIds(List<Long> ids) {
        List<Menu> menus = ids.stream()
                              .map(menuId -> {
                                  Menu menu = findEntityById(menuId);
                                  imageFileService.delete(menu.getMenuImage()
                                                              .getId());
                                  menu.updateImage(null);
                                  return menu;
                              })
                              .collect(Collectors.toList());

        menuRepository.deleteAll(menus);
    }

    @Transactional
    public void deleteAllByRestaurantId(Long id) {
        Restaurant restaurant = restaurantService.findEntityById(id);
        List<Menu> menus = restaurant.getMenu();

        menus.forEach(menu -> imageFileService.delete(menu.getMenuImage()
                                                          .getId()));

        menuRepository.deleteAll(menus);
    }

    private Menu dtoToMenu(Restaurant restaurant, MultipartMenuRequest dto) {
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
        return menuRepository.findById(id)
                             .orElseThrow(MenuNotFoundException::new);
    }
}
