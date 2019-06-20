package life.draymond.community.controller;

import life.draymond.community.Provider.GitHubProvider;
import life.draymond.community.dto.GitHubUser;
import life.draymond.community.dto.AccesstokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {
    @Autowired
    GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSerrect;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String  callback(@RequestParam(name="code") String  code ,
                            @RequestParam(name="state")String state,
                            HttpServletRequest request) {
        AccesstokenDTO accesstokenDTO=new AccesstokenDTO();
        accesstokenDTO.setClient_id(clientId);
        accesstokenDTO.setClient_secret(clientSerrect);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirectUri);
        accesstokenDTO.setState(state);
        //将accessToken携带code传给GitHub,等待github返回一个access_token
        String accessToken = gitHubProvider.getAccessToken(accesstokenDTO);

        //请求得到user对象
        GitHubUser user = gitHubProvider.getUser(accessToken);

        //授权成功则将user对象传到session当中去
        if(user!=null){
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else{
            return "redirect:/";
        }

    }
}
