# Форум API

## Описание

Движок форума предоставляет API для создания и управления темами (топиками) и сообщениями в этих темах. Реализованы функции для CRUD операций, что позволяет пользователям добавлять, просматривать, редактировать и удалять темы и сообщения. 

### Основные особенности:
- **Хранение данных в БД H2**: легковесная, встраиваемая база данных, идеально подходящая для разработки и тестирования.
- **CRUD операции с топиками и сообщениями**: API поддерживает создание, чтение, обновление и удаление тем и сообщений.
- **Global Exception Handler**: для обработки исключений и ошибок, возникающих при работе с API.
- **Swagger UI**: интерактивная документация API, доступная для тестирования эндпоинтов.

## База данных H2

- **URL**: `http://localhost:8080/h2-console`
- **Логин**: `sa`
- **Пароль**: *(оставьте поле пустым, если не настраивали)*

## Эндпоинты

### Топики
- **Получить все топики**: `GET /api/topics/getAllTopics`
- **Создать топик**: `POST /api/topics/createTopic`

### Сообщения
- **Получить сообщения в топике**: `GET /api/topics/{topicId}/messages`
- **Создать сообщение в топике**: `POST /api/topics/{topicId}/createMessage`
- **Обновить сообщение**: `PUT /api/topics/messages/{messageId}/update`
- **Удалить сообщение**: `DELETE /api/topics/messages/{messageId}/delete`

## Swagger UI

Для удобства работы и тестирования API предоставляется Swagger UI.

- **URL**: `http://localhost:8080/swagger-ui.html`
