# MobileSoftwareWarehouseEmployee
### ИНСТРУКЦИЯ ЗАПУСКА ПРИЛОЖЕНИЯ

1. В GitBush ввести команду <br /> <br />
```
git clone http://gogs.ngknn.ru:3000/CoolVeryVery/MobileSoftwareWarehouseEmployee.git
```  
2. Открыть Android Studio <br /> <br />
3. Файлы - Новый - Открыть - находим и открываем папку mobileAPP <br /> <br />
4. После того, как приложения соберется, в Gradle Scripts в файл local.properties нужно вставить две строки из файла date_for_signIn_and_work_mobileApp -> supabase_key.txt <br /> <br />
НИЧЕГО ИЗ ФАЙЛА НЕ УДАЛЯТЬ, ИЗ ФАЙЛА СКОПИРОВАТЬ ДВЕ СТРОЧКИ И ВСТАВИТЬ В ФАЙЛ ПОСЛЕ sdk.dir=<br /> <br />
5. Структура данных Очень зависит от Даты, поэтому в файле date_for_signIn_and_work_mobileApp -> Work_Shift_SignIn.xlsx находим сегодшняшнюю дату и запоминаем id <br /> <br />
6. date_for_signIn_and_work_mobileApp -> Workers_Work_shift.xlsx хранит Рабочих, которые должны прийти на смену, по id_work_shift находим uuid работников, под которым хотим войти <br /> <br />
7. ДАЛЬШЕ ВЫБОР <br /> <br />
Если надо зайти под Главным на смене, то мы смотрим id_main_shift_worker в Work_Shift_SignIn.xlsx <br /> <br />
Если надо зайти под Обычным работником, то мы смотрим id_worker в Workers_Work_shift.xlsx <br /> <br />
Далее выбранный uuid работника и находим его телефон и пароль в date_for_signIn_and_work_mobileApp -> phone_and_password.txt<br /> <br />
8. ПОВТОР. Так как приложение зависит от даты, Вы можете изменить Дату (И время) на телефону и зайти в приложение под рабочими, СВЕРИВ С ДАННЫМИ В ФАЙЛАХ (ЭТО ВАЖНО)<br /> <br />

### Основные папки

1. **docs** - хранится вся документация проекта


-----


1.1 **ТЕХНИЧЕСКОЕ ЗАДАНИЕ** - ТЗ для мобильного приложения <br /> <br />
1.2 **РАЗРАБОТКА МАКЕТА ПРИЛОЖЕНИЯ** - Отчет о дизайне приложения с ссылкой на проект в Figma (ссылка: https://www.figma.com/design/I0BU5cw0tcnEDkCUU53PKI/Untitled?node-id=0-1&t=CpNYu23TgVZ4WGKr-1) <br /> <br />
1.3 **Диаграммы** - Хранятся все диаграммы проекта <br /> <br />
1.4 **СЕРВЕРНАЯ ЧАСТЬ ПРИЛОЖЕНИЯ** - Отчет о серверной части приложения <br /> <br />
1.5 **Тестирование серверной части** - Хранятся все документы о тестировании серверной части <br /> <br />
1.5.1 **ТЕСТ-ПЛАН** - Тест-план сервеной части приложения <br /> <br />
1.5.2 **ТЕСТОВЫЙ НАБОР, ТЕСТ-КЕЙСЫ, UNIT-ТЕСТЫ** - Отчет о тестовых наборах, тест-кейсах и unit-тестах<br /> <br />
1.5.3 **supabase_unit_test** - Хранятся unit-тесты для БД через Supabase CLI <br /> <br />
1.6 **Тестирование мобильного приложения** - Хранится все документы о тестировании мобильного приложения<br /> <br />
1.6.1 **ТЕСТ-ПЛАН** - Тест-план мобильного приложения <br /> <br />
1.6.2 **ТЕСТОВЫЙ НАБОР, ТЕСТ-КЕЙСЫ, UI-ТЕСТЫ, UNIT-ТЕСТЫ** - Отчет о тестовых наборах, тест-кейсах, ui-тесты, unit-тестах<br /> <br />


-----


2. **mobileApp** - хранится мобильное приложене 


-----


3. **date_for_signIn_and_work_mobileApp** - Файлы для входа и работы мобильного приложения


-----


3.1 **phone_and_password.txt** - хранит все телефоны, пароли, uuid пользователей, которые могут войти в приложение <br /> <br />
3.2 **supabase_key.txt** - хранит ключи для подключения к supabase <br /> <br />
3.3 **Work_Shift_SignIn.xlsx** - хранит записи из БД о рабочих сменах <br /> <br />
3.4 **Workers_Work_shift.xlsx** - хранит записи из БД о работниках, которые должны прийти на смену <br /> <br />


-----


4. **final_defense** - хранит итоговый отчет и итоговую презентацию