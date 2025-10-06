package abc.pageobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ArticlePageObject {

    By articleTitle = By.cssSelector(".vMjAx.gjbzK.tntuS.eHrJ.mTgUP");
    By articleAuthor = By.cssSelector(".TQPvQ.fVlAg.HUcap.kxY.REjk.UamUc.WxHIR.HhZOB.yaUf.VOJBn.KMpjV.XSbaH.Umfib.ukdDD");
    By articleTimestamp = By.cssSelector(".xAPpq.JQYD.ZdbeE.jTKbV.zIIsP.xpuOU.pCRh");
    By articleBody = By.cssSelector(".xvlfx.ZRifP.TKoO.eaKKC.EcdEg.bOdfO ");
    By shareButtons = By.cssSelector(".WEJto");
    By popularArticles = By.cssSelector(".QGHKv.iVcn.avodi.rEPuv.ICwhc.ibBnq.Bkgbl.ZLXw.kSqqG.rEBmF ");

    WebDriver driver;
    
    public ArticlePageObject(WebDriver driver) {
        this.driver = driver;
    }

    public String getArticleTitle() {
        WebElement title = driver.findElement(articleTitle);
        String titleText = title.getText();
        return titleText;
    }

    public String getArticleAuthor() {
        WebElement author = driver.findElement(articleAuthor);
        String authorText = author.getText();
        return authorText;
    }

    public String getArticleTimestamp() {
        WebElement timestamp = driver.findElement(articleTimestamp);
        String timestampText = timestamp.getText();
        return timestampText;
    }

    public int getShareButtonsCount() {
        List<WebElement> shareButtonsList = driver.findElements(shareButtons);
        int shareButtonsCount = shareButtonsList.size();
        return shareButtonsCount;
    }

    public int getPopularArticlesCount() {
        List<WebElement> popularArticlesList = driver.findElements(popularArticles);
        int popularArticlesCount = popularArticlesList.size();
        return popularArticlesCount;
    }

    public boolean articleBodyIsDisplayed() {
        WebElement articleBodyElement = driver.findElement(articleBody);
        return articleBodyElement.isDisplayed();
    }
}
