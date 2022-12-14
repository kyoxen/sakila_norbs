package sakila_main.services.iface;

import org.springframework.stereotype.Service;
import sakila_main.dto.ActorDTO;
import sakila_main.model.ActorModel;
import sakila_main.vo.ResponseVO;

import java.util.List;

@Service
public interface SakilaService {

    List<ActorModel> getAllActors();

    ActorModel findActor(ActorDTO actorDTO);

    ActorModel updateActorLastName(ActorDTO actorDTO);

    ResponseVO batchInsertActor(ActorDTO actorDTO);

    List<String> updateLastNameBatchUpdate(ActorDTO actorDTO);

    List<String> getActorNames(ActorDTO actorDTO);

    Integer batchDeleteActor(ActorDTO actorDTO);

}
