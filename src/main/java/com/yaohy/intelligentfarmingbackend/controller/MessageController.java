package com.yaohy.intelligentfarmingbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.mapper.UserMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.*;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.StickParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.UserMessageParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.*;
import com.yaohy.intelligentfarmingbackend.service.UserMessageImageService;
import com.yaohy.intelligentfarmingbackend.service.UserMessageService;
import com.yaohy.intelligentfarmingbackend.service.UserMessageTypeService;
import com.yaohy.intelligentfarmingbackend.service.UserService;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import com.yaohy.intelligentfarmingbackend.util.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
public class MessageController {

    @Autowired
    private UserMessageImageService userMessageImageService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMessageTypeService userMessageTypeService;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private HttpServletRequest request;


    @App
    @Web
    @RequestMapping(value = "/user/messages", method = RequestMethod.GET)
    public CommonResult<UserMessageListResult> getAll() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        UserMessageListResult userMessageListResult = new UserMessageListResult();

        List<UserMessageListResult.UserMessageResult> userMessageResults = new ArrayList<>();
        UserMessage sonList1 = userMessageService.getOne(new QueryWrapper<UserMessage>().select("max(id) as id").eq("to_id", userId).eq("type_id", 1).groupBy("from_id", "to_id"));
        if (Objects.isNull(sonList1)){
            UserMessageListResult.UserMessageResult userMessageResult = new UserMessageListResult.UserMessageResult();
//            UserMessage mall = userMessageService.getOne(new QueryWrapper<UserMessage>().eq("type_id", 1).last("limit 1"));
//            User mallInfo = userService.getOne(new QueryWrapper<User>().eq("id", mall.getFromId()));
            userMessageResult.setId(0);
            userMessageResult.setCurrentUserId(userId);
            userMessageResult.setUserId(0);
            userMessageResult.setImgUrl("商城地址");
            userMessageResult.setNickname("商城名称");
            userMessageResult.setType(1);
            userMessageResult.setContent("");
            userMessageResult.setTime(0L);
            userMessageResult.setStick(1);
            userMessageResult.setDisabled(1);
            userMessageResults.add(userMessageResult);
        }else {
            UserMessage mallMessage = userMessageService.getOne(new QueryWrapper<UserMessage>().select("id", "from_id", "to_id", "type_id", "content", "time", "stick", "disabled", "state").in("id",sonList1));
            UserMessageListResult.UserMessageResult userMessageResult = new UserMessageListResult.UserMessageResult();
            User mallInfo = userService.getOne(new QueryWrapper<User>().eq("id", mallMessage.getFromId()));
            userMessageResult.setId(mallMessage.getId());
            userMessageResult.setCurrentUserId(userId);
            userMessageResult.setUserId(0);
            userMessageResult.setImgUrl(mallInfo.getImgUrl());
            userMessageResult.setNickname(mallInfo.getNickname());
            userMessageResult.setType(mallMessage.getTypeId());
            userMessageResult.setContent(mallMessage.getContent());
            userMessageResult.setTime(mallMessage.getTime());
            userMessageResult.setStick(mallMessage.getStick());
            userMessageResult.setDisabled(mallMessage.getDisabled());
            userMessageResults.add(userMessageResult);
        }

        List<UserMessage> sonList2 = userMessageService.list(new QueryWrapper<UserMessage>().select("max(id) as id").eq("to_id", userId).eq("type_id", 2).ne("state", -1).groupBy("from_id", "to_id"));

        for (UserMessage userMessage : sonList2) {
            UserMessage userMessages2 = userMessageService.getOne(new QueryWrapper<UserMessage>().eq("id",userMessage.getId()));

            UserMessageListResult.UserMessageResult userMessageResult2 = new UserMessageListResult.UserMessageResult();

            User user = userService.getOne(new QueryWrapper<User>().eq("id", userMessages2.getFromId()));

            userMessageResult2.setId(userMessages2.getId());
            userMessageResult2.setCurrentUserId(userId);
            userMessageResult2.setUserId(user.getId());
            userMessageResult2.setNickname(user.getNickname());
            userMessageResult2.setImgUrl(user.getImgUrl());
            userMessageResult2.setStick(userMessages2.getStick());
            userMessageResult2.setDisabled(userMessages2.getDisabled());
            userMessageResult2.setContent(userMessages2.getContent());
            userMessageResult2.setTime(userMessages2.getTime());
            userMessageResult2.setType(userMessages2.getTypeId());
            userMessageResults.add(userMessageResult2);
        }
        userMessageListResult.setUserMessageResults(userMessageResults);

        List<UserMessage> count = userMessageService.list(new QueryWrapper<UserMessage>().select("max(id) as id", "from_id, to_id").eq("to_id", userId).eq("state",0).groupBy("from_id", "to_id"));
        userMessageListResult.setCount(count.size());

