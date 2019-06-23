CREATE TABLE question (
  id  bigint  AUTO_INCREMENT  PRIMARY KEY ,
  title  varchar(50) ,
  description  text  ,
  gmt_create  bigint  ,
  gmt_modified  bigint  ,
  creator  bigint  ,
  comment_count  INT  DEFAULT 0 ,
  view_count  INT  DEFAULT 0 ,
  like_count  INT  DEFAULT 0 ,
  tag  varchar(256)
);

