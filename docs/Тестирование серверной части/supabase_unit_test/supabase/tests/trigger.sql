begin;
select plan(2); 

SELECT function_returns('check_worker_on_shift', 'trigger');
SELECT function_returns('task_update_cells', 'trigger');
SELECT function_returns('update_task_completed', 'trigger');
SELECT function_returns('update_weight_product_in_cell', 'trigger');

select * from finish();
rollback;
