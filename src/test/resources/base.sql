create table customers (id integer not null AUTO_INCREMENT, name varchar(255), primary key (id));
create table vendors (id integer not null AUTO_INCREMENT, name varchar(255), primary key (id));
create table vendors_customers (vendor_fk integer not null, customer_fk integer not null);
alter table vendors_customers add constraint UK_i81f65k2ncdjhmgwhv5u7u3s unique (customer_fk);
alter table vendors_customers add constraint FKt86ns08yqegbes25psv2j9gu7 foreign key (customer_fk) references customers;
alter table vendors_customers add constraint FKkp2pe59wcc4bwamat3egpg5fm foreign key (vendor_fk) references vendors;

INSERT INTO vendors (name) VALUES ('Jetbrains');
INSERT INTO vendors (name) VALUES ('Google');
INSERT INTO vendors (name) VALUES ('Oracle');
INSERT INTO customers (name) VALUES ('Brian');
INSERT INTO customers (name) VALUES ('Dan');
INSERT INTO customers (name) VALUES ('Steve');
INSERT INTO customers (name) VALUES ('Stephen');

INSERT INTO vendors_customers (vendor_fk, customer_fk) VALUES (1,1);
INSERT INTO vendors_customers (vendor_fk, customer_fk) VALUES (1,2);
INSERT INTO vendors_customers (vendor_fk, customer_fk) VALUES (1,3);
INSERT INTO vendors_customers (vendor_fk, customer_fk) VALUES (2,4);

select * from Customers as c where c.id in
      (
        SELECT customer_fk from vendors_customers
        WHERE vendor_fk IN
              (
                select id from vendors WHERE vendors.name = 'Jetbrains'
              )
      );