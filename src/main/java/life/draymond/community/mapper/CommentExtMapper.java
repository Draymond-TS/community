package life.draymond.community.mapper;

import life.draymond.community.model.Comment;
import org.apache.ibatis.annotations.Mapper;


public interface CommentExtMapper {
   int incCommentCountForComment(Comment comment);
}