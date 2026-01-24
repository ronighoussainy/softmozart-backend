package com.student.portal.User;

import com.student.portal.Student.StudentDto;
import com.student.portal.Student.StudentInterface;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserInterface{

    private final UserRepository repo;

    private final UserMapper mapper;
    private StudentInterface studentInterface;
    private StudentDto studentDto;

    public UserService(UserRepository userRepository, UserMapper userMapper, StudentInterface studentInterface) {
        this.repo = userRepository;
        this.mapper = userMapper;
        this.studentInterface = studentInterface;
    }

    public List<Map<String, Object>> getallUsers(){

        return repo.getAllUsers();
//        return repo.findAll()
//                .stream()
//                .map(this::convertUserEntitytoDTO)
//                .collect(Collectors.toList());
    }

    public UserDto getUserbyId(Integer id){
        Optional<User> user = repo.findById(id);
        return convertUserEntitytoDTO(user.get());
    }

    private UserDto convertUserEntitytoDTO(User user){
        UserDto userDto = mapper.userToUserDto(user);
        Integer studentId = userDto.getStudentId();
         if (studentId!=0) {
            studentDto = studentInterface.getStudentbyId(userDto.getStudentId());
            userDto.setStudentName(studentDto.getFullName());
        }
        if(studentId==0)
            userDto.setStudentName("All");
        return userDto;
    }

    public Integer CreateUser(UserDto userDto){
        User user = mapper.userDtoToUser(userDto);
        if (user.getStudentId()==null)
           user.setStudentId("0");

        user.setDateupdated(LocalDate.now());
        repo.saveAndFlush(user);
        return repo.getMaxUserId();
    }

    public boolean DeleteUserbyId(Integer id){
        repo.deleteById(id);
        repo.flush();
        return true;
    }

    public UserDto UpdateUserFields(Map<String, Object> userDto, Integer id){
        User user = repo.findById(id) .orElseThrow(() -> new ResourceNotFoundException("user not found on :: "+ id));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (userDto.containsKey("password") ){
            user.setPassword(userDto.get("password").toString());
            }
        if (userDto.containsKey("role") )
            user.setRole(userDto.get("role").toString());
        if (userDto.containsKey("studentId") )
            user.setStudentId(userDto.get("studentId").toString());
        if (userDto.containsKey("username") )
            user.setUsername(userDto.get("username").toString());
       user.setDateupdated(LocalDate.now());
        if (user.getStudentId()==null)
            user.setStudentId("0");
        repo.saveAndFlush(user);
        return convertUserEntitytoDTO(user);
    }
}
