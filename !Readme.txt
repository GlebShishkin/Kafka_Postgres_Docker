    -- Источник. Статья "Apache Kafka. Пишем простой producer и consumer и тестируем их"":
    https://habr.com/ru/articles/742786/
	https://github.com/mista1984gmail/kafka-article/blob/master/pom.xml

	Суть: postman делает json запрос в producer -> передает json в Kafka -> consumer читает из Kafka и сохраняет в Postgres

    часть docker-compose для kafka взят из https://hub.docker.com/r/apache/kafka

	--------------------------------------------------------------------------

    -- посылка запроса из Postman в Producer:
    http://localhost:8081/api/v1/orders
    {
        "productName": "book",
        "barCode": "000004",
        "quantity": 5,
        "price": "4.99"
    }

	--------------------------------------------------------------------------
	-- пример запуска docker образа apache/kafka без docker-compose
	docker run -d -p 9092:9092 apache/kafka
	docker ps
	-- зайдем внутрь контейнера, откуда можно запускать *.sh команды
	docker exec -it bca6298c1e2d sh	
	cd opt/kafka/bin
	sh kafka-metadata-quorum.sh --bootstrap-server localhost:9092 describe --status

	--------------------------------------------------------------------------
	--  пример запуска через docker-compose
	docker-compose up -d
	docker ps

	-- переход в командную строку docker для запуска команд в нем:
	docker exec -it a7052a8605b7 sh
	cd opt/kafka/bin
	-- Статус - !!! 19092 из KAFKA_LISTENERS:
    sh kafka-metadata-quorum.sh --bootstrap-server localhost:19092 describe --status

    -- список топиков:
    sh kafka-topics.sh --list --bootstrap-server localhost:19092
	
	-- создание топика:
	a) из терминала без захода в контейнер:
	docker exec -it a7052a8605b7 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:19092 --topic sandbox --create --partitions 3 --replication-factor 2
	    или с использование имени одного из контейнеров:
    docker exec -it broker-1 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:19092 --topic sandbox --create --partitions 3 --replication-factor 2

	b) из контейнера после захода в него в терминале через docker exec (!!!заходить нужно в контейнер типа apache/kafka)
	sh kafka-topics.sh --bootstrap-server localhost:19092 --topic sandbox --create --partitions 3 --replication-factor 2

    c) через docker-compose.yml с помощью временного контейнера "topics-generator"

	-- статус топика:
	sh kafka-topics.sh --bootstrap-server localhost:19092 --topic sandbox --describe

	-- отправка сообщения в топик:
	sh kafka-console-producer.sh --bootstrap-server localhost:19092 --topic sandbox

	-- получение сообщения (в новом окне терминала):
	sh kafka-console-consumer.sh --bootstrap-server localhost:19092 --topic sandbox

	-- чтение сначала из 2-ой партиции:
	sh kafka-console-consumer.sh --bootstrap-server localhost:19092 --topic sandbox --offset 0 --partition 2
	
	-- удаление топика - т.к. нет команд, кот полностью очищали бы содержимое топика, проще его удалить и создать
	a) из терминала без захода в контейнер:
	docker exec -it a7052a8605b7 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:19092 --topic sandbox --delete
	b) из контейнера после захода в него в терминале через docker exec (!!!заходить нужно в контейнер типа apache/kafka)
	sh kafka-topics.sh --bootstrap-server localhost:19092 --topic sandbox --delete

--------------------------------		
kafka-delete-records --bootstrap-server localhost:19092 --topic sandbox --offset-json-file offsets.json

kafka-topics --bootstrap-server localhost:19092 --topic sandbox --delete