CREATE TABLE question (
  id  INT  AUTO_INCREMENT  PRIMARY KEY ,
  title  varchar(50) ,
  description  text  ,
  gmt_create  bigint  ,
  gmt_modified  bigint  ,
  creator  INT  ,
  comment_count  INT  DEFAULT 0 ,
  view_count  INT  DEFAULT 0 ,
  like_count  INT  DEFAULT 0 ,
  tag  varchar(256)
);

