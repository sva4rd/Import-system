# Import System

## Описание
Реализация импорта из старой системы в новую систему. 
Импортируются текстовые заметки.  
Текстовая заметка делается конкретным пользователем (user) для конкретного пациента 
(clients в Старой системе и patients в Новой системе). 

### Old System
Имитация REST API для получения данных из старой системы.

Создана база данных по требованиям задания.
![image](https://github.com/user-attachments/assets/b871901c-21c6-42de-a374-02bf258ab7cb)

Реализовано два enpoints:
- /cliets - возвращает всех клиентов бд
- /notes - возвращает все заметки клиента

Подробнее о запросах можно узнать на `http://localhost:8080/OldSystem/swagger-ui/index.html` после запуска приложения.

### New System
Приложение для импорта заметок из старой системы.

Создана база данных по требованиям задания.
![image](https://github.com/user-attachments/assets/8eef3b95-dd55-4feb-8b86-14c08a17beb1)

Реализован сервис для импортирования данных из старой системы по заданному расписанию.

Реализовано логирования статистики, процесса импортирования и ошибок.


## Запуск
1. Docker

Для запуска при помощи Docker достаточно выполнить команду `docker-compose up`, после чего развернуться контейнеры с приложениями и базами данных. Требуется убедиться, что порты 8080, 8082, 3306, 3307 свободны.

2. Локальный запуск по отдельности

  * Требуется создать базы данных `old_system_db` и `new_system_db` в MySQL
  * Настроить подключение в `../src/main/resources/application.propeties` по примеру
  * Выполнить скрипты `../scripts/init.sql` по созданию таблиц и заполнению тестовыми данными для соответствующих баз данных.
  * Запустить приложения

## Дополнительно
Дополнительные данные и условие тестового задания смотреть в папке [docs](/docs).

