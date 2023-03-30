package com.example.registration.controllers;

import com.example.registration.dto.HousingDTO;
import com.example.registration.service.HousingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HousingControllerTest {


    @InjectMocks
    private HousingController housingController;

    @Mock
    private HousingService housingService;

    @Test
    void testCreateHousing() throws IOException {

        HousingDTO housingDTO = new HousingDTO();
        housingDTO.setTitle("Test Housing");
        housingDTO.setDescription("Test Description");
        housingDTO.setPrice(BigDecimal.valueOf(1000.0));
        List<MultipartFile> files = new ArrayList<>();
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test File".getBytes());
        files.add(mockFile);
        when(housingService.createHousing(any(HousingDTO.class), any(MultipartFile[].class)))
                .thenReturn(housingDTO);

        ResponseEntity<HousingDTO> responseEntity = housingController.createHousing(housingDTO, files.toArray(new MultipartFile[0]));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(housingDTO, responseEntity.getBody());
    }


    @Test
    void testGetAllHousing() {

        List<HousingDTO> housingList = new ArrayList<>();
        HousingDTO housing1 = new HousingDTO();
        housing1.setTitle("Housing 1");
        housingList.add(housing1);
        HousingDTO housing2 = new HousingDTO();
        housing2.setTitle("Housing 2");
        housingList.add(housing2);
        when(housingService.getAllHousing()).thenReturn(housingList);

        List<HousingDTO> responseList = housingController.getAllHousing();

        assertEquals(housingList.size(), responseList.size());
        assertEquals(housing1.getTitle(), responseList.get(0).getTitle());
        assertEquals(housing2.getTitle(), responseList.get(1).getTitle());
    }

    @Test
    void testUpdateHousing() throws IOException {

        Long id = 1L;
        HousingDTO housingDTO = new HousingDTO();
        housingDTO.setId(id);
        MultipartFile[] files = new MultipartFile[0];
        HousingDTO updatedHousing = new HousingDTO();
        updatedHousing.setId(id);
        updatedHousing.setTitle("Updated Housing");
        when(housingService.updateHousing(id, housingDTO, files)).thenReturn(updatedHousing);

        HousingDTO response = housingController.updateHousing(id, housingDTO, files);

        assertEquals(updatedHousing.getId(), response.getId());
        assertEquals(updatedHousing.getTitle(), response.getTitle());
    }

    @Test
    public void testDeleteHousingById() {
        Long id = 1L;

        // mock the housing service to do nothing when deleteHousing method is called
        doNothing().when(housingService).deleteHousingById(id);

        // call the deleteHousing method on the controller and check the response
        ResponseEntity<String> response = housingController.deleteHousingById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Housing " + id + " delete successfully!", response.getBody());

        // verify that the deleteHousing method was called on the service
        verify(housingService).deleteHousingById(id);
    }

    @Test
    void testGetHousingById() {
        Long id = 1L;
        HousingDTO expectedHousing = new HousingDTO();
        expectedHousing.setId(id);
        when(housingService.getHousingById(id)).thenReturn(expectedHousing);

        HousingDTO actualHousing = housingController.getHousingById(id);

        assertThat(actualHousing).isNotNull();
        assertThat(actualHousing).isEqualTo(expectedHousing);
        assertEquals(expectedHousing, actualHousing);
    }

    @Test
    void testGetImageByHousingId() {
        // Создаем HousingDTO и PhotoDTO
        Housing housing = new Housing();
        housing.setId(1L);

        ImageDTO photo1 = new ImageDTO();
        photo1.setId(1L);
        photo1.setHousing(housing);

        ImageDTO photo2 = new ImageDTO();
        photo2.setId(2L);
        photo2.setHousing(housing);

        List<ImageDTO> photos = Arrays.asList(photo1, photo2);

        // Указываем, что HousingService.getPhotosByHousingId() должен возвращать список фотографий
        when(housingService.getImagesByHousingId(1L)).thenReturn(photos);

        // Выполняем метод контроллера и проверяем результат
        List<ImageDTO> result = housingController.getImagesByHousingId(1L);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(1L, result.get(0).getHousing().getId());
        assertEquals(2L, result.get(1).getId());
        assertEquals(1L, result.get(1).getHousing().getId());
    }

    @Test
    void testGetImageByIdFromHousingId() {
        // создаем фиктивный объект HousingDTO и PhotoDTO
        HousingDTO housingDTO = new HousingDTO();
        housingDTO.setId(1L);
        List<ImageDTO> images = new ArrayList<>();
        ImageDTO photoDTO = new ImageDTO();
        photoDTO.setId(1L);
        images.add(photoDTO);
        housingDTO.setImages(images);

        // настроить возвращаемое значение сервиса
        when(housingService.getHousingById(1L)).thenReturn(housingDTO);

        // вызов контроллера
        ResponseEntity<ImageDTO> response = housingController.getImageByIdFromHousingId(1L, 1L);

        // проверяем статус ответа
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // проверяем, что вернулся правильный объект PhotoDTO
        ImageDTO actualImageDTO = response.getBody();
        assertEquals(photoDTO.getId(), actualImageDTO.getId());
    }

    @Test
    void testGetImageByIdNotFound() {
        // Указываем, что HousingService.getHousingById() должен возвращать null
        when(housingService.getHousingById(1L)).thenReturn(null);

        // Выполняем метод контроллера и проверяем, что NullPointerException было выброшено
        assertThrows(NullPointerException.class, () -> {
            housingController.getImageByIdFromHousingId(1L, null);
        });
    }

    @Test
    void testGetImageByIdNotFoundInHousing() {
        // Создаем HousingDTO без фотографий
        HousingDTO housing = new HousingDTO();
        housing.setId(1L);

        // Указываем, что HousingService.getHousingById() должен возвращать HousingDTO без фотографий
        when(housingService.getHousingById(1L)).thenReturn(housing);

        // Выполняем метод контроллера и проверяем, что NullPointerException было выброшено
        assertThrows(NullPointerException.class, () -> {
            housingController.getImageByIdFromHousingId(1L, 2L);
        });
    }

    @Test
    void testDeleteImageByIdFromHousingId() {
        Long housingId = 1L;
        Long imageId = 2L;

        // Mock housingService.deleteImageByIdFromHousingId() method
        doNothing().when(housingService).deleteImageByIdFromHousingId(housingId, imageId);

        // Call the deletePhoto() method of HousingController
        ResponseEntity<Void> responseEntity = housingController.deleteImageByIdFromHousingId(housingId, imageId);

        // Verify that the deleteImageByIdFromHousingId() method of HousingService is called once
        verify(housingService, times(1)).deleteImageByIdFromHousingId(housingId, imageId);

        // Verify the response status code
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}