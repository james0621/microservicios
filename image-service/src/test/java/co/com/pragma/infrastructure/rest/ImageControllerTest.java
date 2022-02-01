package co.com.pragma.infrastructure.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import co.com.pragma.application.service.ImageService;
import co.com.pragma.domain.Image;
import co.com.pragma.infrastructure.rest.dto.ImageDto;
import co.com.pragma.infrastructure.rest.mapper.ImageMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class ImageControllerTest {


    @Mock
    private ImageService imageService;

    @Mock
    private ImageMapper mapper;

    @InjectMocks
    private ImageController imageController;

    @Mock
    private ImageDto imageDto;

    @Mock
    private Image image;

    private static final String ID = "1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test para consultar todas las imagenes")
    void find_all_test() {
        List<ImageDto> imagesDto = new ArrayList<>();
        imagesDto.add(imageDto);
        List<Image> images = new ArrayList<>();
        images.add(image);

        when(imageService.findAll()).thenReturn(images);
        when(mapper.toImageDtoList(images)).thenReturn(imagesDto);

        assertEquals(imageController.findAll().getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Test para buscar Imagen por ID")
    void findByIdTest() {
        when(imageService.findById(ID)).thenReturn(image);
        when(mapper.imageToImageDto(image)).thenReturn(imageDto);

        assertEquals(imageController.findById(ID).getBody(), imageDto);
    }

    @Test
    @DisplayName("Test para guardar una imagen")
    void save_test() throws IOException {
        when(imageService.save(image)).thenReturn(image);
        when(mapper.imageToImageDto(image)).thenReturn(imageDto);

        assertEquals(imageController.save(imageDto).getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    @DisplayName("Test para eliminar una imagen que existe")
    void delete_test_when_image_is_present() {
        when(imageService.findById(ID)).thenReturn(image);

        assertEquals(imageController.delete(ID).getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Test para eliminar una imagen que no existe")
    void delete_test_when_image_is_not_present() {
        when(imageService.findById(ID)).thenReturn(null);

        assertEquals(imageController.delete(ID).getStatusCode(),HttpStatus.BAD_REQUEST);
    }
}