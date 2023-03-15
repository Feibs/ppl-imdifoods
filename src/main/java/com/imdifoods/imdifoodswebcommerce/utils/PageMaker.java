package com.imdifoods.imdifoodswebcommerce.utils;

public class PageMaker {
    private int buttonsToShow = 5;
    private int startPage;
    private int endPage;

    public PageMaker(int totalPages, int currentPage, int buttonsToShow) {
        setButtonsToShow(buttonsToShow);

        int halfPagesToShow = getButtonsToShow() / 2;

        // show all pages
        if (totalPages <= getButtonsToShow()) {
            setStartPage(1);
            setEndPage(totalPages);

            // show first buttonsToShow pages
        } else if (currentPage - halfPagesToShow <= 0) {
            setStartPage(1);
            setEndPage(getButtonsToShow());

            // show halfPagesToShow pages before currentPage to totalPages
        } else if (currentPage + halfPagesToShow == totalPages) {
            setStartPage(currentPage - halfPagesToShow);
            setEndPage(totalPages);

            // show last buttonsToShow pages
        } else if (currentPage + halfPagesToShow > totalPages) {
            setStartPage(totalPages - getButtonsToShow() + 1);
            setEndPage(totalPages);

            // show halfPagesToShow pages before and after currentPage
        } else {
            setStartPage(currentPage - halfPagesToShow);
            setEndPage(currentPage + halfPagesToShow);
        }

    }

    public int getButtonsToShow() {
        return buttonsToShow;
    }

    public void setButtonsToShow(int buttonsToShow) {
        if (buttonsToShow % 2 != 0) {
            this.buttonsToShow = buttonsToShow;
        } else {
            throw new IllegalArgumentException("Must be an odd value!");
        }
    }

    @Override
    public String toString() {
        return "Pager [startPage=" + startPage + ", endPage=" + endPage + "]";
    }

    public int getStartPage() {
        return this.startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return this.endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
}

// Reference: https://github.com/BranislavLazic/spring-thymeleaf-pagination
