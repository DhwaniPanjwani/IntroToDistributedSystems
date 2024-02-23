package com.example.project1task3;
/*
This model represents an MVC where this is the servlet. The file index.jsp points to this
servlet where it asks the user to choose a specific league, followed by searching a player
to view their stats (page2.jsp), followed by searching a player to view their recent news (page3.jsp)
and lastly displaying the results on page4.jsp.
All the results are a result of screen scraping.
 */



import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/*
 * The following WebServlet annotation gives instructions to the web container.
 * It states that when the user browses to the URL path /getOption
 * then the servlet with the name BaseballAppServlet should be used.
 *
 * (Documentation reference from lab2 -> interesting picture)
 */
@WebServlet(name = "BaseballApp",
        urlPatterns = {"/getOption","/getPlayers","/getTeam"})
public class BaseballAppServlet extends HttpServlet {

    BaseballAppModel bam = null;  // This is the "business model" for this app

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {bam = new BaseballAppModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    //Reference for the doGet function: Interesting Picture: Lab 2
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // get the option choice - American League or National League
        String search = request.getParameter("optionChoice");

        //get the player name whose stats are to be fetched
        String player = request.getParameter("searchWord");

        //get the player name whose news is to be fetched
        String newsPlayerName = request.getParameter("newsPlayerName");

        // determine what type of device our user is
        String ua = request.getHeader("User-Agent");

        boolean mobile;
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        String nextView;

        //Check if the option choice parameter is present.

        if (search != null ) {
            String picSize = (mobile) ? "mobile" : "desktop";
            // use model with the league name input and choose the result view
            String teamStats = bam.scrapeMLBPTeamStats(search);
            /*
            Here the teamStats which is returned from the scrapeMLBTeamStats function, is
            set as attribute teamStats which is displayed on page2.jsp
             */

            request.setAttribute("teamStats",teamStats);

            // Forward the request to the next view.
            nextView = "page2.jsp";
            RequestDispatcher view = request.getRequestDispatcher(nextView);
            view.forward(request, response);
            return;
        }
        // Transfer control over the correct "view"

        /*
        Check if the searched player is not null. If no results are found, then
        display No Results found on the result page.
         */
        if(player!=null)
        {
            //Call the function bam.scrapeMLBPlayerStats to fetch the searched player's stats

            String playerStats = bam.scrapeMLBPlayerStats(player);
            System.out.println(playerStats);
             /*
            Here the teamStats which is returned from the scrapeMLBPlayerStats function, is
            set as attribute playerStats which is displayed on page3.jsp
             */

            request.setAttribute("playerStats",playerStats);
            nextView = "page3.jsp";
            // Forward the request to the next view.
            RequestDispatcher view = request.getRequestDispatcher(nextView);
            view.forward(request, response);
            return;
        }

        /*
        Check if the searched player is not null. If no results are found, then
        display No Results found on the result page.
         */
        if(newsPlayerName!= null)
        {
            //Call the function bam.scrapeMLBPlayerStats to fetch the searched player's recent news

            String newsData =  bam.getNewsData(newsPlayerName);

            /*
            Here the newsData which is returned from the scrapeMLBPlayerStats function, is
            set as attribute newsData which is displayed on page4.jsp
             */
            request.setAttribute("newsPlayerName",newsPlayerName);
            request.setAttribute("newsData",newsData);

            // Forward the request to the next view.
            nextView = "page4.jsp";
            RequestDispatcher view = request.getRequestDispatcher(nextView);
            view.forward(request, response);

        }
        else {
            //goes back to the index.jsp if no results found at any stage
            System.out.println("No Results");
            nextView = "index.jsp";
            RequestDispatcher view = request.getRequestDispatcher(nextView);
            view.forward(request, response);
        }
    }
    public void destroy() {
    }

}


