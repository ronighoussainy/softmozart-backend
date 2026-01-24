package com.student.portal.Others;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/url/")
public class  LabController {

    private final LabInterface labInterface;

    public LabController(LabInterface labInterface) {

        this.labInterface = labInterface;
    }

    @GetMapping("labs")
    public List<LabDto> list() {

        return labInterface.getallLabs();
    }

    @GetMapping("labs/{id}")
    public LabDto findById(@PathVariable Integer id)
    {
        try {
            return labInterface.getLabbyId(id);
        }
        catch (Exception e) {
            System.out.println("Error "+ e);
            return null;
        }
    }

    @PostMapping ("labs")
    public Integer PostLab(@RequestBody LabDto labDto) {
        return labInterface.CreateLab(labDto);
    }

    @PatchMapping ("labs/{id}")
    public LabDto UpdateLab(@PathVariable Integer id ,@RequestBody Map<String, Object> labDto) {
        return labInterface.UpdateLabFields(labDto, id);
    }

    @DeleteMapping("labs/{id}")
    public boolean DeleteLabbyId(@PathVariable Integer id) {

        return labInterface.DeleteLabbyId(id);
    }

}
