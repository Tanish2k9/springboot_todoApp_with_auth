package com.tanish.Task_Springboot.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {
    private Long id;
    private String Content;
    private Date createdAt;
    private Long taskId;
    private Long userId;
    private String postedBy;
}
