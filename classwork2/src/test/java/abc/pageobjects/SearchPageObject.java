package abc.pageobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SearchPageObject {

    By searchResultsTotal = By.cssSelector(".Search__results");
    By dateFilter = By.name("after");
    By sortByFilter = By.name("sort");
    By searchedArticles = By.cssSelector(".ContentRoll__Item");
    By searchArticleTimeStamp = By.cssSelector(".TimeStamp__Date");
    By noResults = By.cssSelector(".Search__No__Results");

    WebDriver driver;

    public SearchPageObject(WebDriver driver) {
        this.driver = driver;
    }
    
    public String getSearchResultsTotal() {
        WebElement searchResults = driver.findElement(searchResultsTotal);
        String searchResultsText = searchResults.getText();
        return searchResultsText;
    }

    public void clickOnArticle(int index) {
        List<WebElement> articleList = driver.findElements(searchedArticles);
        WebElement article = articleList.get(index);
        article.click();
    }

    public void filterByDate(String option) {
        WebElement dateFilterElement = driver.findElement(dateFilter);
        dateFilterElement.click();
        Select selectDateFilter = new Select(dateFilterElement);
        selectDateFilter.selectByVisibleText(option);
    }

    public void sortBy(String option) {
        WebElement sortByFilterElement = driver.findElement(sortByFilter);
        sortByFilterElement.click();
        Select selectSortByFilter = new Select(sortByFilterElement);
        selectSortByFilter.selectByVisibleText(option);
    }

    public String getArticleTimeStamp() {
        WebElement articleTimeStamp = driver.findElement(searchArticleTimeStamp);
        String articleTimeStampText = articleTimeStamp.getText();
        return articleTimeStampText;
    }

    public boolean noResultsFound() {
        List<WebElement> noResultsElements = driver.findElements(searchedArticles);
        return noResultsElements.size() == 0;
    }

    public String getNoResultsText() {
        WebElement noResultsElement = driver.findElement(noResults);
        return noResultsElement.getText();
    }

    public int getSearchResultsCount() {
        List<WebElement> searchResults = driver.findElements(searchedArticles);
        return searchResults.size();
    }
}
