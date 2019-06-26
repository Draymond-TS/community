package life.draymond.community.mapper;

import life.draymond.community.dto.QuestionDTO;
import life.draymond.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question question);
    int incCommentCount(Question record);
    List<Question> selectRelated (Question question);
}
