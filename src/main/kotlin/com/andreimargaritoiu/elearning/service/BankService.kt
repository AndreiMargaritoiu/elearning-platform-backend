package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.datasource.BankDataSource
import com.andreimargaritoiu.elearning.model.models.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val bankDataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> = bankDataSource.retrieveBanks()

    fun getBank(accountNumber: String): Bank = bankDataSource.retrieveBank(accountNumber)

    fun addBank(bank: Bank): Bank = bankDataSource.createBank(bank)

    fun updateBank(accountNumber: String, bank: Bank): Bank = bankDataSource.modifyBank(accountNumber, bank)

    fun deleteBank(accountNumber: String): Unit = bankDataSource.removeBank(accountNumber)

}