package com.michaelloi.user.services;

import com.michaelloi.common.constants.*;
import com.michaelloi.common.models.entity.UserData;
import com.michaelloi.common.models.objects.BaseError;
import com.michaelloi.common.models.objects.Error;
import com.michaelloi.common.models.objects.Message;
import com.michaelloi.common.models.objects.Session;
import com.michaelloi.common.models.requests.BaseRequest;
import com.michaelloi.common.models.responses.BaseResponse;
import com.michaelloi.common.services.LoggerService;
import com.michaelloi.user.model.request.RequestRegister;
import com.michaelloi.user.model.response.ResponseRegister;
import com.michaelloi.user.repositories.RegisterRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class RegisterService {
    @Autowired
    LoggerService loggerService;

    @Autowired
    RegisterRepository registerRepository;

    public BaseResponse<ResponseRegister> register(BaseRequest<RequestRegister> baseRequest){
        BaseResponse<ResponseRegister> response = new BaseResponse<>(baseRequest.getSession().getLanguage());
        baseRequest.getSession().setUuid(loggerService.writeRequest(new Message(baseRequest.getSession(), baseRequest)));

        try{
            UserData userData = setupUserData(baseRequest.getRequest(), baseRequest.getSession());
            BaseError responseInsertUser = registerRepository.insertUser(
                userData,
                baseRequest.getSession()
            );

            if(responseInsertUser.getErrorCode().equalsIgnoreCase(ErrorCodes.general.success)){
                String runningId = registerRepository.getRunningId();
                if(runningId != null && runningId.length() <= 12){
                    String accountNo = StringUtils.leftPad(
                        runningId,
                        Lengths.acctNo,
                        DefaultValues.zeroString
                    );

                    BaseError responseInsertAccount = registerRepository.insertAccount(
                        accountNo,
                        userData.getUserId(),
                        baseRequest.getSession()
                    );

                    if(responseInsertUser.getErrorCode().equalsIgnoreCase(ErrorCodes.general.success)){
                        response.setResponse(setupResponse(userData, accountNo));
                    }else{
                        response.setResponseError(responseInsertAccount);
                    }
                }
            }else{
                response.setResponseError(responseInsertUser);
            }
        }catch (Exception e){
            loggerService.writeException(new Error(baseRequest.getSession(), e));
            response.setResponseError();
        }
        loggerService.writeResponse(new Message(baseRequest.getSession(), response));
        return response;
    }

    public UserData setupUserData(RequestRegister request, Session session){
        UserData userData = new UserData();
        userData.setUserId(UUID.randomUUID().toString());
        userData.setUserName(request.getUserName());
        userData.setUserNickname(request.getNickName());
        userData.setUserFullname(request.getFullName());
        userData.setUserDateOfBirth(new Date());
        userData.setLastLogin(new Date());
        userData.setSessionId(session.getSessionId());
        userData.setIsActive(true);
        userData.setPassword(request.getPassword());
        return userData;
    }

    public ResponseRegister setupResponse(UserData userData, String accountNo){
        ResponseRegister response = new ResponseRegister();
        response.getUser().setUserId(userData.getUserId());
        response.getUser().setUserName(userData.getUserName());
        response.getUser().setNickName(userData.getUserNickname());
        response.getUser().setAccountNo(accountNo);
        response.getUser().setAccountCcy(CurrencyValues.idrCurrency.code);
        response.getUser().setAccountBalance(BigDecimal.ZERO);
        return response;
    }
}
