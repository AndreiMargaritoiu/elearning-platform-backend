package com.andreimargaritoiu.elearning.datasource.mock

import com.andreimargaritoiu.elearning.datasource.BankDataSource
import com.andreimargaritoiu.elearning.model.models.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class MockBankDataSource : BankDataSource {

    val banks = mutableListOf(
            Bank("aa", 0.1, 12),
            Bank("bb", 71.1, 22),
            Bank("cc", 33.1, 32)
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank =
            banks.firstOrNull { it.accountNumber == accountNumber }
                    ?: throw  NoSuchElementException("Could not found bank with accountNumber = $accountNumber")

    override fun createBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber }) {
            throw IllegalArgumentException("Bank with accountNumber = ${bank.accountNumber} already exists")
        }
        banks.add(bank)

        return bank
    }

    override fun modifyBank(accountNumber: String, bank: Bank): Bank {
        val currentBank = banks.firstOrNull { it.accountNumber == accountNumber }
                ?: throw  NoSuchElementException("Could not found bank with accountNumber = $accountNumber")
        banks.remove(currentBank)
        banks.add(bank)

        return bank
    }

    override fun removeBank(accountNumber: String) {
        val bankToBeRemoved = banks.firstOrNull { it.accountNumber == accountNumber }
                ?: throw  NoSuchElementException("Could not found bank with accountNumber = $accountNumber")
        banks.remove(bankToBeRemoved)
    }
}