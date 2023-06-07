insert into USERS (ID, PASSWORD, USERNAME, ENABLED)
VALUES (0, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'Jefferson Restani', true);

insert into USERS (ID, PASSWORD, USERNAME, ENABLED)
VALUES (1, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'SistemasNow', true);
insert into USERS (ID, PASSWORD, USERNAME, ENABLED)
VALUES (2, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'iLoveCanecas', true);

insert into USERS (ID, PASSWORD, USERNAME, ENABLED)
VALUES (3, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'Adriana de Carvalho', true);
insert into USERS (ID, PASSWORD, USERNAME, ENABLED)
VALUES (4, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'Guilherme Nicoleti', true);
insert into USERS (ID, PASSWORD, USERNAME, ENABLED)
VALUES (5, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'Gabriel Reguera', true);

insert into AUTHORITIES (ID, AUTHORITY)
VALUES (0, 'ROLE_ANONYMOUS');
insert into AUTHORITIES (ID, AUTHORITY)
VALUES (1, 'ROLE_ADMIN');
insert into AUTHORITIES (ID, AUTHORITY)
VALUES (2, 'ROLE_USER');

insert into USERS_AUTHORITIES (USER_ID, AUTHORITY_ID)
VALUES (1, 1);
insert into USERS_AUTHORITIES (USER_ID, AUTHORITY_ID)
VALUES (1, 2);
insert into USERS_AUTHORITIES (USER_ID, AUTHORITY_ID)
VALUES (2, 2);

insert into POSTS (ID, BODY, IMAGE_POST, CREATION_DATE, TITLE, USER_ID)
VALUES (1, 'As nossas coleções de canecas são tão lindas que vai ser impossível não querer todas elas. Nossas canecas são produzidas em cerâmica de alta qualidade, super brilho, resistente ao uso em forno micro-ondas e máquina de lavar louças. Orgulhamo-nos de possuir uma das melhores canecas do mercado. Tem caneca de chá, caneca pra dar de presente, caneca divertida, caneca com música e mais uma infinidade de opções para você se apaixonar e colecionar.',
        'https://cdn.colab55.com/images/55001/studio/11878/art/26933/mugs.png', '2023-04-25', 'Caneca para canhoto', 2);
insert into POSTS (ID, BODY, IMAGE_POST, CREATION_DATE, TITLE, USER_ID)
VALUES (2, 'Nossos serviços e soluções são adaptadas as suas necessidade. Cada empresa tem um perfil e um público específico. Estaremos juntos com um único objetivo, crescermos juntos.',
        'https://avmb.com.br/wp-content/uploads/2020/03/ERP-SOFTWARE-DE-GEST%C3%83O-EMPRESARIAL.jpg', '2023-04-25', 'Sistema ERP', 1);
insert into POSTS (ID, BODY, IMAGE_POST, CREATION_DATE, TITLE, USER_ID)
VALUES (3, 'Nossas canecas para mudos são as melhores! Compre já em nosso site.',
        'https://cdn.colab55.com/images/55001/studio/11878/art/26933/mugs.png', '2023-04-25', 'Caneca para mudos', 2);

insert into COMMENTS (ID, BODY, CREATION_DATE, POST_ID, USER_ID)
VALUES (1, 'Simplesmente a melhor caneca que já comprei!', current_timestamp(), 1, 0);
insert into COMMENTS (ID, BODY, CREATION_DATE, POST_ID, USER_ID)
VALUES (2, 'A caneca do Harry Styles é P-E-R-F-E-I-T-A!', current_timestamp(), 1, 4);
insert into COMMENTS (ID, BODY, CREATION_DATE, POST_ID, USER_ID)
VALUES (3, 'Amei! Vou recomendar para os amigos', current_timestamp(), 1, 3);
insert into COMMENTS (ID, BODY, CREATION_DATE, POST_ID, USER_ID)
VALUES (4, 'Up!', current_timestamp(), 2, 0);
insert into COMMENTS (ID, BODY, CREATION_DATE, POST_ID, USER_ID)
VALUES (5, 'O link do nosso site está em nosso perfil!', current_timestamp(), 2, 1);
insert into COMMENTS (ID, BODY, CREATION_DATE, POST_ID, USER_ID)
VALUES (6, 'Finalmente alguém que faça caneca de anime, perfeito!', current_timestamp(), 1, 5);


