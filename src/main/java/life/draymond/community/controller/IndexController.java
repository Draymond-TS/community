package life.draymond.community.controller;

import life.draymond.community.dto.PaginationDTO;
import life.draymond.community.mapper.UserMapper;
import life.draymond.community.model.User;
import life.draymond.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page" ,defaultValue = "1")Integer page ,
                        @RequestParam(name = "size" ,defaultValue = "2")Integer size ) {


        PaginationDTO pagination =questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }


}
