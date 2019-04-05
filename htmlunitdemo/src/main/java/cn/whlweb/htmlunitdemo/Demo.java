package cn.whlweb.htmlunitdemo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;

import java.io.IOException;

public class Demo {

    public static void main(String[] args) throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
        webClient.getOptions().setThrowExceptionOnScriptError(true);
        webClient.getOptions().setActiveXNative(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        Page page = webClient.getPage("http://www.baidu.com/");
        webClient.waitForBackgroundJavaScript(30000);

        String contentAsString = page.getWebResponse().getContentAsString();
        System.out.printf("kjkl");

    }
}
