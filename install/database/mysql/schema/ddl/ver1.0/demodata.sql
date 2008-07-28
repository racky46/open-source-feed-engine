--
-- Create Demo Data Sources
--
insert into t_feed_data_source (feed_data_source_id, description) values ('acme', 'Acme is a test data source');
insert into t_feed_data_source (feed_data_source_id, description) values ('qagen', 'QAGen Testing Organization');

--
-- Create Demo Feed Group
--
insert into t_feed_group (feed_group_id, allow_concurrent_runs, allow_failed_state_runs, collect_phase_stats) values ('test_group', '1', '1', '1');

--
-- Create Demo Feed Queue
--
insert into t_feed_queue_type (feed_queue_type_id, max_concurrent_runs, description) values ('test_queue', 0, 'testing feeds only');

--
-- Create Demo Feed Types
--
insert into t_feed_type (feed_type_id, description) values ('testd', 'Test Delimited Feed File');
insert into t_feed_type (feed_type_id, description) values ('testf', 'Test Fixed Feed File');
insert into t_feed_type (feed_type_id, description) values ('testx', 'Test Complex XML Feed File');

--
-- Create Demo Feed Definitions
--
insert into t_feed (feed_id, activation_date, termination_date, allow_concurrent_runs, allow_failed_state_runs, restart_at_checkpoint, collect_phase_stats, feed_directory, feed_document, max_concurrent_runs, last_sequence_number, from_data_source_id, to_data_source_id, feed_type_id, feed_protocol_id, feed_direction_id, feed_group_id, feed_queue_type_id) values ('acme_qagen_testd_request', '2008-01-01 12:00:00', '2009-01-01 12:00:00', '1', '0', '1', '1', 'feed/acme/qagen/testd/request', 'partnerConfig/acme/qagen/testd/request', 1, 0, 'acme', 'qagen', 'testd', 'request', 'inbound', 'test_group', 'test_queue');
insert into t_feed (feed_id, activation_date, termination_date, allow_concurrent_runs, allow_failed_state_runs, restart_at_checkpoint, collect_phase_stats, feed_directory, feed_document, max_concurrent_runs, last_sequence_number, from_data_source_id, to_data_source_id, feed_type_id, feed_protocol_id, feed_direction_id, feed_group_id, feed_queue_type_id) values ('acme_qagen_testf_request', '2008-01-01 12:00:00', '2009-01-01 12:00:00', '1', '0', '1', '1', 'feed/acme/qagen/testf/request', 'partnerConfig/acme/qagen/testf/request', 1, 0, 'acme', 'qagen', 'testf', 'request', 'inbound', 'test_group', 'test_queue');
insert into t_feed (feed_id, activation_date, termination_date, allow_concurrent_runs, allow_failed_state_runs, restart_at_checkpoint, collect_phase_stats, feed_directory, feed_document, max_concurrent_runs, last_sequence_number, from_data_source_id, to_data_source_id, feed_type_id, feed_protocol_id, feed_direction_id, feed_group_id, feed_queue_type_id) values ('acme_qagen_testx_request', '2008-01-01 12:00:00', '2009-01-01 12:00:00', '1', '0', '1', '1', 'feed/acme/qagen/testx/request', 'partnerConfig/acme/qagen/testx/request', 1, 0, 'acme', 'qagen', 'testx', 'request', 'inbound', 'test_group', 'test_queue');
