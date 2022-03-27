package com.michaelloi.user.repositories;

import com.michaelloi.common.constants.ErrorCodes;
import com.michaelloi.common.constants.ErrorMessage;
import com.michaelloi.common.constants.LabelCodes;
import com.michaelloi.common.constants.LanguageValues;
import com.michaelloi.common.models.entity.AccountData;
import com.michaelloi.common.models.entity.UserData;
import com.michaelloi.common.models.objects.BaseError;
import com.michaelloi.common.models.objects.Session;
import com.michaelloi.user.constants.QueryValues;
import com.michaelloi.user.model.request.RequestLogin;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class LoginRepository {
    @PersistenceContext
    public EntityManager entityManager;

    @Transactional
    public UserData checkLogin(RequestLogin requestLogin) {
        UserData userData = (UserData) entityManager.createNativeQuery(QueryValues.loginQuery.loginUser)
            .setParameter(1, requestLogin.getUserName())
            .setParameter(2, requestLogin.getPassword())
            .getResultStream();
        return userData;
    }

    @Transactional
    public AccountData getAccount(String userId) {
        AccountData accountData = (AccountData) entityManager.createNativeQuery(QueryValues.loginQuery.getAccount)
            .setParameter(1, userId)
            .getResultStream();
        return accountData;
    }
}
