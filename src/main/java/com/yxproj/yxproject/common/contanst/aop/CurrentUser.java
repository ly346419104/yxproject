package com.yxproj.yxproject.common.contanst.aop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrentUser {

    private String userId;
    private String username;
    private String enterpriseId;
    private String userEnterpriseType;

}
