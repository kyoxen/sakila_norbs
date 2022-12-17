package sakila_main.services.impl;


import cn.hutool.core.util.StrUtil;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sakila_main.dto.ActorDTO;
import sakila_main.mappers.ActorMapper;
import sakila_main.model.ActorModel;
import sakila_main.services.iface.SakilaService;
import sakila_main.utils.CollectionUtil;
//import sakila_main.utils.ExportActorCsvHelper;
import sakila_main.utils.ExportActorCsvHelper;
import sakila_main.utils.ListSplitUtil;
import sakila_main.vo.ParentCommonStatusCode;
import sakila_main.vo.ResponseHelper;
import sakila_main.vo.ResponseVO;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class SakilaServiceImpl implements SakilaService {

    @Autowired
    ActorMapper actorMapper;


    public List<ActorModel> getAllActors(ActorDTO actorDTO) {
        return actorMapper.getAllActors(actorDTO);
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
    public ResponseVO batchInsertActor(ActorDTO actorDTO){
        String firstName = actorDTO.getFirst_name();
        String lastName = actorDTO.getLast_name();
        String [] arrNames1 = firstName.split(",");
        String [] arrNames2 = lastName.split(",");

       //check if firstname and last name does not exist
        String errorMsg=verifyNames(Arrays.stream(arrNames1).collect(Collectors.toList()), Arrays.stream(arrNames2).collect(Collectors.toList()));
        if(!StringUtils.isNullOrEmpty(errorMsg) && errorMsg.length()>0){
            return new ResponseVO(ParentCommonStatusCode.FAILURE.getCode(),errorMsg,errorMsg);
        }

       List<ActorDTO> actorDTOList = new ArrayList<>();
       for (int i = 0; i < arrNames1.length; i++) {
          // boolean isExist = actorMapper.checkBothFirstLastNames(arrNames1[i], arrNames2[i]);
          // if (!isExist) {
               ActorDTO acD = new ActorDTO();
               acD.setActor_id(actorMapper.countIds()+1);
               acD.setFirst_name(arrNames1[i]);
               acD.setLast_name(arrNames2[i]);
               acD.setCreated_at(LocalDateTime.now());
               acD.setLast_update("");
               actorDTOList.add(acD);
               actorMapper.insertSelective(acD);
        //   }
       }
       List<List<ActorDTO>> returnList = new ArrayList<>();
       returnList.add(actorDTOList);
       return ResponseHelper.success(returnList);
   }

    private String verifyNames(List<String> firstName, List<String> lastName) {
        List<ActorDTO> listNamesInfo = actorMapper.verifyNames(firstName, lastName);
        Map<String, List<ActorDTO>> mapFirstNamesInfo = listNamesInfo.stream().collect(Collectors.groupingBy(ActorDTO::getFirst_name));
        Map<String, List<ActorDTO>> mapLastNamesInfo = listNamesInfo.stream().collect(Collectors.groupingBy(ActorDTO::getLast_name));

        StringBuffer errorMsg = new StringBuffer();
        List<String> first = new ArrayList<>();
        List<String> last = new ArrayList<>();
        List<String> fullName = new ArrayList<>();
        for (String fName : firstName) {
            if (!CollectionUtil.isEmpty(mapFirstNamesInfo.get(fName))) {
                first.add(fName);
            }
        }
        for (String lName : lastName) {
            if (!CollectionUtil.isEmpty(mapLastNamesInfo.get(lName))) {
                last.add(lName);
            }
        }

        for (int i = 0; i < first.size(); i++) {
            fullName.add(first.get(i) + " " + last.get(i));
        }

       // errorMsg.append("The following accounts " +fullName + " already exist!");
      //  errorMsg.append(StrUtil.format("The following accounts {} already exist!", fullName.stream().collect(Collectors.joining(","))));
        errorMsg.append(String.format("The following accounts [%s] already exist!", fullName.stream().collect(Collectors.joining(","))));


        return errorMsg.toString();
    }

    public List<String> updateLastNameBatchUpdate(ActorDTO actorDTO) {
        Assert.isTrue(!actorDTO.getActorIds().isEmpty(),"Please enter ids! ");
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

    public ByteArrayInputStream exportActor() {
        List<ActorModel> exportFile = actorMapper.exportActor();
        log.info("exportFile: {}",exportFile);
        ByteArrayInputStream download = ExportActorCsvHelper.exportHelperToCsv(exportFile);
        return download;
    }


}
