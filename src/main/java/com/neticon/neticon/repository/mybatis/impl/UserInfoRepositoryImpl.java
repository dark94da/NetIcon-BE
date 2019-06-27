package com.neticon.neticon.repository.mybatis.impl;

import com.neticon.neticon.common.domain.UserInfo;
import com.neticon.neticon.common.domain.UserInfoExample;
import com.neticon.neticon.repository.mybatis.mapper.UserInfoMapper;
import com.neticon.neticon.tools.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class UserInfoRepositoryImpl {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo getUserInfoByNickname(String nickname) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andNicknameEqualTo(nickname);
        List<UserInfo> res = userInfoMapper.selectByExample(example);
        return CollectionUtils.isEmpty(res) ? null : res.get(0);
    }

    public boolean insertUserInfo(UserInfo userInfo) {
        return userInfoMapper.insertSelective(DBUtils.completeInsert(userInfo)) > 0;
    }

    public void updateUserInfo(UserInfo userInfo) {
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample
                .createCriteria()
                .andNicknameEqualTo(userInfo.getNickname());
        userInfoMapper.updateByExampleSelective(userInfo, userInfoExample);
    }
}
