package sakila_main.services.impl;

import jdk.vm.ci.meta.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sakila_main.dto.ActorDTO;
import sakila_main.mappers.ActorMapper;
import sakila_main.model.ActorModel;
import sakila_main.services.iface.SakilaService;
import sakila_main.vo.ResponseVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    public ResponseVO updateActorLastName(String lastName, int actorId) {

        int getActors = actorMapper.updateActorLastName(lastName,actorId);

        if(getActors>0) {
            return new ResponseVO(200,"Success",getActors);
        } else {
            return new ResponseVO(500,"Error",null);
        }

   /*     List<ActorModel> getActors = actorMapper.getAllActors();

        //initialization

        //alternate for each
       *//* getActors.forEach((v)->
                System.out.println("Hello"));
*//*

        int i = 0;
        for (ActorModel list : getActors) {

            list.setLastName("Coronado");
            System.out.println("Name: " + getActors.get(i).getLastName() + "  id: " + getActors.get(i).getActorId());
            actorMapper.setAllActorLastName(getActors.get(i).getActorId(), list.getLastName());

            i += 1;
        }*/

    }

    public void batchInsertActor(ActorDTO actorDTO){
        String firstName = actorDTO.getFirst_name();
        String lastName = actorDTO.getLast_name();
        String [] arrNames1 = firstName.split(",");
        String [] arrNames2 = lastName.split(",");

        //list
        List<String> fName = Stream.of(arrNames1).collect(Collectors.toList());
        List<String> lName = Stream.of(arrNames2).collect(Collectors.toList());

        ActorDTO acD = new ActorDTO();

        List<ActorDTO> actorDTOList = new ArrayList<>();
        for (int i = 0; i < fName.size(); i++) {
            acD.setFirst_name(fName.get(i).trim());
            acD.setLast_name(lName.get(i).trim());
            acD.setLast_update("");
            actorDTOList.add(acD);
        }
     //   actorMapper.batchInsert(actorDTOList);

       List<List<ActorDTO>> splitList = split(actorDTOList,20);
        for (List<ActorDTO> list : splitList) {
            actorMapper.batchInsert(list);
        }
    }


    public static <T> List<List<T>> split (Collection<T> collection, int size) {
        List<List<T>> result = new ArrayList<>();
        ArrayList<T> subList = new ArrayList(size);

        Object t;
        for(Iterator var4 = collection.iterator(); var4.hasNext(); subList.add((T) t)){
            t= var4.next();
            if(subList.size()>=size) {
                result.add(subList);
                subList = new ArrayList(size);
            }
        }
        result.add(subList);
        return result;
    }


}
