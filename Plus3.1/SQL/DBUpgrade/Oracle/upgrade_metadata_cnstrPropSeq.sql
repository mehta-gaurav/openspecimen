declare
    i number :=1; j number :=1;k number :=1;l number :=1;
    sql_stmt varchar2(150);
begin

	select nvl(max(identifier),0)+1 into i from DYEXTN_CONSTRAINTKEY_PROP;
    sql_stmt :='create sequence DYEXTN_CNSTRKEY_PROP_SEQ start with ' || i ;
    execute immediate sql_stmt;

 end;

declare
    i number :=1;
    sql_stmt varchar2(150);
begin
    select nvl(max(identifier),0)+1 into i from DYEXTN_DATABASE_PROPERTIES;
    sql_stmt :='drop sequence DYEXTN_DATABASE_PROPERTIES_SEQ';
    execute immediate sql_stmt;
    sql_stmt :='create sequence DYEXTN_DATABASE_PROPERTIES_SEQ start with ' || i;
    execute immediate sql_stmt;
end;