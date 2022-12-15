package sakila_main.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sakila_main.dto.ActorDTO;
import sakila_main.model.ActorModel;

import java.util.List;

@Mapper
public interface ActorMapper {

     int updateActorLastName(String lastName, int actorId);

     ActorModel findActor(ActorDTO actorDTO);

     Boolean checkBothFirstLastNames(String firstname,String lastname);

     String checkFirstName(String firstname);

     String checkLastName(String lastname);

     List<ActorModel> getAllActors(ActorDTO actorDTO);

     Integer countIds();

     List<ActorDTO> verifyNames(@Param("fName") List<String> fName, @Param("lName")List<String> lName);

    // void setAllActorLastName(int actorId,String lastName);

     int batchInsert(@Param("list") List<ActorDTO> list);

     int insertSelective(ActorDTO actorDTO);

     int batchDeleteByIds(@Param("list") List<Integer> ids);

     int ifIdExist(Integer id);


     List<String> queryActors(@Param("list") List<Integer> ids);

     int lastNameBatchUpdate(@Param("list") List<Integer> ids , String lastName);


}
