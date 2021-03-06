package life.draymond.community.service;


import life.draymond.community.dto.PaginationDTO;
import life.draymond.community.dto.QuestionDTO;
import life.draymond.community.exception.CustomizeErrorCode;
import life.draymond.community.exception.CustomizeException;
import life.draymond.community.mapper.QuestionExtMapper;
import life.draymond.community.mapper.QuestionMapper;
import life.draymond.community.mapper.UserMapper;
import life.draymond.community.model.Question;
import life.draymond.community.model.QuestionExample;
import life.draymond.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO list(Integer page, Integer size){

        //size*(page-1)
        Integer offset=size*(page-1);
        QuestionExample  questionExample=new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));

        List<QuestionDTO> questionDTOList =new ArrayList<QuestionDTO>();
        PaginationDTO<QuestionDTO> paginationDTO=new PaginationDTO();

        for(Question question: questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            questionDTO.setUser(user);
            BeanUtils.copyProperties(question,questionDTO);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);


        Integer totalCount = questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;
    }

    public PaginationDTO list(Long id, Integer page, Integer size) {
        User user=userMapper.selectByPrimaryKey(id);

        //size*(page-1)
        Integer offset=size*(page-1);
        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));


        List<QuestionDTO> questionDTOList =new ArrayList<QuestionDTO>();
        PaginationDTO<QuestionDTO> paginationDTO=new PaginationDTO();

        for(Question question: questionList){
            QuestionDTO questionDTO=new QuestionDTO();
            questionDTO.setUser(user);
            BeanUtils.copyProperties(question,questionDTO);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);

        QuestionExample questionExampleCountByUserId=new QuestionExample();
        questionExampleCountByUserId.createCriteria().andCreatorEqualTo(id);
        Integer totalCountQuestionById = questionMapper.countByExample(questionExampleCountByUserId);

        paginationDTO.setPagination(totalCountQuestionById,page,size);

        return paginationDTO;

    }


    public QuestionDTO getById(Long id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
    }

    @Transactional
    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.insert(question);
        }else {
            //更新
            question.setGmtModified(System.currentTimeMillis());
            int updated = questionMapper.updateByPrimaryKeySelective(question);
            if(updated != 1){

                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    //增加阅读数量
    public void incView(Long id) {
        Question question=new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    //相似问题推荐
    public List<QuestionDTO> selectRelated(QuestionDTO quetyDTO) {
        if(StringUtils.isBlank(quetyDTO.getTag())){
            return new ArrayList<>();
        }
        String [] tags=quetyDTO.getTag().split(",");
        String strTag = Arrays.stream(tags).collect(Collectors.joining("|"));

        Question question=new Question();
        question.setTag(strTag);
        question.setId(quetyDTO.getId());
        List<Question> questions  = questionExtMapper.selectRelated(question);

        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());


        return questionDTOS;
    }
}
