package com.bibibiradio.scan.webkit;

import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.webkit.QWebView;

public class WebkitTest {
    public static void main(String[] args){
        QApplication.initialize(args);
        
        QWebView view = new QWebView();
        
        view.load(new QUrl("https://www.baidu.com"));
        
        view.show();
        
        QApplication.execStatic();
        QApplication.shutdown();
    }
}
