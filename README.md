# Эмулятор распределенной системы

Данный проект представляет собой эмулятор распределенной системы. 

Для запуска необходимы следующие инструменты:

- intelij idea в качестве среды разработки
- брокер сообщений apache kafka (скачать можно тут https://kafka.apache.org/downloads, инструкция по установке - https://habr.com/ru/companies/slurm/articles/719540/)
- postman для удобного тестирования приложения https://www.postman.com/downloads/

## Шаги по запуску

1. Запустить кафку, выполнив следующие команды в терминале:
- ./bin/zookeeper-server-start.sh config/zookeeper.properties
- ./bin/kafka-server-start.sh config/server.properties
2. Открыть приложение и перейти в класс SimulatorApplication 
3. Нажать на зеленую стрелку слева от названия класса и выбрать run. После этого приложение запуститься и будет готово к использованию

## Тестирование

Открыть postman. Выбрать метод post в качестве http-запроса.

- url: http://localhost:8080/api/messages/0
- body, формат json: 

	- для рассылки всем узлам по кругу
{
"message":  "--all --start 256 --division 4",
"sendToNeighbors":  false
}

	- для смены функции
	{
"message":  "switch",
"sendToNeighbors":  false
}

	- для рассылки сообщения соседям
		{
"message":  "message",
"sendToNeighbors":  true
}

	- для рассылки сообщения соседям со сменой функции (и у соседей тоже)
		{
"message":  "switch",
"sendToNeighbors":  true
}
