package com.imdifoods.imdifoodswebcommerce.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PageMakerTest {

    private final int buttonsCount = 5;

    @Test
    void testTotalLessEqualThanButtonsCount() {
        PageMaker pageMaker = new PageMaker(4, 1, buttonsCount);
        assertEquals(1, pageMaker.getStartPage());
        assertEquals(4, pageMaker.getEndPage());
    }

    @Test
    void testCurrentPageLessThanHalfButtonsCount() {
        PageMaker pageMaker = new PageMaker(10, 1, buttonsCount);
        assertEquals(1, pageMaker.getStartPage());
        assertEquals(buttonsCount, pageMaker.getEndPage());
    }

    @Test
    void testCurrentPagePlusHalfButtonsCountEqualTotalPages() {
        PageMaker pageMaker = new PageMaker(10, 8, buttonsCount);
        assertEquals(6, pageMaker.getStartPage());
        assertEquals(10, pageMaker.getEndPage());
    }

    @Test
    void testCurrentPagePlusHalfButtonsCountGreaterThanTotalPages() {
        PageMaker pageMaker = new PageMaker(10, 10, buttonsCount);
        assertEquals(6, pageMaker.getStartPage());
        assertEquals(10, pageMaker.getEndPage());
    }

    @Test
    void testCurrentPagePlusHalfButtonsCountLessThanTotalPages() {
        PageMaker pageMaker = new PageMaker(10, 5, buttonsCount);
        assertEquals(3, pageMaker.getStartPage());
        assertEquals(7, pageMaker.getEndPage());
    }

    @Test
    void testSetOddButtonsCountShouldSuccess() {
        int oddButtonsCount = 3;
        PageMaker pageMaker = new PageMaker(10, 5, oddButtonsCount);
        assertEquals(oddButtonsCount, pageMaker.getButtonsToShow());
    }

    @Test
    void testSetEvenButtonsCountShouldThrowException() {
        int evenButtonsCount = 4;
        try {
            new PageMaker(10, 5, evenButtonsCount);
        } catch (IllegalArgumentException e) {
            assertEquals("Must be an odd value!", e.getMessage());
        }
    }

}
