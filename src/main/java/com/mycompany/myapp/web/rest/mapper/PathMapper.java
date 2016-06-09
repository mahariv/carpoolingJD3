package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.PathDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Path and its DTO PathDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PathMapper {

    @Mapping(source = "locationStart.id", target = "locationStartId")
    @Mapping(source = "locationStart.nameLocation", target = "locationStartNameLocation")
    @Mapping(source = "locationEnd.id", target = "locationEndId")
    @Mapping(source = "locationEnd.nameLocation", target = "locationEndNameLocation")
    PathDTO pathToPathDTO(Path path);

    List<PathDTO> pathsToPathDTOs(List<Path> paths);

    @Mapping(source = "locationStartId", target = "locationStart")
    @Mapping(source = "locationEndId", target = "locationEnd")
    Path pathDTOToPath(PathDTO pathDTO);

    List<Path> pathDTOsToPaths(List<PathDTO> pathDTOs);

    default Location locationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
