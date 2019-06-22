package life.draymond.community.service;

import life.draymond.community.dto.PaginationDTO;
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

    public PaginationDTO list(Integer page, Integer size){

        //size*(page-1)
        Integer offset=size*(page-1);
        List<Question> questionList = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList =new ArrayList<QuestionDTO>();
        PaginationDTO paginationDTO=new PaginationDTO();

        for(Question question: questionList){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            questionDTO.setUser(user);
            BeanUtils.copyProperties(question,questionDTO);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);



        return paginationDTO;
    }

    public PaginationDTO list(Long id, Integer page, Integer size) {

        User user=userMapper.findById(id);

        //size*(page-1)
        Integer offset=size*(page-1);
        List<Question> questionList = questionMapper.listById(id,offset,size);
        List<QuestionDTO> questionDTOList =new ArrayList<QuestionDTO>();
        PaginationDTO paginationDTO=new PaginationDTO();

        for(Question question: questionList){
            QuestionDTO questionDTO=new QuestionDTO();
            questionDTO.setUser(user);
            BeanUtils.copyProperties(question,questionDTO);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        Integer totalCountQuestionById = questionMapper.countQuestionById(id);
        paginationDTO.setPagination(totalCountQuestionById,page,size);

        return paginationDTO;

    }

    public QuestionDTO getById(Long id) {
        Question question=questionMapper.getById(id);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.findById(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
    }
}
