package life.draymond.community.mapper;

import life.draymond.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) " +
                                "values " +
                                "(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    //@Insert("insert into user (name) values (#{name})")
    void insert(User user);
}
