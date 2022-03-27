package com.michaelloi.user.controller;

import com.michaelloi.common.constants.ChannelCodes;
import com.michaelloi.common.constants.ConfigValues;
import com.michaelloi.common.constants.RequestHeaders;
import com.michaelloi.common.models.objects.Session;
import com.michaelloi.common.models.requests.BaseRequest;
import com.michaelloi.common.models.responses.BaseResponse;
import com.michaelloi.user.constants.UrlValues;
import com.michaelloi.user.model.request.RequestLogin;
import com.michaelloi.user.model.request.RequestRegister;
import com.michaelloi.user.model.response.ResponseLogin;
import com.michaelloi.user.services.LoginService;
import com.michaelloi.user.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Value(value = ConfigValues.applicationName)
    String applicationName;

    @Autowired
    LoginService loginService;

    @Autowired
    RegisterService registerService;

    @PostMapping(value = UrlValues.login)
    public ResponseEntity<?> login(
        @RequestBody RequestLogin request,
        @RequestHeader(RequestHeaders.language) String language,
        @RequestHeader(RequestHeaders.channel) String channel
    ) {
        Session session = new Session();
        session.setService(applicationName);
        session.setPath(UrlValues.login);
        session.setChannel(ChannelCodes.channel);
        session.setLanguage(language);
        return new ResponseEntity<>(
            loginService.login(
                new BaseRequest<>(
                    session,
                    request
                )
            ),
            HttpStatus.OK
        );
    }

    @PostMapping(value = UrlValues.register)
    public ResponseEntity<?> register(
        @RequestBody RequestRegister request,
        @RequestHeader(RequestHeaders.language) String language,
        @RequestHeader(RequestHeaders.channel) String channel
    ) {
        Session session = new Session();
        session.setService(applicationName);
        session.setPath(UrlValues.login);
        session.setChannel(ChannelCodes.channel);
        session.setLanguage(language);
        return new ResponseEntity<>(
            registerService.register(
                new BaseRequest<>(
                    session,
                    request
                )
            ),
            HttpStatus.OK
        );
    }
}
