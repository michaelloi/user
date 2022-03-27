package com.michaelloi.user.repositories;

import com.michaelloi.common.constants.*;
import com.michaelloi.common.models.entity.UserData;
import com.michaelloi.common.models.objects.BaseError;
import com.michaelloi.common.models.objects.Session;
import com.michaelloi.user.constants.QueryValues;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class RegisterRepository {
    @PersistenceContext
    public EntityManager entityManager;

    @Transactional
    public BaseError insertUser(UserData userData, Session session) {
        BaseError baseError = new BaseError();

        try{
            entityManager.createNativeQuery(QueryValues.registerQuery.insertUser)
                .setParameter(1, userData.getUserId())
                .setParameter(2, userData.getUserName())
                .setParameter(3, userData.getUserNickname())
                .setParameter(4, userData.getUserFullname())
                .setParameter(5, userData.getUserDateOfBirth())
                .setParameter(6, userData.getLastLogin())
                .setParameter(7, userData.getSessionId())
                .setParameter(8, userData.getIsActive())
                .setParameter(9, userData.getPassword())
                .executeUpdate();

            baseError.setErrorCode(ErrorCodes.general.success);
            baseError.setErrorTitle(
                session.getLanguage().equalsIgnoreCase(LanguageValues.enLanguage)
                    ? LabelCodes.success.english
                    : LabelCodes.success.indonesia
            );
            return baseError;
        }catch (Exception e){
            baseError.setErrorCode(ErrorCodes.general.failed);
            baseError.setErrorTitle(
                session.getLanguage().equalsIgnoreCase(LanguageValues.enLanguage)
                    ? ErrorMessage.title.english
                    : ErrorMessage.title.indonesia
            );
            baseError.setErrorDescription(
                session.getLanguage().equalsIgnoreCase(LanguageValues.enLanguage)
                    ? ErrorMessage.description.english
                    : ErrorMessage.description.indonesia
            );
            return baseError;
        }
    }

    public String getRunningId(){
        String runningId = DefaultValues.emptyString;
        try{
            runningId = entityManager.createNativeQuery(QueryValues.registerQuery.getRunningId).getResultStream().toString();
            return runningId;
        } catch (Exception e){
            return runningId;
        }
    }

    public BaseError insertAccount(String accountNo, String userId, Session session){
        BaseError baseError = new BaseError();
        try{
            entityManager.createNativeQuery(QueryValues.registerQuery.insertUser)
                .setParameter(1, accountNo)
                .setParameter(2, userId)
                .executeUpdate();

            baseError.setErrorCode(ErrorCodes.general.success);
            baseError.setErrorTitle(
                session.getLanguage().equalsIgnoreCase(LanguageValues.enLanguage)
                    ? LabelCodes.success.english
                    : LabelCodes.success.indonesia
            );
            return baseError;
        } catch (Exception e){
            baseError.setErrorCode(ErrorCodes.general.failed);
            baseError.setErrorTitle(
                session.getLanguage().equalsIgnoreCase(LanguageValues.enLanguage)
                    ? ErrorMessage.title.english
                    : ErrorMessage.title.indonesia
            );
            baseError.setErrorDescription(
                session.getLanguage().equalsIgnoreCase(LanguageValues.enLanguage)
                    ? ErrorMessage.description.english
                    : ErrorMessage.description.indonesia
            );
            return baseError;
        }
    }
}

