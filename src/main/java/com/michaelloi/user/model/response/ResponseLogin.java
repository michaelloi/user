package com.michaelloi.user.model.response;

import com.michaelloi.common.models.objects.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResponseLogin {
    public User user = new User();
}
