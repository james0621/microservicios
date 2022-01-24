package co.com.pragma.mapper;

import co.com.pragma.dto.ImageDto;
import co.com.pragma.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    ImageDto imageToImageDto(Image image);

    Image imageDtoToImage(ImageDto imageDto);
}
