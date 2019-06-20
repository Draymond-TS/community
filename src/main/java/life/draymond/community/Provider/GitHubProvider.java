package life.draymond.community.Provider;

import com.alibaba.fastjson.JSON;
import life.draymond.community.dto.AccesstokenDTO;
import life.draymond.community.dto.GitHubUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class GitHubProvider {
    //将accessToken携带code传给GitHub,等待github返回一个access_token
    public String getAccessToken(AccesstokenDTO accesstokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accesstokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String access_tokenOrigin = response.body().string();
            String access_tokenTrue = access_tokenOrigin.split("&")[0].split("=")[1];
            return access_tokenTrue;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public GitHubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https:api.github.com/user?access_token="+accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String userStr =response.body().string();
            GitHubUser user=JSON.parseObject(userStr,GitHubUser.class);
            return user;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }


}
