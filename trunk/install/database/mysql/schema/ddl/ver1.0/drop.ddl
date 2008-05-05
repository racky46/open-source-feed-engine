alter table t_feed drop foreign key t_fromData_source_t_feed;
alter table t_feed_file drop foreign key t_feed_t_feed_file;
alter table t_feed_job drop foreign key t_feed_file_t_feed_job;
alter table t_feed_file drop foreign key t_feed_file_state_t_feed_file;
alter table t_feed_job drop foreign key t_feed_job_state_t_feed_job;
alter table t_feed_retrieve drop foreign key t_feed_retrieve_state_t_feed_retrieve;
alter table t_feed drop foreign key t_feed_type_t_feed;
alter table t_feed drop foreign key t_toData_source_t_feed;
alter table t_feed drop foreign key t_feed_protocol_t_feed;
alter table t_feed_queue drop foreign key t_feed_t_feed_queue;
alter table t_feed_retrieve drop foreign key t_feed_t_feed_retrieve;
alter table t_feed drop foreign key t_feed_group_t_feed;
alter table t_feed drop foreign key t_feed_direction_t_feed;
alter table t_checkpoint drop foreign key FKt_checkpoi105827;
drop table if exists t_feed_type;
drop table if exists t_feed_retrieve_state;
drop table if exists t_feed_retrieve;
drop table if exists t_feed_protocol;
drop table if exists t_feed_job_state;
drop table if exists t_feed_job;
drop table if exists t_feed_file_state;
drop table if exists t_feed_file;
drop table if exists t_feed;
drop table if exists t_error_log;
drop table if exists t_data_source;
drop table if exists t_feed_direction;
drop table if exists t_feed_queue;
drop table if exists t_feed_group;
drop table if exists t_checkpoint;

