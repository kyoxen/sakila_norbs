package sakila_main.services.iface;

import org.springframework.stereotype.Service;
import sakila_main.dto.ActorDTO;
import sakila_main.model.ActorModel;

import java.util.List;

@Service
public interface SakilaService {

    List<ActorModel> getAllActors();

    List<ActorModel> findActor(ActorDTO actorDTO);

    List<ActorModel>  updateActorLastName();



}
