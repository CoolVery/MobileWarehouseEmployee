begin;
select plan(4); 

SELECT is(CAST(count(*) as int), 3, 'Запрос должен вернуть 3 строки') 
FROM public.workers_work_shifts
WHERE id_work_shift = (SELECT id FROM public.work_shifts WHERE date_shift = current_date AND id_warehouse = 1);

SELECT is(CAST(count(*) as int), 3, 'Запрос должен вернуть 3 строки')
FROM public.tasks_workers 
WHERE id_worker = '024b4df6-0695-44ac-9d52-c1a3d3be4771' 
AND id_task IN (
    SELECT id 
    FROM public.tasks 
    WHERE date_trunc('day', date_execution_task) = '2024-10-30'
);

SELECT is(CAST(count(*) as int), 2, 'Запрос должен вернуть 3 строки')
FROM public.cells 
WHERE id IN (
    SELECT id_cell
    FROM public.tasks_products 
    WHERE id_task = 4
);

SELECT is(CAST(count(*) as int), 2, 'Запрос должен вернуть 3 строки')
FROM public.products 
WHERE id IN (
    SELECT id_product
    FROM public.tasks_products 
    WHERE id_task = 4
);

select * from finish();
rollback;

