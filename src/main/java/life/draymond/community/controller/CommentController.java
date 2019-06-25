package life.draymond.community.controller;


import life.draymond.community.dto.CommentCreateDTO;
import life.draymond.community.dto.CommentDTO;
import life.draymond.community.dto.ResultDTO;
import life.draymond.community.exception.CustomizeErrorCode;
import life.draymond.community.exception.CustomizeException;
import life.draymond.community.model.Comment;
import life.draymond.community.model.User;
import life.draymond.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user =(User) request.getSession().getAttribute("user");

        if(user == null){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }

        Comment comment=new Comment();
        comment.setCommentator(user.getId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        commentService.insert(comment);


        return ResultDTO.okOf();
    }
}
