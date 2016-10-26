package com.example.plugins.tutorial.confluence;

import com.atlassian.confluence.pages.Page;
import java.util.Comparator;

public class PageComparator implements Comparator<Page> {

    public int compare(Page p1, Page p2) {
        return Integer.compare(p2.getVersion(), p1.getVersion());
    }
}
