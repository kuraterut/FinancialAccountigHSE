Financial Accounting System for HSE API

Это Spring Boot приложение для управления банковскими счетами. 
Оно предоставляет RESTful API
для создания, редактирования, удаления и просмотра банковских счетов,
категорий и операций.


Требования

Для запуска проекта вам понадобится:

    Java 17+

    Maven 3.6+

    Docker (опционально)

    PostgreSQL (если запускаете без Docker)

Локальный запуск
Сборка и запуск без Docker

    Клонируйте репозиторий:

    git clone https://github.com/kuraterut/FinancialAccountingHSE.git
    cd FinancialAccountingHSE

    Настройте базу данных:

        Убедитесь, что у вас установлена и запущена PostgreSQL.

        Создайте базу данных financialaccounting:

        CREATE DATABASE financialaccounting;

        Настройте подключение к базе данных в application.properties:

        spring.datasource.url=jdbc:postgresql://localhost:5432/financialaccounting
        spring.datasource.username=user
        spring.datasource.password=password

    Соберите проект:

    ./mvnw clean package

    Запустите приложение:

    java -jar target/FinancialAccountigHSE-0.0.1-SNAPSHOT.jar

    
Запуск с Docker

    Клонируйте репозиторий:

    git clone https://github.com/kuraterut/FinancialAccountigHSE.git
    cd TaskManagementSystemAPI

    Соберите проект:

    ./mvnw clean package

    Запустите Docker Compose:

    docker-compose up --build

    Проверка:

        Откройте браузер и перейдите по адресу http://localhost:8080.

        Используйте Swagger UI для тестирования API: http://localhost:8080/swagger-ui/index.html.

Использование API

API предоставляет следующие endpoints:
Авторизация (Auth)

    POST /api/auth/login — LogIn по email и паролю и получение токена.

    POST /api/auth/register — Регистрация нового пользователя.

Далее все эндпоинты будут делится на две категории
```/api/admin - Доступно только пользователям с ролью ROLE_ADMIN```
и
```/api/user - Доступно только пользователям с ролью ROLE_USER```,
Доступ проверяется через JWT-токен

Задачи (Tasks)

    GET /api/admin/tasks — получить список задач.

    GET /api/admin/tasks/{id} — получить задачу по ID.

    POST /api/admin/tasks — создать новую задачу.

    PUT /api/admin/tasks/{id} — обновить задачу.

    DELETE /api/admin/tasks/{id} — удалить задачу.

    GET /api/admin/tasks/executor?executorId={executorId} — получить задачи по ID исполнителя

    GET /api/admin/tasks/author?authorId={authorId} — получить задачи по ID автора
    
    GET /api/admin/tasks/status?status={status} — получить задачи по статусу

    GET /api/admin/tasks/priority?priority={priority} — получить задачи по приоритету

    PUT /api/admin/tasks/{taskId}/executor?executorId={executorId} — добавить исполнителя к задаче

    DELETE /api/admin/tasks/{taskId}/executor?executorId={executorId} — Открепить исполнителя от задачи
    
    PUT /api/user/tasks/{taskId}/status?status={status} — Обновление статуса

    GET /api/user/tasks — получить список задач, где user является исполнителем.

    GET /api/user/tasks/{taskId} — получить информацию о задаче(Проверяются права доступа).

Комментарии (Comments)

    GET /api/admin/comments — получить список комментариев.

    GET /api/admin/comments/{id} — получить комментарий по ID.

    POST /api/admin/comments — создать новый комментарий.

    PUT /api/admin/comments/{id} — обновить комментарий.

    DELETE /api/admin/comments/{id} — удалить комментарий.

    GET /api/admin/comments/author?authorId={authorId} — получить список комментариев по id автора.

    GET /api/admin/comments/task?taskId={taskId} — получить список комментариев по id задачи.

    POST /api/user/comments — создать новый комментарий.

    GET /api/user/comments — получить список комментариев, где user - автор.

    GET /api/user/comments/{id} — получить комментарий по ID(Проверка доступа).

    PUT /api/user/comments/{id} — обновить комментарий(Проверка доступа).

    DELETE /api/suer/comments/{id} — удалить комментарий(Проверка доступа).

Пользователи (Users):

    GET /api/admin/users - получить всех пользователей(email и роль).

    GET /api/admin/users/{userId} - получить информацию о пользователе по ID.

    GET /api/admin/users/email?email={email} - получить информацию о пользователе по Email.


Подробную документацию можно найти в Swagger UI: http://localhost:8080/swagger-ui/index.html.
Настройка
Настройка базы данных

    Для локального запуска настройте подключение к PostgreSQL в application.properties.

    Для Docker Compose настройки базы данных задаются в docker-compose.yml.

Логирование

    Логи сохраняются в директорию logs (локально) или /app/logs (в Docker).

    Настройки логирования можно изменить в logback-spring.xml.

Технологии

    Java 17 — язык программирования.

    Spring Boot — фреймворк для создания приложений.

    PostgreSQL — база данных.

    Docker — контейнеризация приложения.

    Swagger и OpenAPI — документация API.

    Logback — логирование.
