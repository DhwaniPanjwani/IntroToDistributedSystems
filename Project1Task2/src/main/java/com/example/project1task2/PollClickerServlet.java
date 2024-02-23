package com.example.project1task2;

import java.io.*;
import java.util.HashMap;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/*
This model represents an MVC where this is the servlet. The file index.jsp points to this
servlet where it asks the user to choose an option, and viewing the results using the /getResults
in the URL.

 */


@WebServlet(name = "choosingOptionServlet", value = {"/getOption", "/getResults"})
public class PollClickerServlet extends HttpServlet {


    PollClickerModel pcm = null;  // The "business model" for this app

    //Initializing the hashmap which will be used to store the count results
    HashMap<Character, Integer> scoreCount = new HashMap<>();

    //Initializing the model
    public void init() {pcm = new PollClickerModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    //Reference for the doGet function: Interesting Picture: Lab 2 and Lab 1
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        String option = request.getParameter("optionChoice");

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

        //checking if the path contains /getResults
        if (!request.getServletPath().equals("/getResults")) {
            request.setAttribute("getTheOptionChoice", option);


            if(option!=null) {
                //storing the returned hashmap from model in scoreCount
                scoreCount = pcm.keepScoreCount(option);
            }

            //setting the attribute getTheScoreCount to scoreCount
            request.setAttribute("getTheScoreCount", scoreCount);

            //forwarding to the next view to fetch more inputs from the user
            nextView = "index.jsp";
            RequestDispatcher view = request.getRequestDispatcher(nextView);
            view.forward(request, response);

            //if url path does not contain getResults
        } else {
            if (scoreCount.isEmpty()) {
                //flag for keeping track of no results found or refreshing the page
                int flag = 1;
                request.setAttribute("flag",flag );

                //forwarding to the next view
                nextView = "result.jsp";
                RequestDispatcher view = request.getRequestDispatcher(nextView);
                view.forward(request, response);
            } else {
                int flag = 0;


                request.setAttribute("flag",flag);

                //setting the attributes for ACount, BCount, CCount and DCount
                for (char key : scoreCount.keySet()) {
                    int value = scoreCount.get(key);
                    if(key=='A')
                        request.setAttribute("ACount", value);
                    if (key=='B')
                        request.setAttribute("BCount", value);
                    if (key=='C')
                        request.setAttribute("CCount", value);
                    if (key=='D')
                        request.setAttribute("DCount", value);


                }
                request.setAttribute("getTheScoreCount", scoreCount);

                //forwarding to the next view
                nextView = "result.jsp";
                RequestDispatcher view = request.getRequestDispatcher(nextView);
                view.forward(request, response);
                scoreCount.clear();
                request.setAttribute("getTheScoreCount", scoreCount);

            }
        }



        }



    public void destroy() {
    }
}