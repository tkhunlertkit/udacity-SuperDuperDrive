package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

    @Autowired private CredentialMapper credentialMapper;
    @Autowired private EncryptionService encryptionService;
    @Autowired private UserService userService;

    public void insertCredential(Credential credential, String username) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setUserid(Integer.valueOf(userService.getUserId(username)));

        if (credential.getCredentialid() == null) {
            // new credential
            credentialMapper.insertCredential(credential);
        } else {
            // update credential
            credentialMapper.updateCredential(credential);
        }
    }

    public List<Credential> getAllCredentials(String username) {
        return credentialMapper.getAllCredentials(userService.getUserId(username));
    }

    public void deleteCredential(Integer credentialid) {
        credentialMapper.deleteCredential(credentialid);
    }
}