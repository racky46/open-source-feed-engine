--
-- Populate t_feed_direction
--
insert into t_feed_direction (feed_direction_id, description) values ("inbound", "Defines that the feed file is coming in from the partner.");
insert into t_feed_direction (feed_direction_id, description) values ("outbound", "Defines that the feed file is being sent to the partner.");

--
-- Populate t_feed_file_state
--
insert into t_feed_file_state (feed_file_state_id, description) values ("completed", "The feed file life cycle was completed successfully.");
insert into t_feed_file_state (feed_file_state_id, description) values ("failed", "The feed file life cycle was not completed successfully.");
insert into t_feed_file_state (feed_file_state_id, description) values ("processing", "The feed file is currently being processed.");
insert into t_feed_file_state (feed_file_state_id, description) values ("rejected", "A feed that has failed cannot be repaired and is thus permanently rejected.");
insert into t_feed_file_state (feed_file_state_id, description) values ("retry", "The feed file is waiting to be reprocessed because of a previous failure.");

--
-- Populate t_feed_job_state
--
insert into t_feed_job_state (feed_job_state_id, description) values ("active", "The Active state is the initial stat of a feed job. All feed jobs, by default, start in the Active state.");
insert into t_feed_job_state (feed_job_state_id, description) values ("completed", "The feed processing life cycle completed successfully. A feed job can only transition to the Completed state from an Active state.");
insert into t_feed_job_state (feed_job_state_id, description) values ("failed", "When the feed file life cycle does not complete successfully, the feed job must transition to the Failed state. A feed job in a Failed state can only transition to the Resolved state.");
insert into t_feed_job_state (feed_job_state_id, description) values ("resolved", "A feed job is in a Failed state and has been resolved. This can only happen if the feed job is in a Failed state.");

--
-- Populate t_feed_protocol
--
insert into t_feed_protocol (feed_protocol_id, description) values ("request", "Defines the feed as a request to be procecessed.");
insert into t_feed_protocol (feed_protocol_id, description) values ("response", "Defines a respose feed to a previously processed request feed.");

--
-- Populate t_feed_role
--
insert into t_feed_role (feed_role_id, role_name, description) values (200, 'User', 'Ability to query data and run reports');
insert into t_feed_role (feed_role_id, role_name, description) values (300, 'Data Manager', 'Ability to manage user managed tables');
insert into t_feed_role (feed_role_id, role_name, description) values (400, 'Feed Manager', 'Ability to manage the processing of feeds');
insert into t_feed_role (feed_role_id, role_name, description) values (500, 'Administrator', 'Ability to do everything');
