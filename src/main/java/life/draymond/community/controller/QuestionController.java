package life.draymond.community.controller;

import life.draymond.community.dto.CommentDTO;
import life.draymond.community.dto.QuestionDTO;
import life.draymond.community.enums.CommentTypeEnum;
import life.draymond.community.model.Question;
import life.draymond.community.service.CommentService;
import life.draymond.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions=questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByQuestionId(id);
        //增加阅读数量
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
