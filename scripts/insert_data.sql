-- INSERT clients...
INSERT INTO public.clients(first_name, last_name, date_of_birth)
    VALUES ('Thomas', 'Crane', '01/01/1979');
INSERT INTO public.clients(first_name, last_name, date_of_birth)
    VALUES ('Alden', 'Cantrell', '01/05/1954');
INSERT INTO public.clients(first_name, last_name, date_of_birth)
    VALUES ('Cierra', 'Vega', '01/04/1972');
INSERT INTO public.clients(first_name, last_name, date_of_birth)
    VALUES ('Miranda', 'Shaffer', '02/15/1963');
INSERT INTO public.clients(first_name, last_name, date_of_birth)
    VALUES ('Bradyn', 'Kramer', '09/13/1980');
INSERT INTO public.clients(first_name, last_name, date_of_birth)
    VALUES ('Alvaro', 'Mcgee', '01/04/1973');


-- INSERT users...
INSERT INTO public.users(first_name, last_name, trigram)
    VALUES ('Mounir', 'DRIOUCHI', 'MD3431');
INSERT INTO public.users(first_name, last_name, trigram)
    VALUES ('Philippe', 'Istas', 'PBI');
INSERT INTO public.users(first_name, last_name, trigram)
    VALUES ('Gregory', 'Delespes', 'AUDGED');
INSERT INTO public.users(first_name, last_name, trigram)
    VALUES ('Philippe', 'Foguenne', 'NRBPHF');

-- Insert user_client
INSERT INTO public.user_client(user_id, client_id)
    VALUES (1, 1);
INSERT INTO public.user_client(user_id, client_id)
    VALUES (1, 2);
INSERT INTO public.user_client(user_id, client_id)
    VALUES (2, 3);
INSERT INTO public.user_client(user_id, client_id)
    VALUES (2, 4);
INSERT INTO public.user_client(user_id, client_id)
    VALUES (3, 1);
INSERT INTO public.user_client(user_id, client_id)
    VALUES (3, 4);
INSERT INTO public.user_client(user_id, client_id)
    VALUES (4, 2);
INSERT INTO public.user_client(user_id, client_id)
    VALUES (4, 4);