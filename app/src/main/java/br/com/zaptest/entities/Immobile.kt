package br.com.zaptest.entities

data class Immobile(
    val usableAreas: String,
    val listingType: String,
    val createdAt: String,
    val listingStatus: String,
    val id: String,
    val parkingSpaces: String,
    val updateAt: String,
    val owner: Boolean,
    val images: ArrayList<String>,
    val address: Address,
    val bathrooms: String,
    val bedrooms: String,
    val pricingInfos: PricingInfos
) {
    data class Address(
        val city: String,
        val neighborhood: String,
        val geoLocation: GeoLocation
    ) {

        data class GeoLocation(val precision: String, val location: Location) {
            data class Location(val lat: Double, val long: Double)
        }
    }

    data class PricingInfos(
        val yearlyIptu: String,
        val price: String,
        val businessType: String,
        val monthlyCondoFee: String
    )
}