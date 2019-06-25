package life.draymond.community.service;


import life.draymond.community.dto.GitHubUser;
import life.draymond.community.mapper.UserMapper;
import life.draymond.community.model.User;
import life.draymond.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    public UserMapper userMapper;

    @Transactional
    public String createOrUpdate(GitHubUser gitHubUser) {
        UserExample userExample=new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(String.valueOf(gitHubUser.getId()));
        List<User> dbUser =userMapper.selectByExample(userExample);
        User user=new User();
        if(dbUser.size() == 0){
            String token= UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setName(gitHubUser.getName());
            user.setAvatarUrl(gitHubUser.getAvatar_url());
            user.setGmtModified(System.currentTimeMillis());
            user.setGmtCreate(System.currentTimeMillis());
            userMapper.insert(user);
            return  token;
        }else {
            //更新
            dbUser.get(0).setGmtModified(System.currentTimeMillis());
            dbUser.get(0).setAvatarUrl(gitHubUser.getAvatar_url());
            dbUser.get(0).setName(gitHubUser.getName());
            dbUser.get(0).setToken( UUID.randomUUID().toString());
            userMapper.updateByPrimaryKeySelective(dbUser.get(0));
            return dbUser.get(0).getToken();
        }
    }
}
