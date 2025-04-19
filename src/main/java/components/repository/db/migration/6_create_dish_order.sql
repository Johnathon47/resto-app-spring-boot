CREATE TABLE public.dish_order (
    id BIGINT PRIMARY KEY,
    dish_id BIGINT NOT NULL,
    quantitytoorder NUMERIC NOT NULL,
    orderid BIGINT NOT NULL,
    price NUMERIC NOT NULL,
    status order_dish_status NOT NULL DEFAULT 'CREATED',
    FOREIGN KEY (dish_id) REFERENCES public.dish(id),
    FOREIGN KEY (orderid) REFERENCES public.order(id)
);
