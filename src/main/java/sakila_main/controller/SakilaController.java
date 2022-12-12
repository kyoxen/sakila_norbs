package sakila_main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sakila_main.dto.ActorDTO;
import sakila_main.exception.ResourceNotFoundException;
import sakila_main.model.ActorModel;
import sakila_main.services.iface.SakilaService;
import sakila_main.vo.ResponseHelper;
import sakila_main.vo.ResponseVO;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sakila/")
public class SakilaController {


    @Autowired
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


}