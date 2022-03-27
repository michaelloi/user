package com.michaelloi.user.model.request;

import com.michaelloi.common.constants.DefaultValues;
import com.michaelloi.common.models.objects.User;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class RequestRegister extends User {
    private String dateOfBirth = DefaultValues.emptyString;
}
