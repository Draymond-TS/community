package life.draymond.community.Provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class UCloudProvider {
    @Value("${ucloud.ufile.public-key}")
    private String publicKey;
    @Value("${ucloud.ufile.private-key}")
    private String privateKey;

    public String upload(InputStream fileStream, String mimeType, String fileName) {
        String generatedFileName;
        String [] filePaths=fileName.split("\\.");
        if(filePaths.length>1){
            generatedFileName=UUID.randomUUID().toString() +"."+filePaths[1];
        }else{
            return null;
        }

        try{
            //授权对象，参数是公钥和密钥
            ObjectAuthorization objectAuthorization=new UfileObjectLocalAuthorization(publicKey,privateKey);
            //服务器的域名对应的参数
            ObjectConfig config = new ObjectConfig("cn-bj", "ufileos.com");
            PutObjectResultBean response= UfileClient.object(objectAuthorization,config)
                    .putObject(fileStream,mimeType)
                    .nameAs(generatedFileName)
                    .toBucket("draymond")
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener((bytesWritten,contentLength)->{

                    })
                    .execute();
        }catch (UfileClientException e) {
            e.printStackTrace();
            return null;
        } catch (UfileServerException e) {
            e.printStackTrace();
            return null;
        }

        return generatedFileName;
    }

}