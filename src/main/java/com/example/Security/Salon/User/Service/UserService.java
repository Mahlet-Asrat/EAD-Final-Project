package com.example.Security.Salon.User.Service;

import com.example.Security.Salon.Exception.AlreadyExistsException;
import com.example.Security.Salon.Exception.ResourceNotFoundException;
import com.example.Security.Salon.Role.Model.Role;
import com.example.Security.Salon.Role.Repository.RoleRepository;
import com.example.Security.Salon.User.Model.Dto.AddUserDto;
import com.example.Security.Salon.User.Model.Dto.EditUserDto;
import com.example.Security.Salon.User.Model.Dto.LoginDto;
import com.example.Security.Salon.User.Model.User;
import com.example.Security.Salon.User.Model.UserPrincipal;
import com.example.Security.Salon.User.Service.Repository.UserRepository;
import com.example.Security.Salon.Utils.JWTService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService extends IUserLogin implements IUserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JWTService jwtService;
    @Override
    @Transactional
    public User createUser(AddUserDto addUserDto, Role role) throws AlreadyExistsException{
        return (User) Optional.ofNullable(userRepository.findByUsername(addUserDto.getUsername()))
                .map((existingUser) -> {
                    throw new AlreadyExistsException("User already exists with the username: " + addUserDto.getUsername());
                })
                .orElseGet(() -> {
                    String password = bCryptPasswordEncoder.encode(addUserDto.getPassword());


                    System.out.println("Fetched Role ID: " + role.getId()); // Log the fetched role ID
                    System.out.println("Inserting user with role_id: " + role.getId()); // Log the role_id
                    User user = new User(
                            addUserDto.getFirstName(),
                            addUserDto.getLastName(),
                            addUserDto.getUsername(),
                            addUserDto.getPhoneNumber(),
                            addUserDto.getEmail(),
                            password,
                            role




                    );
                    return  userRepository.save(user);
                });
    }

public String login(LoginDto loginDto) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        if (authentication.isAuthenticated()){
            UserPrincipal users = (UserPrincipal) authentication.getPrincipal();
            UUID userId = users.getId();
            String role = users.getRoleId();
            return jwtService.generateToken(userId, role);
    }
        return "boo";


}

    private String convertUUIDToHex(UUID uuid) {
        // Convert UUID to a string without hyphens
        String uuidWithoutHyphens = uuid.toString().replace("-", "");
        // Return the hex string
        return uuidWithoutHyphens;
    }



    @Override
    public User editUser(EditUserDto editUserDto, UUID id) throws Exception {
        return Optional.of(findUserById(id)).map((exsistingUser)->{
            exsistingUser.setFirstName(editUserDto.getFirstName());
            exsistingUser.setLastName(editUserDto.getLastName());
            exsistingUser.setPhoneNumber(editUserDto.getPhoneNumber());
            exsistingUser.setEmail(editUserDto.getEmail());
            exsistingUser.setUsername(editUserDto.getUsername());

            userRepository.save(exsistingUser);
            return exsistingUser;

        }).orElseThrow(()->
                new ResourceNotFoundException("User not found"));
    }


    @Override
    public User findUserById(UUID id) throws Exception {
        return userRepository.findById(id).orElseThrow( () ->new ResourceNotFoundException("User Not Found!"));

    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        return Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(() ->new Exception("User Not Found!"));
    }

    @Override
    public User findUserByUsername(String username) throws Exception {
        return Optional.ofNullable(userRepository.findByUsername(username)).orElseThrow(() ->new ResourceNotFoundException("User Not Found!"));
    }

    @Override
    public String deleteUser(UUID id)  throws Exception {
        return Optional.of(userRepository.findById(id)).map((existingUser)-> {
            userRepository.deleteById(id);
            return "User deleted";
        }).orElseThrow(()->new ResourceNotFoundException("User Not Found!"));

    }
}

