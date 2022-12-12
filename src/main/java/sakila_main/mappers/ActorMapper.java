package sakila_main.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sakila_main.dto.ActorDTO;
import sakila_main.model.ActorModel;

import java.util.List;

@Mapper
public interface ActorMapper {

     int updateActorLastName(String lastName, int actorId);

     List<ActorModel> findActor(ActorDTO actorDTO);

     List<ActorModel> getAllActors();

    // void setAllActorLastName(int actorId,String lastName);

     int batchInsert(@Param("list") List<ActorDTO> list);


}
