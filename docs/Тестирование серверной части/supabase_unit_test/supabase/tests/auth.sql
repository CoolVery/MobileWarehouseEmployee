begin;
select plan(3); 

SELECT is(id, '4202d2e7-5164-4cd1-aa16-e38815846b7c', 'id должен быть равен 4202d2e7-5164-4cd1-aa16-e38815846b7c')     
FROM auth.users
WHERE phone = '79290509332'
AND encrypted_password ='$2a$10$V6z25.1LqNpQNsa6Vomgp.m8U/.UC743rp1FJ3yhvxtweqaV.FEDO';

SELECT is_empty(
  'SELECT id FROM auth.users WHERE phone = '' AND encrypted_password = ''',
  'Запрос должен быть пустым'
);

SELECT isnt(id, '4202d2e7-5164-4cd1-aa16-e38815846b7c', 'id НЕ должен быть равен 4202d2e7-5164-4cd1-aa16-e38815846b7c')     
FROM auth.users
WHERE phone = '79290509329'
AND encrypted_password ='$2a$10$/4dEFK05Rq/zJNinADjvVuUdRGMONL7keKfROj6xWIsW67Z4ocAiO';  

select * from finish();
rollback;