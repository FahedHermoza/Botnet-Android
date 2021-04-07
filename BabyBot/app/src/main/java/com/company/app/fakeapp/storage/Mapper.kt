package com.company.app.fakeapp.storage

import com.company.app.fakeapp.model.ImeiEntity
import com.company.app.fakeapp.storage.db.imei.Imei

/**
 * https://kotlinlang.org/docs/reference/collection-transformations.html
 * http://modelmapper.org/
 */
object Mapper {

    //TODO convertir entidad a DTO y DTO a entidad
    fun dbImeiToImeiEntity(imei: Imei): ImeiEntity =
        ImeiEntity(imei.idUnique ?: "")

    fun imeiEntityToDbImei(imeiEntity: ImeiEntity): Imei = Imei(imeiEntity.idUnique)

}