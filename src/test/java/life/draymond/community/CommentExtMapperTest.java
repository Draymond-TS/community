package life.draymond.community;

import life.draymond.community.mapper.CommentExtMapper;
import life.draymond.community.model.Comment;
import org.junit.Test;

public class CommentExtMapperTest {
    CommentExtMapper commentExtMapper;

    @Test
    public void test1(){
        Comment comment=new Comment();
        comment.setId(7l);
        comment.setCommentCount(1);
        commentExtMapper.incCommentCountForComment(comment);
    }
}
