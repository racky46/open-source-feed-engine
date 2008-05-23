create table t_feed_type (feed_type_id varchar(25) not null, description varchar(250) not null, constraint PK_t_feed_type primary key (feed_type_id)) ENGINE=INNODB;
create table t_feed_retrieve_state (feed_retrieve_state_id varchar(15) not null, description varchar(250) not null, constraint PK_t_feed_retrieve_state primary key (feed_retrieve_state_id)) ENGINE=INNODB;
create table t_feed_retrieve (feed_retrieve_id int not null auto_increment, feed_retrieve_date date not null, feed_retrieve_time time not null, full_feed_file_name varchar(100), feed_id varchar(60), feed_retrieve_state_id varchar(15), constraint PK_t_feed_retrieve primary key (feed_retrieve_id)) ENGINE=INNODB;
create table t_feed_protocol (feed_protocol_id varchar(10) not null, description varchar(250) not null, constraint PK_t_feed_protocol primary key (feed_protocol_id)) ENGINE=INNODB;
create table t_feed_job_state (feed_job_state_id varchar(10) not null, description varchar(250) not null, constraint PK_t_feed_job_state primary key (feed_job_state_id)) ENGINE=INNODB;
create table t_feed_job (feed_job_id int not null auto_increment, processing_start timestamp not null, processing_end timestamp, failed_row_number int, failure_code varchar(40), failure_message varchar(5000), feed_file_id int not null, feed_job_state_id varchar(10) not null, constraint PK_t_feed_job primary key (feed_job_id)) ENGINE=INNODB;
create table t_feed_file_state (feed_file_state_id varchar(10) not null, description varchar(250) not null, constraint PK_t_feed_file_state primary key (feed_file_state_id)) ENGINE=INNODB;
create table t_feed_file (feed_file_id int not null auto_increment, feed_file_date date, feed_file_time time, records_processed int, records_rejected int, feed_file_name varchar(255) unique, feed_document varchar(255) not null, feed_id varchar(60), feed_file_state_id varchar(10), constraint PK_t_feed_file primary key (feed_file_id), constraint IDX_t_feed_file_1 unique (feed_file_name)) ENGINE=INNODB;
create table t_feed (feed_id varchar(60) not null, activation_date timestamp not null, termination_date timestamp, allow_concurrent_runs tinyint(1) not null, allow_failed_state_runs tinyint(1) not null, restart_at_checkpoint tinyint(1) not null, collect_phase_stats tinyint(1) not null, feed_directory varchar(255) not null, feed_document varchar(255) not null, queue_id varchar(60), max_concurrent_runs int default 1 not null, last_sequence_number int, from_data_source_id varchar(20) not null, to_data_source_id varchar(20) not null, feed_type_id varchar(25) not null, feed_protocol_id varchar(10) not null, feed_direction_id varchar(10), feed_group_id varchar(60), constraint PK_t_feed primary key (feed_id)) ENGINE=INNODB;
create table t_error_log (error_log_id int not null auto_increment, time_stamp datetime not null, error_type_id varchar(30), message varchar(5000), feed_job_id int, constraint PK_t_error_log primary key (error_log_id)) ENGINE=INNODB;
create table t_data_source (data_source_id varchar(20) not null, description varchar(250) not null, constraint PK_t_data_source primary key (data_source_id)) ENGINE=INNODB;
create table t_feed_direction (feed_direction_id varchar(10) not null, description varchar(250), primary key (feed_direction_id)) ENGINE=INNODB;
create table t_feed_queue (feed_queue_id int not null auto_increment, queue_id varchar(40) not null, monitor_id int not null, entry_time datetime not null, feed_file_name varchar(255) not null, feed_id varchar(60) not null, constraint PK_t_feed_mapped_queue primary key (feed_queue_id));
create table t_feed_group (feed_group_id varchar(60) not null, allow_concurrent_runs tinyint(1) not null, allow_failed_state_runs tinyint(1) not null, collect_phase_stats tinyint(1) not null, primary key (feed_group_id)) ENGINE=INNODB;
create table t_checkpoint (checkpoint_id int not null auto_increment, phase_id varchar(60) not null, current_file_index int not null, feed_file_id int not null, primary key (checkpoint_id)) ENGINE=INNODB;
create table t_feed_phase_stats (feed_phase_stats_id int not null auto_increment, phase_id varchar(60) not null, avg_processing_time real, total_time_in_ms int, iteration_count int, feed_file_id int not null, primary key (feed_phase_stats_id)) ENGINE=INNODB;
create table t_feed_user (feed_user_id int not null auto_increment, username varchar(30) not null unique, password varchar(32) not null, first_name varchar(30) not null, last_name varchar(30) not null, email_address varchar(50), date_created timestamp not null, date_modified timestamp not null, date_last_login timestamp not null, locked tinyint(1), feed_role_id int not null, primary key (feed_user_id)) ENGINE=INNODB;
create table t_feed_role (feed_role_id int not null, role_name varchar(30) not null, description text not null, primary key (feed_role_id)) ENGINE=INNODB;
create index idx_t_feed_file_feed_file_name on t_feed_file (feed_file_name);
alter table t_feed add index t_fromData_source_t_feed (from_data_source_id), add constraint t_fromData_source_t_feed foreign key (from_data_source_id) references t_data_source (data_source_id);
alter table t_feed_file add index t_feed_t_feed_file (feed_id), add constraint t_feed_t_feed_file foreign key (feed_id) references t_feed (feed_id);
alter table t_feed_job add index t_feed_file_t_feed_job (feed_file_id), add constraint t_feed_file_t_feed_job foreign key (feed_file_id) references t_feed_file (feed_file_id);
alter table t_feed_file add index t_feed_file_state_t_feed_file (feed_file_state_id), add constraint t_feed_file_state_t_feed_file foreign key (feed_file_state_id) references t_feed_file_state (feed_file_state_id);
alter table t_feed_job add index t_feed_job_state_t_feed_job (feed_job_state_id), add constraint t_feed_job_state_t_feed_job foreign key (feed_job_state_id) references t_feed_job_state (feed_job_state_id);
alter table t_feed_retrieve add index t_feed_retrieve_state_t_feed_retrieve (feed_retrieve_state_id), add constraint t_feed_retrieve_state_t_feed_retrieve foreign key (feed_retrieve_state_id) references t_feed_retrieve_state (feed_retrieve_state_id);
alter table t_feed add index t_feed_type_t_feed (feed_type_id), add constraint t_feed_type_t_feed foreign key (feed_type_id) references t_feed_type (feed_type_id);
alter table t_feed add index t_toData_source_t_feed (to_data_source_id), add constraint t_toData_source_t_feed foreign key (to_data_source_id) references t_data_source (data_source_id);
alter table t_feed add index t_feed_protocol_t_feed (feed_protocol_id), add constraint t_feed_protocol_t_feed foreign key (feed_protocol_id) references t_feed_protocol (feed_protocol_id);
create index idx_fq_queueid_feedQueueId on t_feed_queue (queue_id, feed_queue_id);
create index idx_fq_monitorId_feedQueueId on t_feed_queue (monitor_id, feed_queue_id);
create index fk_feed_queue_queue_id on t_feed_queue (queue_id);
alter table t_feed_queue add index t_feed_t_feed_queue (feed_id), add constraint t_feed_t_feed_queue foreign key (feed_id) references t_feed (feed_id);
alter table t_feed_retrieve add index t_feed_t_feed_retrieve (feed_id), add constraint t_feed_t_feed_retrieve foreign key (feed_id) references t_feed (feed_id);
alter table t_feed add index t_feed_group_t_feed (feed_group_id), add constraint t_feed_group_t_feed foreign key (feed_group_id) references t_feed_group (feed_group_id);
alter table t_feed add index t_feed_direction_t_feed (feed_direction_id), add constraint t_feed_direction_t_feed foreign key (feed_direction_id) references t_feed_direction (feed_direction_id);
alter table t_checkpoint add index t_feed_file_t_checkpoint (feed_file_id), add constraint t_feed_file_t_checkpoint foreign key (feed_file_id) references t_feed_file (feed_file_id);
alter table t_feed_phase_stats add index t_feed_file_t_feed_phase_stats (feed_file_id), add constraint t_feed_file_t_feed_phase_stats foreign key (feed_file_id) references t_feed_file (feed_file_id);
alter table t_feed_user add index t_feed_role_t_feed_user (feed_role_id), add constraint t_feed_role_t_feed_user foreign key (feed_role_id) references t_feed_role (feed_role_id);

