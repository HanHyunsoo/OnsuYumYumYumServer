package com.onsuyum;

import com.onsuyum.restaurant.domain.service.MenuService;
import com.onsuyum.restaurant.domain.service.RestaurantCategoryService;
import com.onsuyum.restaurant.domain.service.RestaurantService;
import com.onsuyum.restaurant.dto.request.MenuRequest;
import com.onsuyum.restaurant.dto.request.RestaurantRequest;
import com.onsuyum.restaurant.dto.response.MenuResponse;
import com.onsuyum.restaurant.dto.response.RestaurantResponse;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("development")
public class DummyDataTests {
    @Autowired
    private MenuService menuService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantCategoryService restaurantCategoryService;

    @Value("${dummy-data-json-path}")
    private String dummyDataJsonPath;

    @Value("${dummy-data-images-path}")
    private String dummyDataImagesPath;

    @Value("${alt-image-path}")
    private String altImagePath;

    private BufferedImage bufferedAltImage;

    @BeforeEach
    void setUp() throws IOException {
        bufferedAltImage = ImageIO.read(new File(altImagePath));
    }

    @Test
    void insertDummyData() throws IOException, ParseException {
        FileReader fileReader = new FileReader(dummyDataJsonPath);
        JSONParser jsonParser = new JSONParser(fileReader);
        List<Object> list = jsonParser.list();

        for (Object object : list) {
            Map<String, Object> map = (Map<String, Object>) object;

            RestaurantRequest restaurantRequest = toRestaurantRequest(map);
            Set<String> categoryNames = new HashSet<>((List<String>) map.get("category"));
            List<MenuRequest> menuRequestList = toMenuRequestList((List<Map<String, Object>>) map.get("menu"));

            RestaurantResponse restaurantResponse = restaurantService.save(restaurantRequest, false);
            Map<String, Object> responseMap = restaurantCategoryService.saveAllRestaurantCategory(restaurantResponse.getId(), categoryNames);
            List<MenuResponse> menuResponses = menuService.saveAll(restaurantResponse.getId(), menuRequestList);
        }

        clearDummyDataImageFiles();
    }

    private void clearDummyDataImageFiles() {
        File rootDir = new File(dummyDataImagesPath);

        try {
            FileUtils.cleanDirectory(rootDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File toFileFromUrl(String urlString) {
        File file = null;
        try {
            URL url = new URL(urlString);

            String extension = urlString.substring(urlString.lastIndexOf('.') + 1);
            String fileName = urlString.substring(urlString.lastIndexOf('/'));
            fileName = fileName.split("\\.")[0];

            if (extension.contains("?type=")) {
                extension = "jpeg";
                fileName = UUID.randomUUID().toString();
            }

            BufferedImage bufferedImage = ImageIO.read(url);
            if (isAltImage(bufferedImage, extension)) return null;
            file = new File(dummyDataImagesPath + fileName + "." + extension);
            ImageIO.write(bufferedImage, extension, file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    private MultipartFile toMultipartFileByFile(File file) {
        if (file == null) return null;

        MultipartFile multipartFile = null;

        try (InputStream inputStream = new FileInputStream(file)) {
            multipartFile = new MockMultipartFile("file", file.getName(), Files.probeContentType(file.toPath()), inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return multipartFile;
    }

    private RestaurantRequest toRestaurantRequest(Map<String, Object> map) {
        return RestaurantRequest.builder()
                .name((String) map.get("name"))
                .phone((String) map.get("phone"))
                .time(validTimeList((List<String>) map.get("time")))
                .summary((String) map.get("summary"))
                .location((String) map.get("location"))
                .outsideImage(toMultipartFileByFile(toFileFromUrl((String) map.get("menuImg"))))
                .insideImage(toMultipartFileByFile(toFileFromUrl((String) map.get("locationImg"))))
                .build();

    }

    private List<MenuRequest> toMenuRequestList(List<Map<String, Object>> maps) {
        return maps.stream()
                .map(map -> MenuRequest.builder()
                        .name((String) map.get("name"))
                        .price(convertInteger((String) map.get("price")))
                        .menuImage(toMultipartFileByFile(toFileFromUrl((String) map.get("menuImg"))))
                        .build()
                )
                .collect(Collectors.toList());
    }

    private Integer convertInteger(String priceString) {
        Integer result;
        try {
            result = Integer.parseInt(priceString.split("원")[0]);
        } catch (NumberFormatException e) {
            result = null;
        }
        return result;
    }

    private List<String> validTimeList(List<String> times) {
        if (times.size() == 1 && "정보 없음".equals(times.get(0))) {
            return List.of();
        }

        return times;
    }

    private boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y))
                        return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean isAltImage(BufferedImage image, String extension) {
        return extension.equals("png") && bufferedImagesEqual(bufferedAltImage, image);
    }
}
