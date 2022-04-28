package controllers


import models.Shoe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ShoeAPITest {

    private var jordanOnes: Shoe? = null
    private var footLocker: Shoe? = null
    private var tommyHilfiger: Shoe? = null
    private var TimberLand: Shoe? = null
    private var nike: Shoe? = null
    private var populatedShoes: ShoeAPI? = ShoeAPI()
    private var emptyShoes: ShoeAPI? = ShoeAPI()

    @BeforeEach
    fun setup() {
        jordanOnes = Shoe("Jordans", 5, "For jogging ", 70.00, "Runners", false)
        footLocker = Shoe("Vapour Max", 10, "Everyday wear ", 125.00, "Sneakers", false)
        tommyHilfiger = Shoe("Learning Kotlin", 5, "Casual wear ", 80.00, "Sneakers", false)
        TimberLand = Shoe("Bradstreet", 5, "Street wear ", 100.00, "Boots", false)
        nike = Shoe("Black AF1", 11, "Casual ", 110.00, "Runners", false)


        populatedShoes!!.add(jordanOnes!!)
        populatedShoes!!.add(footLocker!!)
        populatedShoes!!.add(tommyHilfiger!!)
        populatedShoes!!.add(TimberLand!!)
        populatedShoes!!.add(nike!!)
    }

    @AfterEach
    fun tearDown() {
        jordanOnes = null
        footLocker = null
        tommyHilfiger = null
        TimberLand = null
        nike = null
        populatedShoes = null
        emptyShoes = null
    }

    @Test
    fun `adding a Shoe to a populated list adds to ArrayList`() {
        val newShoe = Shoe("Black AF1", 11, "Casual ", 110.00, "Runners", false)
        assertEquals(5, populatedShoes!!.numberOfShoes())
        assertTrue(populatedShoes!!.add(newShoe))
        assertEquals(6, populatedShoes!!.numberOfShoes())
        assertEquals(newShoe, populatedShoes!!.findShoe(populatedShoes!!.numberOfShoes() - 1))
    }

    @Test
    fun `adding a Shoe to an empty list adds to ArrayList`() {
        val newShoe = Shoe("Black AF1", 11, "Casual ", 110.00, "Runners", false)
        assertEquals(0, emptyShoes!!.numberOfShoes())
        assertTrue(emptyShoes!!.add(newShoe))
        assertEquals(1, emptyShoes!!.numberOfShoes())
        assertEquals(newShoe, emptyShoes!!.findShoe(emptyShoes!!.numberOfShoes() - 1))
    }

    @Test
    fun `listAllShoes returns No Shoe Stored message when ArrayList is empty`() {
        assertEquals(0, emptyShoes!!.numberOfShoes())
        assertTrue(emptyShoes!!.listAllShoes().lowercase().contains("no shoes"))
    }

    @Test
    fun `listAllShoes returns Shoes when ArrayList has shoes stored`() {
        assertEquals(5, populatedShoes!!.numberOfShoes())
        val shoesString = populatedShoes!!.listAllShoes().lowercase()
        assertTrue(shoesString.contains("jordanOnes"))
        assertTrue(shoesString.contains("footLocker"))
        assertTrue(shoesString.contains("tommyHilfiger"))
        assertTrue(shoesString.contains("TimberLand"))
        assertTrue(shoesString.contains("nike"))
    }
}




