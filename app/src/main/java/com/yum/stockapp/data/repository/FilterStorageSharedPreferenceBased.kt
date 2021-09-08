package com.yum.stockapp.data.repository

import android.content.SharedPreferences
import com.yum.stockapp.data.model.StockCompanyType
import com.yum.stockapp.data.model.StockFilter
import java.util.*

class FilterStorageSharedPreferenceBased(private val sharedPreferences: SharedPreferences) : FilterStorage {
    companion object {
        const val NAME_KEY = "NAME"
        const val COMPANY_TYPES = "TYPES"
    }

    override fun write(value: StockFilter): Boolean {
        // TODO shared pref based storage doesn't need to know how to serialize staff
        return sharedPreferences.edit()
            .putString(NAME_KEY, value.name)
            .putStringSet(COMPANY_TYPES, value.companyType.map { it.name }.toSet())
            .commit()
    }

    override fun read(): Optional<StockFilter> {
        if (!sharedPreferences.contains(NAME_KEY) || !sharedPreferences.contains(COMPANY_TYPES)) {
            return Optional.empty()
        }

        val name = sharedPreferences.getString(NAME_KEY, "").orEmpty()
        val companyTypes = sharedPreferences.getStringSet(COMPANY_TYPES, emptySet()).orEmpty()

        return Optional.of(StockFilter(name, companyTypes.map { StockCompanyType(it) }.toSet()))
    }

    override fun wipe(): Boolean {
        return sharedPreferences.edit()
            .remove(NAME_KEY)
            .remove(COMPANY_TYPES)
            .commit()
    }
}