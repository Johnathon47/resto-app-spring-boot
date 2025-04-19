CREATE TABLE public.order (
    id BIGINT PRIMARY KEY,
    tablenumber table_number,
    amountpaid NUMERIC NOT NULL,
    amountdue NUMERIC NOT NULL,
    customerarrivaldatetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status order_dish_status NOT NULL DEFAULT 'CREATED'
);
