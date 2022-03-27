package com.michaelloi.user.services;

import com.michaelloi.common.constants.*;
import com.michaelloi.common.models.entity.AccountData;
import com.michaelloi.common.models.entity.UserData;
import com.michaelloi.common.models.objects.Message;
import com.michaelloi.common.models.objects.Error;
import com.michaelloi.common.models.requests.BaseRequest;
import com.michaelloi.common.models.responses.BaseResponse;
import com.michaelloi.common.services.LoggerService;
import com.michaelloi.user.model.request.RequestLogin;
import com.michaelloi.user.model.response.ResponseLogin;
import com.michaelloi.user.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    LoggerService loggerService;

    @Autowired
    LoginRepository loginRepository;

    public BaseResponse<ResponseLogin> login(BaseRequest<RequestLogin> baseRequest){
        BaseResponse<ResponseLogin> response = new BaseResponse<>(baseRequest.getSession().getLanguage());
        baseRequest.getSession().setUuid(loggerService.writeRequest(new Message(baseRequest.getSession(), baseRequest)));

        try{
            UserData userData = loginRepository.checkLogin(
                baseRequest.getRequest()
            );

            if (userData!=null && userData.getIsActive()){
                AccountData accountData = loginRepository.getAccount(
                    userData.getUserId()
                );
                if(accountData!=null){
                    response.setResponse(setupResponse(userData, accountData));
                    return response;
                }
            }
            response.setResponseCode(ErrorCodes.general.failed);
            response.setResponseTitle(
                baseRequest.getSession().getLanguage().equalsIgnoreCase(LanguageValues.enLanguage)
                    ? ErrorMessage.title.english
                    : ErrorMessage.title.indonesia
            );
            response.setResponseDescription(
                baseRequest.getSession().getLanguage().equalsIgnoreCase(LanguageValues.enLanguage)
                    ? ErrorMessage.description.english
                    : ErrorMessage.description.indonesia
            );
        }catch (Exception e){
            loggerService.writeException(new Error(baseRequest.getSession(), e));
            response.setResponseError();
        }
        loggerService.writeResponse(new Message(baseRequest.getSession(), response));
        return response;
    }

    public ResponseLogin setupResponse(UserData userData, AccountData accountData){
        ResponseLogin response = new ResponseLogin();
        response.getUser().setUserId(userData.getUserId());
        response.getUser().setUserName(userData.getUserName());
        response.getUser().setNickName(userData.getUserNickname());
        response.getUser().setAccountNo(accountData.getAccountNo());
        response.getUser().setAccountCcy(CurrencyValues.idrCurrency.code);
        response.getUser().setAccountBalance(accountData.getAccountBalance());
        return response;
    }
}
