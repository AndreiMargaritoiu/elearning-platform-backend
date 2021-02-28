package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.datasource.BankDataSource
import io.mockk.*
import org.junit.jupiter.api.Test

internal class BankServiceTest {

    private val bankDataSource: BankDataSource = mockk(relaxed = true) // return some default value
    private val bankService = BankService(bankDataSource) // POJO, not dependency injection

    @Test
    fun `should call its data source to retrieve banks` () {
        // given
//        every { bankDataSource.retrieveBanks() } returns listOf()  // replaced with relaxed = true

        // when
        bankService.getBanks()

        // then
        verify(exactly = 1) {
            bankDataSource.retrieveBanks()
        }
    }
}