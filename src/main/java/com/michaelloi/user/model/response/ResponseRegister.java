package com.michaelloi.user.model.response;

import com.michaelloi.common.models.objects.User;
import lombok.Data;

@Data
public class ResponseRegister {
    public User user = new User();
}
