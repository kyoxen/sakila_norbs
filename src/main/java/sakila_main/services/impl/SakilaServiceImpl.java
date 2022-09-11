package sakila_main.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sakila_main.dto.ActorDTO;
import sakila_main.mappers.ActorMapper;
import sakila_main.model.ActorModel;
import sakila_main.services.iface.SakilaService;

import java.util.List;

@Service
public class SakilaServiceImpl implements SakilaService {

    @Autowired
    ActorMapper actorMapper;


    public List<ActorModel> getAllActors() {

        return actorMapper.getAllActors();

    }

    public List<ActorModel> findActor(ActorDTO actorDTO) {

        return actorMapper.findActor(actorDTO);

    }

    public List<ActorModel> updateActorLastName() {

        List<ActorModel> getActors = actorMapper.getAllActors();

        //initialization

        //alternate for each
       /* getActors.forEach((v)->
                System.out.println("Hello"));
*/

        int i = 0;
        for (ActorModel list : getActors) {

            list.setLastName("Coronado");
            System.out.println("Name: " + getActors.get(i).getLastName() + "  id: " + getActors.get(i).getActorId());
            actorMapper.setAllActorLastName(getActors.get(i).getActorId(), list.getLastName());

            i += 1;
        }


        return getActors;

    }


}
