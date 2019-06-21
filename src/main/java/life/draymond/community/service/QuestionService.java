package life.draymond.community.service;

import life.draymond.community.dto.QuestionDTO;
import life.draymond.community.mapper.QuestionMapper;
import life.draymond.community.mapper.UserMapper;
import life.draymond.community.model.Question;
import life.draymond.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private  QuestionMapper questionMapper;

    public List<QuestionDTO> list(){
        List<QuestionDTO> questionDTOList =new ArrayList<QuestionDTO>();
        List<Question> questionList = questionMapper.list();
        for(Question question: questionList){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            questionDTO.setUser(user);
            BeanUtils.copyProperties(question,questionDTO);
            questionDTOList.add(questionDTO);
        }

        return questionDTOList;
    }
}
