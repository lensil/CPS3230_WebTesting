package abc.tests;

import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import abc.pageobjects.ArticlePageObject;
import abc.pageobjects.HomepagePageObject;
import abc.pageobjects.SearchPageObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AbcNewsTests {
    WebDriver driver;
    WebDriverWait wait;

    ArticlePageObject articlePage;
    HomepagePageObject homepagePage;
    SearchPageObject searchPage;

    @BeforeAll
    static void setPaths() { // Tell selenium where to find driver, will find broswer by itself
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/drivers/mac/chromedriver");
    }

    @BeforeEach
    public void setUp() {

        // Start browser and initialize driver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);

        // Now initialize the WebDriverWait with the driver
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        // Go to URL
        driver.get("https://abcnews.go.com");

        // Initialize the page objects
        articlePage = new ArticlePageObject(driver);
        homepagePage = new HomepagePageObject(driver);
        searchPage = new SearchPageObject(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit(); // will close all broswe, close() will close only a tab
    }

    /*************** Given Tests ***************/

    /*
     * (Replacement for the cookies test)
     * Given that I go to https://abcnews.go.com
     * When I search for "coronavirus"
     * And I sort by "Oldest"
     * Then the date of the article should be "January 01, 2006"
     */
    @Test
    public void testSortBy() {
        String query = "coronavirus";
        homepagePage.searchFor(query);
        searchPage.sortBy("Oldest");
        String articleTimeStamp = searchPage.getArticleTimeStamp();
        assertEquals("January 01, 2006", articleTimeStamp);
    }

    /*
     * Given I go to the news site landing page When I search for EU
     * Then search results show up
     */
    @Test
    public void testSearchForEU() {
        homepagePage.searchFor("EU");
        int totalResults = searchPage.getSearchResultsCount();
        assertTrue(totalResults > 0);
    }

    /*
     * Given I go to the news site landing page
     * When I click the main article link
     * Then the full article is shown
     */
    @Test
    public void testMainArticle() {
        String mainArticleTitle = homepagePage.getMainArticleTitle();
        homepagePage.clickOnMainArticle();
        String articleTitle = articlePage.getArticleTitle();
        boolean articleBodyIsDisplayed = articlePage.articleBodyIsDisplayed();
        assertEquals(mainArticleTitle, articleTitle);
        assertTrue(articleBodyIsDisplayed);
    }

    /*
     * Given I go to the news site landing page
     * When I click the link to a specific section
     * Then the URL is different form the landing page
     */
    @Test
    public void testSectionLink() {
        String homepageURL = driver.getCurrentUrl();
        homepagePage.clickBusinessLink();
        String businessURL = driver.getCurrentUrl();
        assertTrue(!homepageURL.equals(businessURL));
    }

    /*
     * Given I open a specific article
     * Then there should be links to other articles
     */
    @Test
    public void testPopularArticles() {
        homepagePage.clickOnArticle(0);
        int popularArticles = articlePage.getPopularArticlesCount();
        assertTrue(popularArticles > 0);
    }

    /*************** Chosen Tests ***************/

    /*
     * Given that I go to https://abcnews.go.com
     * When I click on the business section
     * Then I should see the heading "Business News"
     */
    @Test
    public void testPageHeading() {
        homepagePage.clickBusinessLink();
        String heading = driver.findElement(By.cssSelector(".section-callout")).getText();
        assertEquals("Business News", heading);
    }

    /*
     * Given that I go to https://abcnews.go.com
     * When I click on the first article
     * Then the author should be displayed as "by" followed by the author's name
     */
    @Test
    public void testArticleAuthor() {
        homepagePage.clickOnArticle(0);
        String author = articlePage.getArticleAuthor();
        assertTrue(author.length() > 0);
        assertTrue(author.startsWith("By"));
    }

    /*
     * Given that I go to https://abcnews.go.com
     * When I click on the first article
     * Then four share buttons should be displayed
     */
    @Test
    public void testArticleShareButtons() {
        homepagePage.clickOnArticle(0);
        int shareButtons = articlePage.getShareButtonsCount();
        assertEquals(4, shareButtons);
    }

    /*
     * Given that I go to https://abcnews.go.com
     * When I search for "coronavirus"
     * Then the search result summary should start with "Results"
     * And end with "coronavirus"
     */
    @Test
    public void testSearchResults() {
        String query = "coronavirus";
        homepagePage.searchFor(query);
        String totalResults = searchPage.getSearchResultsTotal();
        assertTrue(totalResults.startsWith("Results"));
        assertTrue(totalResults.endsWith("coronavirus"));
    }

    /*
     * Given that I go to https://abcnews.go.com
     * When I click on the live link
     * Then I should url should be https://abcnews.go.com/Live
     */
    @Test
    public void testLiveLink() {
        homepagePage.clickLiveLink();
        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://abcnews.go.com/Live", currentUrl);
    }

    /*************** Additional Tests ***************/

    /*
     * Given that I go to https://abcnews.go.com
     * When I search for "university"
     * Then I should see at least one article
     */
    @Test
    public void testSearchForUniversity() {
        homepagePage.searchFor("university");
        int totalResults = searchPage.getSearchResultsCount();
        assertTrue(totalResults > 0);
    }

    /*
     * Given that I go to https://abcnews.go.com
     * When I search for "coronavirus"
     * And I filter by "Today"
     * Then I should see less articles than before filtering
     */
    @Test
    public void testFilterByDate() {
        String query = "coronavirus";
        String[] resultsArray;
        homepagePage.searchFor(query);
        String totalResults = searchPage.getSearchResultsTotal();
        resultsArray = totalResults.split(" ");
        int totalBeforeFiltering = Integer.parseInt(totalResults.split(" ")[resultsArray.length - 3]);
        searchPage.filterByDate("Last Week");
        String totalResultsAfterFiltering = searchPage.getSearchResultsTotal();
        resultsArray = totalResultsAfterFiltering.split(" ");
        int totalAfterFiltering = Integer.parseInt(totalResultsAfterFiltering.split(" ")[resultsArray.length - 3]);
        assertTrue(totalAfterFiltering < totalBeforeFiltering);
    }

    /*
     * Given that I go to https://abcnews.go.com
     * When I click on the first article
     * Then the link text should match the article title
     */
    @Test
    public void testArticleLinkText() {
        String linkText = homepagePage.getArticleHeading();
        homepagePage.clickOnArticle(0);
        String articleTitle = articlePage.getArticleTitle();
        assertEquals(articleTitle, linkText);
    }

}
