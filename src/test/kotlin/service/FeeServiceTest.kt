package com.emeka.service

import com.emeka.dto.request.TransactionRequest
import com.emeka.dto.response.TransactionResponse
import com.emeka.transaction.ATMWithdrawalTransaction
import com.emeka.transaction.MobileTopUpTransaction
import com.emeka.transaction.UtilityBillPaymentTransaction
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigInteger
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FeeServiceTest {

    private lateinit var accountingService: AccountingService
    private lateinit var feeService: FeeService

    @BeforeTest
    fun setup() {
        accountingService = mockk()
        feeService = FeeService(accountingService)
    }

    @Test
    fun `chargeFee should calculate fee and call accountingService when Mobile Top Up Transaction Type is Passes`() {
        val request = TransactionRequest(
            transactionId = "txn_001",
            amount = BigInteger.valueOf(2_000_000),
            asset = "USD",
            assetType = "FIAT",
            type = "Mobile Top Up",
            state = "SETTLED - PENDING FEE",
            createdAt = "2025-08-30 15:42:17.610059"
        )

        every { accountingService.transfer(any(), any()) } returns Unit

        val transaction = MobileTopUpTransaction()

        val response: TransactionResponse = feeService.chargeFee(request)

        assertEquals(request.transactionId, response.transactionId)
        assertEquals(request.amount, response.amount)
        assertEquals(request.asset, response.asset)
        assertEquals(request.type, response.type)
        assertEquals(transaction.calculateFee(request.amount), response.fee)
        assertEquals(transaction.getRate(), response.rate)
        assertEquals(transaction.getDescription(), response.description)

        verify(exactly = 1) { accountingService.transfer(any(), any()) }
    }
    @Test
    fun `chargeFee should calculate fee and call accountingService when Utility Bill Payment Transaction Type is Passes`() {
        val request = TransactionRequest(
            transactionId = "txn_002",
            amount = BigInteger.valueOf(3_000_000),
            asset = "USD",
            assetType = "FIAT",
            type = "Utility Bill Payment",
            state = "SETTLED - PENDING FEE",
            createdAt = "2025-08-30 15:42:17.610059"
        )

        every { accountingService.transfer(any(), any()) } returns Unit

        val transaction = UtilityBillPaymentTransaction()

        val response: TransactionResponse = feeService.chargeFee(request)

        assertEquals(request.transactionId, response.transactionId)
        assertEquals(request.amount, response.amount)
        assertEquals(request.asset, response.asset)
        assertEquals(request.type, response.type)
        assertEquals(transaction.calculateFee(request.amount), response.fee)
        assertEquals(transaction.getRate(), response.rate)
        assertEquals(transaction.getDescription(), response.description)

        verify(exactly = 1) { accountingService.transfer(any(), any()) }
    }

    @Test
    fun `chargeFee should calculate fee and call accountingService when ATM Withdrawal Transaction Type is Passes`() {
        val request = TransactionRequest(
            transactionId = "txn_003",
            amount = BigInteger.valueOf(4_000_000),
            asset = "USD",
            assetType = "FIAT",
            type = "ATM Withdrawal",
            state = "SETTLED - PENDING FEE",
            createdAt = "2025-08-30 15:42:17.610059"
        )

        every { accountingService.transfer(any(), any()) } returns Unit

        val transaction = ATMWithdrawalTransaction()

        val response: TransactionResponse = feeService.chargeFee(request)

        assertEquals(request.transactionId, response.transactionId)
        assertEquals(request.amount, response.amount)
        assertEquals(request.asset, response.asset)
        assertEquals(request.type, response.type)
        assertEquals(transaction.calculateFee(request.amount), response.fee)
        assertEquals(transaction.getRate(), response.rate)
        assertEquals(transaction.getDescription(), response.description)

        verify(exactly = 1) { accountingService.transfer(any(), any()) }
    }


    @Test
    fun `chargeFee should throw exception when Invalid Transaction Type is Passes`() {
        val request = TransactionRequest(
            transactionId = "txn_004",
            amount = BigInteger.valueOf(5_000_000),
            asset = "USD",
            assetType = "FIAT",
            type = "Unknown",
            state = "SETTLED - PENDING FEE",
            createdAt = "2025-08-30 15:42:17.610059"
        )

        every { accountingService.transfer(any(), any()) } returns Unit

        every { accountingService.transfer(any(), any()) } returns Unit

        val exception = assertFailsWith<IllegalArgumentException> {
            feeService.chargeFee(request)
        }

        assert(exception.message!!.contains("Unsupported transaction type"))

        verify(exactly = 0) { accountingService.transfer(any(), any()) }
    }

}