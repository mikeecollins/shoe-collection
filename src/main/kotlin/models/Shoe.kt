package models



data class Shoe(
    var shoeName: String,
    var shoeSize: Int,
    var shoeDescription: String,
    val shoePrice: Double,
    val shoeType: String,
    var inCurrentProductLine :Boolean) {
}
