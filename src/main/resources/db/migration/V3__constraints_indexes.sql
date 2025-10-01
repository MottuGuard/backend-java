create index idx_motos_status on motos(status);
create index idx_tags_status on uwb_tags(status);
create index idx_pr_moto_ts on position_records(moto_id,timestamp);
create index idx_meas_tag_ts on uwb_measurements(tag_id,timestamp);