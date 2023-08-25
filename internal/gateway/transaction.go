package gateway

import "github.com.br/MarcoAntonioGomes/fc-ms-wallet/internal/entity"

type TransactionGateway interface {
	Create(transaction *entity.Transaction) error
}
