package com.example.Zadanie;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class HelloServlet extends HttpServlet {
    DataSource dataSource;  // źrodło danych
    PrintWriter out;
    private final String charset = "UTF-8";

    private void startHtml(String title) {
        out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>" + title + "</title>" +
                "<style>" +
                "body {background-image: url(\"https://media.istockphoto.com/vectors/bookshelves-with-books-seamless-background-vector-id1279696721?k=6&m=1279696721&s=612x612&w=0&h=PgxyhiYcjK5Fa1D0d9IS6kKyvtWFD2o1biAAzEnl6xw=\");background-position: 50% 50%;background-repeat: repeat;}" +
                "td, th {border:1px solid #999;padding:0.5rem;text-align:left}" +
                "table {background-color:white;width: 100%;border:1px solid black;border-collapse:collapse;}" +
                "</style>");
    }

    private void endHtml() {
        out.println("</body></html>");
    }

    public void init() throws ServletException {
        try {
            Context init = new InitialContext();
            Context contx = (Context) init.lookup("java:comp/env");
            dataSource = (DataSource) contx.lookup("jdbc/ksidb");
        } catch (NamingException exc) {
            throw new ServletException(
                    "Nie mogę uzyskać źródła java:comp/env/jdbc/ksidb", exc);
        }
    }

    public void serviceRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(charset);
        resp.setContentType("text/html; charset=" + charset);
        out = resp.getWriter();

        startHtml("Biblioteka");
        out.println("<script type='text/javascript'>");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/js/sorttable.js");
        dispatcher.include(req, resp);
        out.println("</script></head><body>");

        out.println("<p style=\"text-align:center\"><img src=\"http://2.bp.blogspot.com/-bBJeRPdPd9M/UfYBsNF2ffI/AAAAAAAADnk/_qqet8O6rvo/s1600/library%2Bcard2.gif\" width=\"80%\" height=\"100\"/></p>");
        out.println("<p style=\"text-align:center;font-style:bold;font-family:Comic sans MS;font-size:40px;color:red\">Wyszukiwarka Książek :)</p>");

        // Nazwę pliku z formularzem dostarczymy
        // jako parametr inicjalny serwletu
        String formFile = getInitParameter("searchFormFile");

        // Przeczytamy go i wpiszemy na generowaną stronę
        ServletContext context = getServletContext();
        InputStream in = context.getResourceAsStream("/WEB-INF/" + formFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line;
        out.println("<div style=\"text-align:center\">");
        while ((line = br.readLine()) != null) {
            out.println(line);
        }
        out.println("</div>");
        // Pobieramy parametry formularza
        String title = req.getParameter("title");

        if (title == null) {
            endHtml();
            out.close();
            return;
        }

        Connection con = null;

        List<Book> books = new ArrayList<>();
        try {
            synchronized (dataSource) {
                con = dataSource.getConnection();
            }
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select poz.isbn, poz.tytul, au.name as aname, pub.name as pname, poz.rok, poz.cena from pozycje poz join autor au on au.autid = poz.autid join wydawca pub on pub.wydid = poz.wydid where tytul like '%" + title + "%' or au.name like '%" + title + "%' or pub.name like '%" + title + "%'");

            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String tytul = rs.getString("tytul");
                String autor = rs.getString("aname");
                String publisher = rs.getString("pname");
                int year = rs.getInt("rok");
                float price = rs.getFloat("cena");
                books.add(new Book(isbn, tytul, autor, publisher, year, price));
            }

            out.println("<table class=\"sortable\">");
            out.println("<thead><tr>" +
                    "<th>ISBN</th>" +
                    "<th>Tytuł</th>" +
                    "<th>Autor</th>" +
                    "<th>Wydawca</th>" +
                    "<th>Rok</th>" +
                    "<th>Cena PLN</th>" +
                    "</tr></thead>");

            out.println("<tbody>");
            for (Book book : books) {
                out.println("<tr>" +
                        "<td>" + book.getIsbn() + "</td>" +
                        "<td>" + book.getTitle() + "</td>" +
                        "<td>" + book.getAutor() + "</td>" +
                        "<td>" + book.getPublisher() + "</td>" +
                        "<td>" + book.getYear() + "</td>" +
                        "<td>" + book.getPrice() + "</td>" +
                        "</tr>");
            }
            out.println("</tbody>");

            rs.close();
            stmt.close();
        } catch (Exception exc) {
            out.println(exc.getMessage());
        } finally {
            out.println("</table>");
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ignored) {
            }
        }

        endHtml();
        out.close();
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        serviceRequest(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        serviceRequest(request, response);
    }

    public void destroy() {
    }
}