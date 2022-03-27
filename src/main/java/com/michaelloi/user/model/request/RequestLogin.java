package com.michaelloi.user.model.request;

import com.michaelloi.common.constants.DefaultValues;
import lombok.Data;

@Data
public class RequestLogin {
    public String userName = DefaultValues.emptyString;
    public String password = DefaultValues.emptyString;
}
