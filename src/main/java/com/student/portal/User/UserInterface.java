package com.student.portal.User;

import java.util.List;
import java.util.Map;

public interface UserInterface {

    public List<Map<String, Object>> getallUsers();

    public UserDto getUserbyId(Integer id);

    public Integer CreateUser(UserDto studentDto);

    public boolean DeleteUserbyId(Integer id);

    public UserDto UpdateUserFields(Map<String, Object> studentDto, Integer id);

}
