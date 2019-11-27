package br.com.zaptest.entities

import java.io.Serializable

data class Immobile(
    val usableAreas: String,
    val listingType: String,
    val createdAt: String,
    val listingStatus: String,
    val id: String,
    val parkingSpaces: String,
    val updateAt: String,
    val owner: Boolean,
    val images: List<String>,
    val address: Address,
    val bathrooms: String,
    val bedrooms: String,
    val pricingInfos: PricingInfos
) : Serializable {
    data class Address(
        val city: String,
        val neighborhood: String,
        val geoLocation: GeoLocation
    ) : Serializable {

        data class GeoLocation(val precision: String, val location: Location) : Serializable {
            data class Location(val lat: Double, val lon: Double) : Serializable
        }
    }

    data class PricingInfos(
        val yearlyIptu: String,
        val price: String,
        val businessType: String,
        val monthlyCondoFee: String?,
        val period: String,
        val rentalTotalPrice: String
    ) : Serializable
}