ALTER TABLE DYEXTN_CONTAINER MODIFY CAPTION varchar(800);
ALTER TABLE DYEXTN_CONTROL MODIFY CAPTION varchar(800);
ALTER TABLE DYEXTN_CONTROL ADD (HEADING varchar(255));
CREATE TABLE DYEXTN_FORM_CTRL_NOTES (
   IDENTIFIER bigint not null auto_increment, 
   NOTE varchar(255), 
   FORM_CONTROL_ID bigint, 
   INSERTION_ORDER integer, 
   primary key (IDENTIFIER)
);
ALTER TABLE DYEXTN_FORM_CTRL_NOTES add index FK7A0DA06B41D885B1 (FORM_CONTROL_ID), add constraint FK7A0DA06B41D885B1 foreign key (FORM_CONTROL_ID) references DYEXTN_CONTROL (IDENTIFIER);
ALTER TABLE DYEXTN_USERDEFINED_DE ADD (IS_ORDERED boolean default true);
