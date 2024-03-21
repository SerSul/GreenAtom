## Эндпоинты API

### Получение всех топиков

- **GET** `/api/topics/getAllTopics`
- Возвращает список всех топиков.

### Создание топика

- **POST** `/api/topics/createTopic`
- Тело запроса: `{"title": "string", "message": "string"}`
- Создает новый топик с начальным сообщением.

### Получение сообщений по ID топика

- **GET** `/api/topics/{topicId}/messages`
- Возвращает список сообщений для указанного топика.

### Создание сообщения в топике

- **POST** `/api/topics/messages/{topicId}/createMessages`
- Тело запроса: `{"text": "string"}`
- Добавляет новое сообщение в указанный топик.

### Редактирование сообщения

- **PUT** `/api/topics/messages/{messageId}/update`
- Тело запроса: `{"text": "string"}`
- Обновляет содержимое указанного сообщения.

### Удаление сообщения

- **DELETE** `/api/topics/messages/{messageId}/delete`
- Удаляет указанное сообщение.
