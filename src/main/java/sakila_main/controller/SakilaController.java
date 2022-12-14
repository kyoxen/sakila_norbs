package sakila_main.controller;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import sakila_main.dto.ActorDTO;
import sakila_main.mappers.ActorMapper;
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
    @Resource
    ActorMapper actorMapper;

    @GetMapping("list/actor")
    public ResponseVO getAllActors() {
        return ResponseHelper.success(sakilaService.getAllActors());
    }

    @PostMapping("find/actor")
    public ResponseVO findActor(@RequestBody ActorDTO actorDTO) {
        ActorModel actorModels = sakilaService.findActor(actorDTO);
        if (actorModels == null) {
            return ResponseHelper.nullData(actorModels);
        }
        return ResponseHelper.success(actorModels);
    }

    @PostMapping("update/actorsLastName")
    public ResponseVO updateAllActorLastName(@RequestBody ActorDTO actorDTO) {
        ActorModel actor = sakilaService.updateActorLastName(actorDTO);
        if (actor == null) {
            return ResponseHelper.nullData(actor);
        }
        return ResponseHelper.success(actor);
    }

    @PostMapping("batchInsert/actor")
    public ResponseVO batchInsertActor(@RequestBody ActorDTO actorDTO) {
        return ResponseHelper.success(sakilaService.batchInsertActor(actorDTO));
    }

    @PostMapping("batchDelete/actor")
    public ResponseVO batchDeleteActor(@RequestBody ActorDTO actorDTO) {
        int row = sakilaService.batchDeleteActor(actorDTO);
        if (row == 0) {
            return ResponseHelper.success();
        }
        return ResponseHelper.nullData(sakilaService.batchDeleteActor(actorDTO));
    }

    @PostMapping("batchSelect/actor")
    public ResponseVO getActorNames(@RequestBody ActorDTO actorDTO) {
        Assert.isTrue(!actorDTO.getActorIds().isEmpty(), "Please enter an ids!");
        return ResponseHelper.success(sakilaService.getActorNames(actorDTO));
        //request body parameter
        //{
        //   "actorIds" :[]
        //}
    }

    @PostMapping("batchUpdateLastName/actor")
    public ResponseVO batchUpdateLastName(@RequestBody ActorDTO actorDTO) {
        return ResponseHelper.success(sakilaService.updateLastNameBatchUpdate(actorDTO));
    }


}