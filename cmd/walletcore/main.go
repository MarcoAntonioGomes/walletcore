package main

import (
	"context"
	"database/sql"
	"fmt"

	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/database"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/event"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/event/handler"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/usecase/create_account"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/usecase/create_client"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/usecase/create_transaction"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/web"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/web/webserver"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/pkg/events"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/pkg/kafka"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/pkg/uow"
	ckafka "github.com/confluentinc/confluent-kafka-go/kafka"
	_ "github.com/go-sql-driver/mysql"
	"github.com/golang-migrate/migrate/v4"
	"github.com/golang-migrate/migrate/v4/database/mysql"
	_ "github.com/golang-migrate/migrate/v4/source/file"
)

func create(name string) {

	db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%s)/?charset=utf8&parseTime=True&loc=Local", "root", "root", "mysql", "3306"))
	if err != nil {
		panic(err)
	}
	defer db.Close()

	_, err = db.Exec("CREATE DATABASE IF NOT EXISTS " + name)
	if err != nil {
		panic(err)
	}
	db.Close()

}

func main() {

	create("wallet")

	db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%s)/%s?charset=utf8&parseTime=True&loc=Local", "root", "root", "mysql", "3306", "wallet"))
	if err != nil {
		fmt.Println(err)
		panic(err)
	}
	defer db.Close()

	driver, err := mysql.WithInstance(db, &mysql.Config{})
	if err != nil {
		fmt.Println(err)
	}
	m, err := migrate.NewWithDatabaseInstance(
		"file://./migrations",
		"mysql", driver)
	if err != nil {
		fmt.Println(err)
	}

	if err := m.Up(); err != nil {
		fmt.Println(err)
	}

	configMap := ckafka.ConfigMap{
		"bootstrap.servers": "kafka:29092",
		"group.id":          "wallet",
	}

	kafkaProducer := kafka.NewKafkaProducer(&configMap)

	eventDispatcher := events.NewEventDispatcher()
	eventDispatcher.Register("TransactionCreated", handler.NewTransactionCreatedKafkaHandler(kafkaProducer))
	eventDispatcher.Register("BalanceUpdated", handler.NewUpdateBalanceKafkaHandler(kafkaProducer))
	transactionCreatedEvent := event.NewTransactionCreated()
	balanceUpdatedEvent := event.NewBalanceUpdated()

	clientDb := database.NewClientDB(db)
	accountDb := database.NewAccountDB(db)

	ctx := context.Background()
	uow := uow.NewUow(ctx, db)

	uow.Register("AccountDB", func(tx *sql.Tx) interface{} {
		return database.NewAccountDB(db)
	})

	uow.Register("TransactionDB", func(tx *sql.Tx) interface{} {
		return database.NewTransactionDB(db)
	})

	createClientUseCase := create_client.NewCreateClientUseCase(clientDb)
	createTransactionUseCase := create_transaction.NewCreateTransactionUseCase(uow, eventDispatcher, transactionCreatedEvent, balanceUpdatedEvent)
	createAccoutUserCase := create_account.NewCreateAccountUseCase(accountDb, clientDb)

	webserver := webserver.NewWebServer(":8080")
	clientHandler := web.NewWebClientHandler(*createClientUseCase)
	transactionHandler := web.NewWebTransactionHandler(*createTransactionUseCase)
	accountHandler := web.NewWebAccountHandler(*createAccoutUserCase)

	webserver.AddHandler("/clients", clientHandler.CreateClient)
	webserver.AddHandler("/transactions", transactionHandler.CreateTransaction)
	webserver.AddHandler("/accounts", accountHandler.CreateAccount)

	fmt.Println("Server running on port 8080")
	webserver.Start()
}
