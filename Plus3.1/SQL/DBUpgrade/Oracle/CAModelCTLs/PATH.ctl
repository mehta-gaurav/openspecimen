LOAD DATA INFILE 'H://caTissue//work//workspace//catissuecoreNew/SQL/DBUpgrade/Common/CAModelCSVs/PATH.csv' 
APPEND 
INTO TABLE PATH 
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
(PATH_ID NULLIF PATH_ID='\\N',FIRST_ENTITY_ID NULLIF FIRST_ENTITY_ID='\\N',INTERMEDIATE_PATH NULLIF INTERMEDIATE_PATH='\\N',LAST_ENTITY_ID NULLIF LAST_ENTITY_ID='\\N')