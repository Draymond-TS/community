package life.draymond.community.controller;

import life.draymond.community.dto.QuestionDTO;
import life.draymond.community.mapper.QuestionMapper;
import life.draymond.community.mapper.UserMapper;
import life.draymond.community.model.User;
import life.draymond.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    User user = userMapper.findByToken(cookie.getValue());
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionDTOList=questionService.list();
        model.addAttribute("questions",questionDTOList);
        return "index";
    }

    @GetMapping("/aa")
    public String indexaa(HttpServletRequest request,
                        Model model) {
        List<QuestionDTO> questionDTOList=questionService.list();

        return "index";
    }

}
