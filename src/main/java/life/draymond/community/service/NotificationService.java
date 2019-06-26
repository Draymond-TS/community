package life.draymond.community.service;

import life.draymond.community.dto.NotificationDTO;
import life.draymond.community.dto.PaginationDTO;
import life.draymond.community.dto.QuestionDTO;
import life.draymond.community.enums.NotificationStatusEnum;
import life.draymond.community.enums.NotificationTypeEnum;
import life.draymond.community.exception.CustomizeErrorCode;
import life.draymond.community.exception.CustomizeException;
import life.draymond.community.mapper.NotificationMapper;
import life.draymond.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        //size*(page-1)
        Integer offset=size*(page-1);
        NotificationExample  notificationExample=new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);

        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));

        List<NotificationDTO> questionDTOList =new ArrayList<NotificationDTO>();

        PaginationDTO<NotificationDTO> paginationDTO=new PaginationDTO();

        for(Notification notification: notifications){
           NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
           notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            questionDTOList.add(notificationDTO);
       }

        paginationDTO.setData(questionDTOList);

        NotificationExample notificationExample1= new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount = notificationMapper.countByExample(notificationExample1);
        paginationDTO.setPagination(totalCount,page,size);


        return paginationDTO;

    }

    public Integer unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);

    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