        return CommonResult.success(userMessageListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public CommonResult<MessageListResult> getAll(@RequestParam("id") Integer id) {
        List<UserMessage> userMessageList = userMessageService.list(new QueryWrapper<UserMessage>().eq("id", id));

        List<MessageResult> messageResults = userMessageList.stream().map(userMessage -> {
            MessageResult messageResult = new MessageResult();

            messageResult.setId(userMessage.getId());
            messageResult.setFromId(userMessage.getFromId());
            messageResult.setToId(userMessage.getToId());

            UserMessageType userMessageType = userMessageTypeService.getById(userMessage.getTypeId());
            messageResult.setType(userMessageType.getType());
            messageResult.setContent(userMessage.getContent());

            UserMessageImage userMessageImage = userMessageImageService.getOne(new QueryWrapper<UserMessageImage>().eq("user_message_id", userMessage.getId()).last("limit 1"));
            messageResult.setUrl(userMessageImage.getUrl());
            messageResult.setTime(userMessage.getTime());

            return messageResult;
        }).collect(Collectors.toList());

        MessageListResult messageListResult = new MessageListResult();
        messageListResult.setMessageResultList(messageResults);
        return CommonResult.success(messageListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/conversation", method = RequestMethod.GET)
    public CommonResult<ConversationListResult> getMessage(@RequestParam("id") Integer id) {

//        UserMessage id1 = userMessageService.getOne(new QueryWrapper<UserMessage>().eq("id", id));

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<UserMessage> list = userMessageService.list(new QueryWrapper<UserMessage>().eq("from_id", id).eq("to_id", userId));
        for (UserMessage userMessage : list) {
            userMessage.setState(1);
        }

        userMessageService.updateBatchById(list);

        List<ConversationListResult.ConversationResult> collect = userMessageService.list(new QueryWrapper<UserMessage>().and(i -> i.eq("from_id",id).eq("to_id",userId)).or(i->i.eq("from_id",userId).eq("to_id",id)).orderByAsc("time")).stream()

                .map(userMessage -> {

                    ConversationListResult.ConversationResult conversationResult = new ConversationListResult.ConversationResult();

                    User fromUser = userService.getOne(new QueryWrapper<User>().eq("id", userMessage.getFromId()));
                    User toUser = userService.getOne(new QueryWrapper<User>().eq("id", userMessage.getToId()));

                    conversationResult.setUserId(userId);

                    conversationResult.setFromId(userMessage.getFromId());
                    conversationResult.setFromName(fromUser.getNickname());
                    conversationResult.setFromUrl(fromUser.getImgUrl());

                    conversationResult.setToId(userMessage.getToId());
                    conversationResult.setToName(toUser.getNickname());
                    conversationResult.setToUrl(toUser.getImgUrl());

                    conversationResult.setContent(userMessage.getContent());

                    UserMessageType type = userMessageTypeService.getOne(new QueryWrapper<UserMessageType>().eq("id", userMessage.getTypeId()));
                    if (type != null){
                        conversationResult.setType(type.getType());
                    }

                    UserMessageImage userMessageImage = userMessageImageService.getOne(new QueryWrapper<UserMessageImage>().eq("user_message_id", userMessage.getId()));
                    if (userMessageImage != null){
                        conversationResult.setImgUrl(userMessageImage.getUrl());
                    }

                    conversationResult.setTime(userMessage.getTime());


                    return conversationResult;

                }).collect(Collectors.toList());

        ConversationListResult conversationListResult = new ConversationListResult();
        conversationListResult.setConversationResultList(collect);

        return CommonResult.success(conversationListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public CommonResult<NoData> addMessage(@RequestBody UserMessageParam userMessageParam, @RequestBody MultipartFile... file) {

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        UserMessage userMessage = new UserMessage();

        userMessage.setFromId(userId);
        userMessage.setToId(userMessageParam.getToId());
        userMessage.setTypeId(2);
        userMessage.setContent(userMessageParam.getContent());
        userMessage.setTime(new Date().getTime());
        userMessage.setState(0);

        UserMessage um = userMessageService.getOne(new QueryWrapper<UserMessage>().eq("from_id", userId).eq("to_id", userMessageParam.getToId()).last("limit 1"));

        if (um != null){
            userMessage.setStick(um.getStick());
            userMessage.setDisabled(um.getDisabled());
        }else{
            userMessage.setStick(0);
            userMessage.setDisabled(0);
        }

        userMessageService.save(userMessage);

//        Integer userMessageId = userMessage.getId();
//        if (file != null){
//            for (MultipartFile multipartFile : file) {
//                UserMessageImage userMessageImage = new UserMessageImage();
//                userMessageImage.setUserMessageId(userMessageId);
//                userMessageImageService.save(userMessageImage);
//
//                String name = "/message/"+userMessageImage.getId()+".jpeg";
//                userMessageImage.setUrl("https://image.yaohy.cn/intelligent-farming"+name);
//                userMessageImageService.updateById(userMessageImage);
//                MinioUtil.uploadJpg(multipartFile,name);
//            }
//        }

        return CommonResult.success("发送消息成功");
    }

    @App
    @Web
    @RequestMapping(value = "/stick", method = RequestMethod.POST)
    public CommonResult<NoData> stick(@RequestBody StickParam stickParam) {

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        UserMessage um = userMessageService.getOne(new QueryWrapper<UserMessage>().eq("id", stickParam.getId()));

        List<UserMessage> list = userMessageService.list(new QueryWrapper<UserMessage>().eq("from_id", um.getFromId()).eq("to_id", userId));
        for (UserMessage message : list) {
            message.setStick(stickParam.getStick());
        }

        userMessageService.updateBatchById(list);

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value = "/message", method = RequestMethod.PUT)
    public CommonResult<NoData> deleteMessage(@RequestParam Integer id) {

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        UserMessage userMessage = userMessageService.getOne(new QueryWrapper<UserMessage>().eq("id", id));

        userMessageService.update(new UpdateWrapper<UserMessage>().eq("from_id",userMessage.getFromId()).eq("to_id",userId).set("state",-1));

        return CommonResult.success("true");
    }

}
