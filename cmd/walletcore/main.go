package main

import (
	"database/sql"
	"fmt"

	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/database"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/event"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/usecase/create_account"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/usecase/create_client"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/usecase/create_transaction"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/web"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/web/webserver"
	"github.com.br/MarcoAntonioGomes/fc-ms-wallet/pkg/events"
	_ "github.com/go-sql-driver/mysql"
)

func main() {
	db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%s)/%s?charset=utf8&parseTime=True&loc=Local", "root", "root", "mysql", "3306", "wallet"))
	if err != nil {
		panic(err)
	}
	defer db.Close()

	eventDispatcher := events.NewEventDispatcher()
	transactionCreatedEvent := event.NewTransactionCreated()

	//eventDispatcher.Register("TransactionCreated", handler)

	clientDb := database.NewClientDB(db)
	transactionDb := database.NewTransactionDB(db)
	accountDb := database.NewAccountDB(db)

	createClientUseCase := create_client.NewCreateClientUseCase(clientDb)
	createTransactionUseCase := create_transaction.NewCreateTransactionUseCase(transactionDb, accountDb, eventDispatcher, transactionCreatedEvent)
	createAccoutUserCase := create_account.NewCreateAccountUseCase(accountDb, clientDb)

	webserver := webserver.NewWebServer(":3000")
	clientHandler := web.NewWebClientHandler(*createClientUseCase)
	transactionHandler := web.NewWebTransactionHandler(*createTransactionUseCase)
	accountHandler := web.NewWebAccountHandler(*createAccoutUserCase)

	webserver.AddHandler("/clients", clientHandler.CreateClient)
	webserver.AddHandler("/transactions", transactionHandler.CreateTransaction)
	webserver.AddHandler("/accounts", accountHandler.CreateAccount)

	webserver.Start()
}
