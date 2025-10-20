CREATE TABLE mtc_internet_overview(
        id            varchar(12) PRIMARY KEY,
        report_data    text,
        internet_id varchar(12) NOT NULL,
        type varchar(12) NOT NULL,
        create_time  timestamp
);
CREATE TABLE mtc_ku_overview(
          id            varchar(12) PRIMARY KEY,
          report_data    text,
          ku_id varchar(12) NOT NULL,
          ku_item_id varchar(12) NOT NULL,
          type varchar(12) NOT NULL,
          create_time  timestamp
);

