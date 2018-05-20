package analyser.util;

import analyser.dto.PageData;
import analyser.entity.PageDataEntity;
import org.modelmapper.ModelMapper;

public final class EntityMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    private EntityMapper() {

    }

    public static PageDataEntity toEntity(PageData pageData) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(pageData, PageDataEntity.class);
    }


    public static PageData toDto(PageDataEntity pageDataEntity) {
        return modelMapper.map(pageDataEntity, PageData.class);
    }

}
