package sakila_main.services.iface;

import org.springframework.stereotype.Service;
import sakila_main.dto.ActorDTO;
import sakila_main.model.ActorModel;
import sakila_main.vo.ResponseVO;

import java.util.List;

@Service
public interface SakilaService {

    List<ActorModel> getAllActors();

    List<ActorModel> findActor(ActorDTO actorDTO);

    ResponseVO updateActorLastName(String lastName, int actorId);

    List<List<ActorDTO>> batchInsertActor(ActorDTO actorDTO);

    List<List<Integer>> batchDeleteActor(ActorDTO actorDTO);

}
