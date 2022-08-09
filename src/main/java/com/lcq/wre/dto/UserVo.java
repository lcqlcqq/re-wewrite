package com.lcq.wre.dto;

import lombok.Data;

@Data
public class UserVo {

    private String nickname;

    private String avatar;
    //@JsonSerialize(using = ToStringSerializer.class)
    private String id;
}
