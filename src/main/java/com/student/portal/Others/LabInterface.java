package com.student.portal.Others;

import java.util.List;
import java.util.Map;

public interface LabInterface {

    public List<LabDto> getallLabs();

    public LabDto getLabbyId(Integer id);

    public Integer CreateLab(LabDto studentDto);

    public boolean DeleteLabbyId(Integer id);

    public LabDto UpdateLabFields(Map<String, Object> studentDto, Integer id);

}
