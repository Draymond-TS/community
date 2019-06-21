package life.draymond.community.mapper;

import life.draymond.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) " +
                                "values " +
                                "(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where user.token = #{token}")
    User findByToken(@Param(value="token") String token);

    @Select("select * from user where user.ID = #{Id}")
    User findById(@Param(value="Id") Long Id);
}
