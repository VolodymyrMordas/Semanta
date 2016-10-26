package com.example.plugins.tutorial.confluence;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Scanned
public class ExampleMacro implements Macro {
//    private final XhtmlContent xhtmlUtils;

    private PageManager pageManager;
    private SpaceManager spaceManager;

    @Autowired
    public ExampleMacro(@ComponentImport PageManager pageManager,
                        @ComponentImport SpaceManager spaceManager) {
        this.pageManager = pageManager;
        this.spaceManager = spaceManager;
    }

    public String execute(Map<String, String> map, String s,
                          ConversionContext conversionContext) throws MacroExecutionException {
        Space currentSpace = spaceManager.getSpace(
                conversionContext.getSpaceKey());

        List<Page> pageList = pageManager.getPages(currentSpace, true);

        StringBuilder builder = new StringBuilder();
        builder.append("<table><tr><th>PageId</th><th>PageTitle</th><th>Number of versions</th></tr>");
        Collections.sort(pageList, (new PageComparator()));
        for (Page p : pageList){
            builder.append("<tr>");
            builder.append("<td>"+
                    p.getId() +"</td><td>" + p.getTitle() + "</td><td>"
                    + p.getEntity().getVersion() + "</td>");
            builder.append("</tr>");
        }
        builder.append("</table>");

        return builder.toString();
    }

    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }
}
