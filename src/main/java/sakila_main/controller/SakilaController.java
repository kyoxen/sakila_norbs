package sakila_main.controller;


import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import sakila_main.dto.ActorDTO;
import sakila_main.exception.ResourceNotFoundException;
import sakila_main.model.ActorModel;
import sakila_main.services.iface.SakilaService;
import sakila_main.vo.ResponseHelper;
import sakila_main.vo.ResponseVO;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/sakila/")
public class SakilaController {

    @Resource
SakilaService sakilaService;

    @GetMapping("list/actor")
    public ResponseVO<ActorModel> getAllActors() {

        List<ActorModel> list = sakilaService.getAllActors();

        return new ResponseVO(200, "Success!", list);
    }

    @PostMapping("find/actor")
    public ResponseVO<ActorModel> findActor(@RequestBody ActorDTO actorDTO) {

        try {

            List<ActorModel> actorModels = sakilaService.findActor(actorDTO);

            if (!actorModels.isEmpty()) {
                return new ResponseVO(200, "Success!", actorModels);
            } else {
                return new ResponseVO(500, "No such id found!", actorModels);
            }

        } catch (ResourceNotFoundException e) {
            e.getMessage();
        }
        return null;

    }

    @PostMapping("update/actorsLastName")
    public ResponseVO<ActorModel> updateAllActorLastName(@RequestBody String lastName, int actorId){
           return sakilaService.updateActorLastName(lastName,actorId);
    }

    @PostMapping("batchInsert/actor")
    public ResponseVO batchInsertActor(@RequestBody ActorDTO actorDTO){
        return ResponseHelper.success(sakilaService.batchInsertActor(actorDTO));
    }

    @PostMapping("batchDelete/actor")
    public ResponseVO batchDeleteActor(@RequestBody ActorDTO actorDTO){
      int row = sakilaService.batchDeleteActor(actorDTO);
      if(row==0) {
          return ResponseHelper.success();
      }
      return ResponseHelper.nullData(sakilaService.batchDeleteActor(actorDTO));
    }
    @PostMapping("batchSelect/actor")
    public ResponseVO getActorNames(@RequestBody ActorDTO actorDTO){
        Assert.isTrue(!actorDTO.getActorIds().isEmpty(),"Please enter an ids!");
        return ResponseHelper.success(sakilaService.getActorNames(actorDTO));
        //request body parameter
        //{
        //   "actorIds" :[]
        //}
    }



    @PostMapping("batchUpdateLastName/actor")
    public ResponseVO batchUpdateLastName(@RequestBody ActorDTO actorDTO){
        return ResponseHelper.success(sakilaService.updateLastNameBatchUpdate(actorDTO));
    }


}