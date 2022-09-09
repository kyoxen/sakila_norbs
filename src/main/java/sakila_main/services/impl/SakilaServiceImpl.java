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


    public List<ActorModel> getAllActors(){

        return actorMapper.getAllActors();

    }

    public List<ActorModel> findActor(ActorDTO actorDTO){

        return actorMapper.findActor(actorDTO);

    }






}
