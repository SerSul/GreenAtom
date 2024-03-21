package com.api.greenatom;

import com.api.greenatom.forum.dto.request.TopicRequestDTO;
import com.api.greenatom.forum.dto.response.TopicResponseDTO;
import com.api.greenatom.forum.service.MessageService;
import com.api.greenatom.forum.service.TopicService;
import com.api.greenatom.forum.service.impl.MessageServiceImpl;
import com.api.greenatom.forum.service.impl.TopicServiceImpl;
import com.api.greenatom.security.dto.SignUpRequest;
import com.api.greenatom.security.service.AuthenticationService;
import com.api.greenatom.security.service.impl.AuthenticationServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbSeeder implements CommandLineRunner {
    private final AuthenticationService authenticationService;
    private final TopicService topicService;
    private final MessageService messageService;

    public DbSeeder(AuthenticationServiceImpl authenticationService, TopicServiceImpl topicService, MessageServiceImpl messageService) {
        this.authenticationService = authenticationService;
        this.topicService = topicService;
        this.messageService = messageService;
    }

    @Override
    public void run(String... args) throws Exception {
        SignUpRequest adminSignUpRequest = new SignUpRequest();
        adminSignUpRequest.setUsername("adminUsername");
        adminSignUpRequest.setPassword("adminPassword");
        adminSignUpRequest.setEmail("admin@example.com");
        authenticationService.signUp(adminSignUpRequest);

        SignUpRequest userSignUpRequest = new SignUpRequest();
        userSignUpRequest.setUsername("userUsername");
        userSignUpRequest.setPassword("userPassword");
        userSignUpRequest.setEmail("user@example.com");

        authenticationService.signUp(userSignUpRequest);

        TopicRequestDTO topicRequest = new TopicRequestDTO();
        topicRequest.setTitle("Название топика");


    }
}

