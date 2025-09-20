package com.emeka.service

import com.emeka.constants.SYSTEM_USER_DATA
import com.emeka.enums.AccountType
import com.emeka.transaction.Transaction
import com.tigerbeetle.*
import java.math.BigInteger

class AccountingService(
    private val client: Client
) {

    private fun ensureAccount(accountType: AccountType): Long {
        val accountId = accountType.id

        val lookupBatch = IdBatch(1)
        lookupBatch.add(accountType.id)

        val existingAccounts = client.lookupAccounts(lookupBatch)

        if (existingAccounts.length > 0) {
            return accountId
        }

        val accounts = AccountBatch(1)
        accounts.add()
        accounts.setId(accountId)
        accounts.ledger = accountType.ledger
        accounts.code = accountType.code
        accounts.userData64 = accountType.userData64
        accounts.flags = AccountFlags.NONE

        val result = client.createAccounts(accounts)
        if (result.length > 0) {
            result.next()
            throw RuntimeException("Error creating account: ${result.result}")
        }

        return accountId
    }

    fun transfer(transaction: Transaction, amount: BigInteger) {

        val singleTransfer = TransferBatch(1)
        singleTransfer.add()
        singleTransfer.id = UInt128.id()
        singleTransfer.setDebitAccountId(ensureAccount(transaction.getDebitAccount()))
        singleTransfer.setCreditAccountId(ensureAccount(transaction.getCreditAccount()))
        singleTransfer.amount = amount
        singleTransfer.ledger = transaction.getDebitAccount().ledger
        singleTransfer.setUserData128(SYSTEM_USER_DATA)
        singleTransfer.code = transaction.getTransferCode()

        val result = client.createTransfers(singleTransfer)
        if (result.length > 0) {
            result.next()
            throw RuntimeException("Error making transfer: ${result.result}")
        }
    }

}