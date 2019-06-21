package life.draymond.community.dto;

import com.sun.xml.internal.bind.v2.model.core.ID;
import lombok.Data;
import org.springframework.beans.factory.annotation.Required;

import java.io.Serializable;

/*DTO（Data Transfer Object）：数据传输对象，这个概念来源于J2EE的设计模式，原来的目的是为了EJB的分布式应用提供粗粒度的数据实体，
以减少分布式调用的次数，从而提高分布式调用的性能和降低网络负载，但在这里，我泛指用于展示层与服务层之间的数据传输对象。

将Accesstoken请求时候的参数封装成一个对象，传给GitHub
* */
@Data
public class AccesstokenDTO implements Serializable {
   private String  client_id	 ;
   private String  client_secret	 ;
   private String  code	 ;
   private String  redirect_uri;
   private String  state;


}
