ALTER TABLE `system_user` ADD INDEX  system_user_index_name(`name`);
ALTER TABLE `question` ADD INDEX  question_index_sequence(`sequence`);
ALTER TABLE `answer` ADD INDEX  answer_index_sequence(`sequence`);
ALTER TABLE `client_info` ADD INDEX  client_info_index_macaddress(`mac_address`);
ALTER TABLE `client_result` ADD INDEX  client_result_index_macaddress(`mac_address`);
ALTER TABLE `client_result` ADD INDEX  client_result_index_examination(`examination_id`);


