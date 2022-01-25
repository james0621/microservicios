package co.com.pragma.infrastructure.data.mapper;

import co.com.pragma.domain.Image;
import co.com.pragma.infrastructure.data.entity.ImageEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageDomainMapper {

    Image imageEntityToImage(ImageEntity imageEntity);

    List<Image> toImageList(List<ImageEntity> imageEntities);

    ImageEntity imageToImageEntity(Image image);

    List<ImageEntity> toImageEntityList( List<Image> images);

}
