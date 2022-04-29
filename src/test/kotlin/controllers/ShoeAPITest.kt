package controllers


import models.Shoe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse


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
        footLocker = Shoe("Vapour Max", 10, "Everyday wear ", 125.00, "Sneakers", true)
        tommyHilfiger = Shoe("Badge pool", 5, "Casual wear ", 80.00, "Sandals", false)
        TimberLand = Shoe("Bradstreet", 5, "Street wear ", 100.00, "Boots", false)
        nike = Shoe("Black AF1", 11, "Casual ", 110.00, "Runners", true)


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

    @Nested
    inner class AddShoes {
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

        @Nested
        inner class ListShoes {

            @Test
            fun `listAllShoes returns No Shoe Stored message when ArrayList is empty`() {
                assertEquals(0, emptyShoes!!.numberOfShoes())
                assertTrue(emptyShoes!!.listAllShoes().lowercase().contains("no shoes"))
            }

            @Test
            fun `listAllShoes returns Shoes when ArrayList has shoes stored`() {
                assertEquals(5, populatedShoes!!.numberOfShoes())
                val shoesString = populatedShoes!!.listAllShoes()
                assertTrue(shoesString.contains("Jordans"))
                assertTrue(shoesString.contains("Vapour Max"))
                assertTrue(shoesString.contains("Bradstreet"))
                assertTrue(shoesString.contains("Black AF1"))
            }
        }
        @Test
        fun `listActiveShoes returns no active shoes stored when ArrayList is empty`() {
            assertEquals(0, emptyShoes!!.numberOfActiveShoes())
            assertFalse(
                emptyShoes!!.listActiveShoes().lowercase().contains("no active shoes")
            )
        }
        @Test
        fun `listActiveShoes returns active shoes when ArrayList has active shoes stored`() {
            assertEquals(3, populatedShoes!!.numberOfActiveShoes())
            val activeShoesString = populatedShoes!!.listActiveShoes()
            assertFalse(activeShoesString.contains("jordans"))
            assertFalse(activeShoesString.contains("vapour max"))
            assertFalse(activeShoesString.contains("bradstreet"))
            assertFalse(activeShoesString.contains("black af1"))
            assertFalse(activeShoesString.contains("badge pool"))
        }
    }

    @Test
    fun `listArchivedShoes returns no archived shoes when ArrayList is empty`() {
        assertEquals(0, emptyShoes!!.numberOfArchivedShoes())
        assertFalse(
            emptyShoes!!.listArchivedShoes().lowercase().contains("no archived shoes")
        )
    }

    @Test
    fun `listArchivedShoes returns archived shoes when ArrayList has archived shoes stored`() {
        assertEquals(2, populatedShoes!!.numberOfArchivedShoes())
        val archivedShoesString = populatedShoes!!.listArchivedShoes().lowercase(Locale.getDefault())
        assertFalse(archivedShoesString.contains("jordans"))
        assertTrue(archivedShoesString.contains("vapour max"))
        assertFalse(archivedShoesString.contains("bradstreet"))
        assertTrue(archivedShoesString.contains("black af1"))
        assertFalse(archivedShoesString.contains("badge pool"))
    }
}



