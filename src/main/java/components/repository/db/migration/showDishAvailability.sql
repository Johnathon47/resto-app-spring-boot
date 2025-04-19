SELECT dish.name, availability, dish.unit_price FROM dish_availability
INNER JOIN dish ON dish_availability.dish_id = dish."id" ORDER BY dish."name";