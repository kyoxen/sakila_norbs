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

     int batchDeleteByIds(@Param("list") List<Integer> ids);

     int ifIdExist(Integer id);


     List<String> queryActors(@Param("list") List<Integer> ids);


}
