CREATE TABLE public.stock_movement (
    id BIGINT PRIMARY KEY,
    ingredient_id BIGINT NOT NULL,
    movement_type movement_type NOT NULL,
    quantity NUMERIC NOT NULL,
    unit VARCHAR(255),
    movement_date TIMESTAMP WITHOUT TIME ZONE,
    FOREIGN KEY (ingredient_id) REFERENCES public.ingredient(id)
);
