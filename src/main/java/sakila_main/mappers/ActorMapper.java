package sakila_main.mappers;

import org.apache.ibatis.annotations.Mapper;
import sakila_main.dto.ActorDTO;
import sakila_main.model.ActorModel;

import java.util.List;

@Mapper
public interface ActorMapper {

     List<ActorModel> getAllActors();

     List<ActorModel> findActor(ActorDTO actorDTO);

     void setAllActorLastName(int actorId,String lastName);


}
