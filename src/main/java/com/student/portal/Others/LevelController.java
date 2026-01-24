package com.student.portal.Others;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/url/")
public class  LevelController {

    private final LevelInterface levelInterface;

    public LevelController(LevelInterface levelInterface) {

        this.levelInterface = levelInterface;
    }

    @GetMapping("levels")
    public List<LevelDto> list() {

        return levelInterface.getallLevels();
    }

    @GetMapping("levels/{id}")
    public LevelDto findById(@PathVariable Integer id)
    {
        try {
            return levelInterface.getLevelbyId(id);
        }
        catch (Exception e) {
            System.out.println("Error "+ e);
            return null;
        }
    }

    @PostMapping ("levels")
    public Integer PostLevel(@RequestBody LevelDto levelDto) {
        return levelInterface.CreateLevel(levelDto);
    }

    @PatchMapping ("levels/{id}")
    public LevelDto UpdateLevel(@PathVariable Integer id ,@RequestBody Map<String, Object> levelDto) {
        return levelInterface.UpdateLevelFields(levelDto, id);
    }

    @DeleteMapping("levels/{id}")
    public boolean DeleteLevelbyId(@PathVariable Integer id) {

        return levelInterface.DeleteLevelbyId(id);
    }

}
