package co.com.pragma.infrastructure.rest.mapper;

import co.com.pragma.domain.Image;
import co.com.pragma.infrastructure.rest.dto.ImageDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    ImageDto imageToImageDto(Image image);

    List<ImageDto> toImageDtoList(List<Image> images);

    Image imageDtoToImage(ImageDto imageDto);

    List<Image> toImageList(List<ImageDto> imageDtos);
}
