package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private  HashService hashService;
    public int addUser(User user) {
        SecureRandom random = new  SecureRandom();
        byte [] salt = new byte[16];
        random.nextBytes(salt);
        String encodesSalt = Base64.getEncoder().encodeToString((salt));
        String hashedPassword = hashService.getHashedValue(user.getPassword(),encodesSalt);
        //userName, salt, password, firstName, lastName
        return userMapper.insert(new User(null,user.getUserName(),encodesSalt,hashedPassword, user.getFirstName(), user.getLastName()));
    }

    public User getUserById(int userId) {
        return userMapper.getUserById(userId);
    }

    public User getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    public int updateUser(User user) {
        return userMapper.update(user);
    }

    public int deleteUser(int userId) {
        return userMapper.delete(userId);
    }

    public boolean isUsernameAvailable(String userName) {
        return userMapper.isUsernameAvailable(userName)!=null;
    }
}
