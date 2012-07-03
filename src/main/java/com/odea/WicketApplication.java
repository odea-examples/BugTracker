package com.odea;

import org.apache.wicket.devutils.inspector.InspectorPage;
import org.apache.wicket.devutils.inspector.LiveSessionsPage;
import org.apache.wicket.protocol.http.WebApplication;


public class WicketApplication extends WebApplication
{    	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<ListPage> getHomePage()
	{
        return ListPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
        getDebugSettings().setDevelopmentUtilitiesEnabled(true);

        getMarkupSettings().setDefaultMarkupEncoding("utf-8");
        getMarkupSettings().setStripComments(true);
        getMarkupSettings().setStripWicketTags(true);
   /*
        getRequestLoggerSettings().setRequestsWindowSize(500);
        getRequestLoggerSettings().setRecordSessionSize(true);
        getRequestLoggerSettings().setRequestLoggerEnabled(true);
    */

        mountPage("list", ListPage.class);
        mountPage("grid", Grid.class);
	}
}
