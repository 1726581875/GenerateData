
-- 创建学生表
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`
(
    `id`           bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `name`         varchar(20)     NOT NULL COMMENT '学生名字',
    `sex`   tinyint             NOT NULL COMMENT '性别|1男、2女',
	`age`  int  NOT NULL COMMENT '年龄',
    `status`       tinyint         NOT NULL DEFAULT 1 COMMENT '状态|1正常、2已删除',
    `create_time`  datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT charset = utf8mb4 COMMENT ='学生表';
  

-- 创建课程表
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`
(
    `id`           bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `name`         varchar(200)     NOT NULL COMMENT '课程名',
    `teacher_id`   bigint             NOT NULL COMMENT '学生id',
    `status`       tinyint         NOT NULL DEFAULT 1 COMMENT '状态|1正常、2已删除',
    `create_time`  datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT charset = utf8mb4 COMMENT ='课程表';
 
 
-- 创建选课表
  DROP TABLE IF EXISTS `sc`;
CREATE TABLE `sc`
(
    `id`           bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `student_id`   bigint             NOT NULL COMMENT '教师id',
    `course_id`   bigint             NOT NULL COMMENT '课程id',
    `grade`       int         NOT NULL COMMENT '分数',
    `create_time`  datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT charset = utf8mb4 COMMENT ='选课表';



  

