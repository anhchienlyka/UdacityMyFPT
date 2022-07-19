package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    @Autowired
    private CredentialMapper credentialMapper;
    @Autowired
    private  HashService hashService;
    public int addCredential(Credential credential,int userId) {
        SecureRandom random = new  SecureRandom();
        byte [] salt = new byte[16];
        random.nextBytes(salt);
        String encodesSalt = Base64.getEncoder().encodeToString((salt));
        String hashedPassword = hashService.getHashedValue(credential.getPassword(),encodesSalt);

        Credential credential1 = new Credential(null, credential.getUrl(), credential.getUserName(),hashedPassword,credential.getPassword(),userId);
        return credentialMapper.insert(credential1);

    }

    public Credential getCredentialById(int credentialId) {
        return credentialMapper.getCredential(credentialId);
    }
    public List<Credential> getCredentialByUserId(int userId) {
        return credentialMapper.getListCredential(userId);
    }
    public int updateCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodesSalt = Base64.getEncoder().encodeToString((salt));
        String hashedPassword = hashService.getHashedValue(credential.getPassword(), encodesSalt);

        Credential credential1 = new Credential(credential.getCredentialId(), credential.getUrl(), credential.getUserName(), hashedPassword, credential.getPassword(), credential.getUserId());
        return credentialMapper.update(credential1);
    }

    public int deleteCredential(int credentialId) {
        return credentialMapper.delete(credentialId);
    }
}
