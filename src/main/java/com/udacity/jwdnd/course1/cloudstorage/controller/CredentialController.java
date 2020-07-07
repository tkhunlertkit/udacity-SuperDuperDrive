package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.security.Principal;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/credential/**")
public class CredentialController {

    @Autowired private CredentialService credentialService;

    @RequestMapping("/credential/insert")
    public RedirectView insertCredential(
        @RequestParam(value = "credentialid") Integer credentialid,
        @RequestParam(value = "url") String url,
        @RequestParam(value = "username") String username,
        @RequestParam(value = "password") String password,
        Principal principal
    ) {
        Credential cred = new Credential(credentialid, url, username, password);
        credentialService.insertCredential(cred, principal.getName());
        return new RedirectView("/home#nav-credentials");
    }

    @RequestMapping("/credential/delete/{credentialid:.+}")
    public RedirectView deleteCredential(@PathVariable Integer credentialid) {
        credentialService.deleteCredential(credentialid);
        return new RedirectView("/home#nav-credentials");
    }
}