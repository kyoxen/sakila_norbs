package sakila_main.services.iface;

import org.springframework.stereotype.Service;
import sakila_main.dto.ActorDTO;
import sakila_main.model.ActorModel;
import sakila_main.vo.ResponseVO;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public interface SakilaService {

    List<ActorModel> getAllActors(ActorDTO actorDTO);

    ActorModel findActor(ActorDTO actorDTO);

    ActorModel updateActorLastName(ActorDTO actorDTO);

    ResponseVO batchInsertActor(ActorDTO actorDTO);

    List<ActorModel> updateLastNameBatchUpdate(ActorDTO actorDTO);

    List<ActorModel> getActorNames(ActorDTO actorDTO);

    ResponseVO batchDeleteActor(ActorDTO actorDTO);

    ByteArrayInputStream exportActor();

}
