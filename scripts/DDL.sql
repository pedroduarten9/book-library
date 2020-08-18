create table author (id uuid not null, created_date timestamp, last_modified_date timestamp, status varchar(255) not null, name varchar(255), primary key (id));
create table author_books (author_id uuid not null, books_id uuid not null, primary key (author_id, books_id));
create table book (id uuid not null, created_date timestamp, last_modified_date timestamp, status varchar(255), description varchar(255), isbn varchar(255), title varchar(255), author_id uuid not null, primary key (id));
alter table if exists author_books add constraint UK_fxksjqa1a5dnqf0egcdxlrcna unique (books_id);
alter table if exists author_books add constraint FKr514ej8rhei197wx3nrvp0qie foreign key (books_id) references book;
alter table if exists author_books add constraint FKfvabqdr9njwv4khjqkf1pbmma foreign key (author_id) references author;
alter table if exists book add constraint FKklnrv3weler2ftkweewlky958 foreign key (author_id) references author;
