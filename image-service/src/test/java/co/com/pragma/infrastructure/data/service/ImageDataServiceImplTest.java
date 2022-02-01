package co.com.pragma.infrastructure.data.service;

import static org.mockito.Mockito.*;

import co.com.pragma.application.service.ImageService;
import co.com.pragma.domain.Image;
import co.com.pragma.infrastructure.data.entity.ImageEntity;
import co.com.pragma.infrastructure.data.mapper.ImageDomainMapper;
import co.com.pragma.infrastructure.data.repository.ImageDataRepository;
import co.com.pragma.infrastructure.exception.DataNoFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ImageDataServiceImplTest {

    @InjectMocks
    private ImageDataServiceImpl imageService;

    @Mock
    private ImageDataRepository imageRepo;

    @Mock
    private ImageDomainMapper mapper;

    @Mock
    private Image image;

    private static final String ID = "1";

    @BeforeEach
    void setUp() {
       MockitoAnnotations.openMocks(this);
    }

    @Test
    void find_all_test() {
        imageService.findAll();

        verify(imageRepo).findAll();
    }

    @Test
    void find_by_id_test_when_image_is_not_present() {
       assertThrows(DataNoFoundException.class, ()-> {
          imageService.findById(ID);
       });
    }

    @Test
    void save_test() {
        when(imageService.save(image)).thenReturn(image);

        verify(imageRepo).save(mapper.imageToImageEntity(image));
    }

    @Test
    void delete_test_when_image_is_not_present() {
        assertThrows(DataNoFoundException.class, () -> {
            imageService.delete(ID);
        });
    }
}