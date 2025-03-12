Financial Accounting System for HSE API

Это Spring Boot приложение для управления банковскими счетами. 
Оно предоставляет RESTful API
для создания, редактирования, удаления и просмотра банковских счетов,
категорий и операций, а также экспортирования и импортирования данных.


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

        Настройте подключение к базе данных в application-local.properties(предварительно создав его):

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
    cd FinancialAccountigHSE

    Соберите проект:

    ./mvnw clean package

    Запустите Docker Compose:

    docker-compose up --build

    
Использование API

API предоставляет следующие endpoints:

Банковские счета (Bank Account)

    POST /api/bank-account — создать новый счет.
    
    GET /api/bank-account — получить список счетов.

    GET /api/bank-account/{id} — получить счет по ID.

    PUT /api/bank-account/{id} — обновить счет по ID.

    DELETE /api/bank-account/{id} — удалить счет по ID.

    POST /api/bank-account/export/csv — экспортировать в .csv информацию о счетах в базе.

    GET /api/bank-account/import/csv — импортировать из .csv информацию о счетах в базу. 

    POST /api/bank-account/export/xml — экспортировать в .xml информацию о счетах в базе.

    GET /api/bank-account/import/xml — импортировать из .xml информацию о счетах в базу. 

Категории (Category)

    POST /api/category — создать новую категорию.
    
    GET /api/category — получить список категорий.

    GET /api/category/{id} — получить категорию по ID.

    GET /api/category/type — получить категорию по типу категории.

    PUT /api/category/{id} — обновить категорию по ID.

    DELETE /api/category/{id} — удалить категорию по ID.

    POST /api/category/export/csv — экспортировать в .csv информацию о категориях в базе.

    GET /api/category/import/csv — импортировать из .csv информацию о категориях в базу. 

    POST /api/category/export/xml — экспортировать в .xml информацию о категориях в базе.

    GET /api/category/import/xml — импортировать из .xml информацию о категориях в базу. 


Операции (Operation):

    POST /api/operation — создать новую операцию.
    
    GET /api/operation — получить список операций.

    GET /api/operation/{id} — получить операцию по ID.

    GET /api/operation/type — получить операцию по типу операции.

    GET /api/operation/category — получить операцию по ID категории.
    
    GET /api/operation/bank-account — получить операцию по ID банковского счета.

    GET /api/operation/bank-account — получить операцию по ID банковского счета.

    GET /api/operation/date-time/equal — получить список операций по конкретной дате и времени.

    GET /api/operation/date-time/before — получить список операций до конкретной даты и времени.

    GET /api/operation/date-time/after — получить список операций после конкретной даты и времени.

    GET /api/operation/date-time/between — получить список операций между конкретными датами и временем.

    GET /api/operation/amount/equal — получить список операций по конкретной сумме.

    GET /api/operation/amount/greater — получить список операций >= конкретной суммы.

    GET /api/operation/amount/less — получить список операций <= конкретной суммы.

    GET /api/operation/amount/between — получить список операций между конкретными суммами.

    POST /api/operation/export/csv — экспортировать в .csv информацию о операциях в базе.

    GET /api/operation/import/csv — импортировать из .csv информацию о операциях в базу. 

    POST /api/operation/export/xml — экспортировать в .xml информацию о операциях в базе.

    GET /api/operation/import/xml — импортировать из .xml информацию о операциях в базу. 

По пути ```exports/``` можно найти json файл для импорта в Postman и протестировать все эндпоинты

Настройка

Настройка базы данных
        

    Для локального запуска настройте подключение к PostgreSQL: создайте application-local.properties и пропишите данные 
    конфигурации: spring.datasource.url, spring.datasource.username, spring.datasource.password.

    Для Docker Compose настройки базы данных задаются в docker-compose.yml.


Технологии

    Java 17 — язык программирования.

    Spring Boot — фреймворк для создания приложений.

    PostgreSQL — база данных.

    Docker — контейнеризация приложения.

Пояснения:
SOLID:
    
    S - Например классы сущностей отвечает каждый за свой род деятельности.
    Классы репозитории отвечают за взаимодействие с БД, и т.д.

    O - Например Классы-контроллеры, если хотим добавить новую бизнес-логику за новым эндпоинтом, добавляем новый метод,
    т.е. расширяем класс а не меняем его.

    L - В этом проекте лишь небольшие примеры, например с интерфейсом List вместо кокнретной реализации - ArrayList.

    I - Интерфейсов здесь практически нет, кроме Validation - разделять его не приходится.

    D - Внедрение зависимостей с помощью Spring и @Autowired

GRASP:

    Низкая связность достигается за счет независимости работы классов, т.е. они никак не связаны друг с другом, Сущности - как сущности,
    Репозитории - для взаимодействия с БД, Сервисы - для бизнес логики, Контроллеры для обработки HTTP запросов.

    Высокое зацепление в свою очередь достигается фокусированием ответственности каждого класса

GoF:

    Паттерн Builder - реализован в BankAccount, применен в BankAccountMapper для построения BankAccount

    Паттерн Facade - Контроллеры можно назвать фасадом, они скрывают реализацию бизнес логики.

    Паттер Adapter - Реализован адаптер для экспорта и имепорта в и из CSV и XML файлы.
    
    Паттерн Bridge - Реализован в Validation - интерфейс и его реализации с единственным методом - validate()

    Паттерн Proxy - Реализован как DTO, Например в Operation, В контроллере приниматеся bankAccountId, а потом
    происходит поиск и внедрение самого объкта BankAccount в Service. Это позволяет предварительно проверить
    существование BankAccount с таким ID прежде чем соединить его с Operation.

DI-контейнер:

    Реализован как Spring Context

Тесты:
    
    Реализованы некоторые тесты для BankAccount с использованием Mockito и Junit.
    Также реализованы все запросы, как сказано выше, в exports находится JSON для Postman, его можно импортировать
    и проверить.