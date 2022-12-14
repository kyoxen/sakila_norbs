package sakila_main.services.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sakila_main.dto.ActorDTO;
import sakila_main.mappers.ActorMapper;
import sakila_main.model.ActorModel;
import sakila_main.services.iface.SakilaService;
import sakila_main.utils.ListSplitUtil;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class SakilaServiceImpl implements SakilaService {

    @Autowired
    ActorMapper actorMapper;


    public List<ActorModel> getAllActors() {
        return actorMapper.getAllActors();
    }

    public ActorModel findActor(ActorDTO actorDTO) {
        return actorMapper.findActor(actorDTO);
    }

    public ActorModel updateActorLastName(ActorDTO actorDTO) {
        int getActors = actorMapper.updateActorLastName(actorDTO.getLast_name(), actorDTO.getActor_id());
        ActorModel actorModels =actorMapper.findActor(actorDTO);

        return actorModels;

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




    @Override
    public Integer batchDeleteActor(ActorDTO actorDTO) {
    /*   String[] actorIds = String.valueOf(actorDTO.getActor_id()).split(",");
       List<Integer> Ids = Stream.of(actorIds).map(Integer::valueOf).collect(Collectors.toList());*/

        List<Integer> splitIds = actorDTO.getActorIds();
        //check if id exist
        for (Integer i: splitIds) {
           Integer row = actorMapper.ifIdExist(i);
            if(row==0) {
                return i;
            } else {
                actorMapper.batchDeleteByIds(splitIds);
            }
        }
       return 0;
    }


   @Override
    public Integer batchInsertActor(ActorDTO actorDTO){
        String firstName = actorDTO.getFirst_name();
        String lastName = actorDTO.getLast_name();
        String [] arrNames1 = firstName.split(",");
        String [] arrNames2 = lastName.split(",");

        //check if name exist
       List<ActorDTO> existingName = new ArrayList<>();
       for (String firstnames : arrNames1) {
           for (String lastnames : arrNames2) {
               boolean isExist = actorMapper.checkBothFirstLastNames(firstnames, lastnames);
               if (isExist) {
                   ActorDTO actorDTO1 = new ActorDTO();
                   actorDTO1.setFirst_name(firstnames);
                   actorDTO1.setLast_name(lastnames);
                   existingName.add(actorDTO1);
                   break;
               } else {
                   //if firstname and last name does not exist
                   List<String> fName = Stream.of(arrNames1).collect(Collectors.toList());
                   List<String> lName = Stream.of(arrNames2).collect(Collectors.toList());

                   List<ActorDTO> actorDTOList = new ArrayList<>();
                   for (int i = 0; i < fName.size(); i++) {
                       ActorDTO acD = new ActorDTO();
                       acD.setFirst_name(fName.get(i).trim());
                       acD.setLast_name(lName.get(i).trim());
                       acD.setCreated_at(LocalDateTime.now());
                       acD.setLast_update("");
                       actorDTOList.add(acD);
                   }
                   //actorMapper.batchInsert(actorDTOList);

                   List<List<ActorDTO>> splitList = split(actorDTOList, 20);
                   for (List<ActorDTO> list : splitList) {
                       actorMapper.batchInsert(list);
                   }
                   return 1;
               }
           }
         /*  List<List<ActorDTO>> returnList = new ArrayList<>();
           returnList.add(existingName);
           System.out.println(returnList);*/
       }
       return 0;
   }

    public List<String> updateLastNameBatchUpdate(ActorDTO actorDTO) {
        Assert.isTrue(!actorDTO.getActorIds().isEmpty(),"Please enter ids!");
        Assert.isTrue(!actorDTO.getLast_name().isEmpty(),"Please enter last name!");
        String lastName  = actorDTO.getLast_name();

        List<List<Integer>> splitIds = split(actorDTO.getActorIds(),20);
        for (List<Integer> list: splitIds) {
            actorMapper.lastNameBatchUpdate(list,lastName);
            log.info("actor Ids: {} lastName:{}", list,lastName);
        }
        return getActorNames(actorDTO);
    }

    public List<String> getActorNames(ActorDTO actorDTO){
        List<Integer> actorIds = actorDTO.getActorIds();
        List<List<Integer>> actors = ListSplitUtil.split(actorIds, 20);
        List<String> result = new ArrayList<>();
        for (List<Integer> list : actors) {
            List<String> ids = actorMapper.queryActors(list);
                result.addAll(ids);
        }
        return result;
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
