use vimeo;

insert into role(code,name) values('ROLE_ADMIN','Quan tri');
insert into role(code,name) values('ROLE_USER','Nguoi dung');

insert into user(user_name,password,full_name, avatar) 
values('admin','$2a$10$/RUbuT9KIqk6f8enaTQiLOXzhnUkiwEJRdtzdrMXXwU7dgnLKTCYG','admin', '/asset/private/user/tom.png');

INSERT INTO user_role(user_id,role_id) VALUES (1,1);


DELIMITER $$  
  
CREATE TRIGGER before_delete_user 
BEFORE DELETE  
ON user FOR EACH ROW  
BEGIN  
    delete from user_role where user_role.user_id = OLD.`id`;
    delete from video where video.createdby = OLD.`id`;
    delete from `comment` where `comment`.createdby = OLD.`id`;
    delete from emotion where emotion.createdby = OLD.`id`;
END$$   
  
DELIMITER ; 

DELIMITER $$  
  
CREATE TRIGGER before_delete_video
BEFORE DELETE  
ON video FOR EACH ROW  
BEGIN  
    delete from `comment` where `comment`.video_id = OLD.`id`;
     delete from emotion where emotion.video_id = OLD.`id`;
END$$   
  
DELIMITER ; 
