LOAD DATA INFILE 'H://caTissue//work//workspace//catissuecoreNew/SQL/DBUpgrade/Common/CAModelCSVs/DYEXTN_PERMISSIBLE_VALUE.csv' 
APPEND 
INTO TABLE DYEXTN_PERMISSIBLE_VALUE 
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
(IDENTIFIER NULLIF IDENTIFIER='\\N',DESCRIPTION NULLIF DESCRIPTION='\\N',ATTRIBUTE_TYPE_INFO_ID NULLIF ATTRIBUTE_TYPE_INFO_ID='\\N',USER_DEF_DE_ID NULLIF USER_DEF_DE_ID='\\N')