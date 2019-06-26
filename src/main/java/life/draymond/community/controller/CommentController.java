package life.draymond.community.controller;


import life.draymond.community.dto.CommentCreateDTO;
import life.draymond.community.dto.CommentDTO;
import life.draymond.community.dto.ResultDTO;
import life.draymond.community.enums.CommentTypeEnum;
import life.draymond.community.exception.CustomizeErrorCode;
import life.draymond.community.exception.CustomizeException;
import life.draymond.community.model.Comment;
import life.draymond.community.model.User;
import life.draymond.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment=new Comment();
        comment.setCommentator(user.getId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setCommentCount(0);
        commentService.insert(comment,user);

        return ResultDTO.okOf();
    }



    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
