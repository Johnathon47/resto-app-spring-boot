CREATE TABLE public.dish_ingredient (
    id_dish BIGINT NOT NULL,
    id_ingredient BIGINT NOT NULL,
    required_quantity NUMERIC,
    unit VARCHAR(255),
    PRIMARY KEY (id_dish, id_ingredient),
    FOREIGN KEY (id_dish) REFERENCES public.dish(id) ON DELETE CASCADE,
    FOREIGN KEY (id_ingredient) REFERENCES public.ingredient(id)
);
