package com.student.portal.User;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/url/")
public class  UserController {

    private final UserInterface userInterface;

    public UserController(UserInterface userInterface) {

        this.userInterface = userInterface;
    }

    @GetMapping("users")
    public List<Map<String, Object>> list() {

        return userInterface.getallUsers();
    }

    @GetMapping("users/{id}")
    public UserDto findById(@PathVariable Integer id)
    {
        try {
            return userInterface.getUserbyId(id);
        }
        catch (Exception e) {
            System.out.println("Error "+ e);
            return null;
        }
    }

    @PostMapping ("users")
    public Integer PostUser(@RequestBody UserDto userDto) {
        return userInterface.CreateUser(userDto);
    }

    @PatchMapping ("users/{id}")
    public UserDto UpdateUser(@PathVariable Integer id ,@RequestBody Map<String, Object> userDto) {
        return userInterface.UpdateUserFields(userDto, id);
    }

    @DeleteMapping("users/{id}")
    public boolean DeleteUserbyId(@PathVariable Integer id) {

        return userInterface.DeleteUserbyId(id);
    }

}
