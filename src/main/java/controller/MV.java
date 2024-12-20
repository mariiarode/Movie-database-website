package controller;

import database.MVdb;
import database.USERdb;
import model.MVmovie;
import model.MVuser;
import model.Tools;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MV", value = "/MV")
public class MV extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MVdb mvdb = new MVdb();
        USERdb userdb = new USERdb();


        if (!userdb.usernameExists("user1")) {
            MVuser user1 = new MVuser();
            user1.setLogin("user1");
            user1.setPassword("password1");
            user1.setPrivileges(1);
            userdb.insertUser(user1);
        }

        if (!userdb.usernameExists("user2")) {
            MVuser user2 = new MVuser();
            user2.setLogin("user2");
            user2.setPassword("password2");
            user2.setPrivileges(1);
            userdb.insertUser(user2);
        }

        if (!userdb.usernameExists("admin")) {
            MVuser admin = new MVuser();
            admin.setLogin("admin");
            admin.setPassword("admin");
            admin.setPrivileges(2);
            userdb.insertUser(admin);
        }


        String searchTitle = request.getParameter("searchTitle");
        if (searchTitle != null && !searchTitle.isEmpty()) {
            MVmovie movie = mvdb.selectMovieByTitle(searchTitle);
            if (movie != null) {
                request.setAttribute("movie", movie);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/movieDetails.jsp");
                dispatcher.forward(request, response);
            } else {
                response.getWriter().println("Movie not found.");
            }

            return;
        }

        String movieEdit = request.getParameter("id1");
        if (movieEdit != null && !movieEdit.isEmpty()) {
            int movieId = Integer.parseInt(movieEdit);
            MVmovie movie = mvdb.selectMovieById(movieId);
            if (movie != null) {
                request.setAttribute("movie", movie);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/editMovie.jsp");
                dispatcher.forward(request, response);
            } else {
                response.getWriter().println("Movie not found.");
            }
        }

        String movieIdString = request.getParameter("id");
        if (movieIdString != null && !movieIdString.isEmpty()) {
            int movieId = Integer.parseInt(movieIdString);
            MVmovie movie = mvdb.selectMovieById(movieId);
            if (movie != null) {
                request.setAttribute("movie", movie);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/movieDetails.jsp");
                dispatcher.forward(request, response);
            } else {
                response.getWriter().println("Movie not found.");
            }
        } else {
            String[] posterUrls = {
                    "https://irs.www.warnerbros.com/gallery-v2-tablet-jpeg/inception_posterlarge_0-587516945.jpg",
                    "https://m.media-amazon.com/images/M/MV5BNTRhZGNiZTUtZTUxMi00NWQ2LWI1NmUtZDRjMzAxNjUzZTQ5XkEyXkFqcGdeQXVyMTYzMDM0NTU@._V1_QL75_UX376_.jpg",
                    "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
                    "https://m.media-amazon.com/images/S/pv-target-images/e9a43e647b2ca70e75a3c0af046c4dfdcd712380889779cbdc2c57d94ab63902.jpg",
                    "https://pics.filmaffinity.com/Avatar-208925608-large.jpg",
                    "https://m.media-amazon.com/images/S/pv-target-images/dbb9aff6fc5fcd726e2c19c07f165d40aa7716d1dee8974aae8a0dad9128d392.jpg",
                    "https://storage.googleapis.com/pod_public/750/180854.jpg",
                    "https://www.ecartelera.com/carteles/3400/3475/002.jpg",
                    "https://i.ebayimg.com/images/g/4goAAOSwMyBe7hnQ/s-l1200.webp",
                    "https://m.media-amazon.com/images/I/81V36dsqyyL._AC_UF1000,1000_QL80_.jpg",
                    "https://i.ebayimg.com/images/g/wbIAAOSwoHheJsJA/s-l1200.webp",
                    "https://m.media-amazon.com/images/I/718Z0NkO0fL._AC_UF894,1000_QL80_.jpg",
                    "https://m.media-amazon.com/images/I/71RJmuDZdaL._AC_UF894,1000_QL80_.jpg",
                    "https://m.media-amazon.com/images/I/91zGp74Qc4L._AC_UF894,1000_QL80_.jpg",
                    "https://pics.filmaffinity.com/the_silence_of_the_lambs-767447992-mmed.jpg",
                    "https://i.ebayimg.com/images/g/9wwAAOSw1PZfVemW/s-l1600.jpg",
                    "https://m.media-amazon.com/images/I/61jkTiX8NuL._AC_UF894,1000_QL80_.jpg",
                    "https://i.ebayimg.com/images/g/~gUAAOSwIOthElD4/s-l1600.jpg",
                    "https://www.tuposter.com/pub/media/catalog/product/cache/71d04d62b2100522587d43c930e8a36b/e/l/el_sexto_sentido_poster_1.png",
                    "https://i.etsystatic.com/18242346/r/il/21bb28/3704563719/il_570xN.3704563719_hqwc.jpg"

            };
            String[] titles = {
                    "Inception",
                    "The Matrix",
                    "Interstellar",
                    "The Dark Knight",
                    "Avatar",
                    "Pulp Fiction",
                    "Fight Club",
                    "Forrest Gump",
                    "The Shawshank Redemption",
                    "The Lord of the Rings: The Fellowship of the Ring",
                    "The Godfather",
                    "The Avengers",
                    "Gladiator",
                    "Titanic",
                    "The Silence of the Lambs",
                    "Jurassic Park",
                    "The Lion King",
                    "Harry Potter and the Sorcerer's Stone",
                    "The Sixth Sense",
                    "Inglourious Basterds",
            };
            String[] producers = {
                    "Christopher Nolan",
                    "Wachowskis",
                    "Christopher Nolan",
                    "Christopher Nolan",
                    "James Cameron",
                    "Quentin Tarantino",
                    "David Fincher",
                    "Robert Zemeckis",
                    "Frank Darabont",
                    "Peter Jackson",
                    "Francis Ford Coppola",
                    "Joss Whedon",
                    "Ridley Scott",
                    "James Cameron",
                    "Jonathan Demme",
                    "Steven Spielberg",
                    "Roger Allers, Rob Minkoff",
                    "Chris Columbus",
                    "M. Night Shyamalan",
                    "Quentin Tarantino",
            };
            String[] actors = {
                    "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page",
                    "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss",
                    "Matthew McConaughey, Anne Hathaway, Jessica Chastain",
                    "Christian Bale, Heath Ledger, Aaron Eckhart",
                    "Sam Worthington, Zoe Saldana, Sigourney Weaver",
                    "John Travolta, Uma Thurman, Samuel L. Jackson",
                    "Brad Pitt, Edward Norton, Helena Bonham Carter",
                    "Tom Hanks, Robin Wright, Gary Sinise",
                    "Tim Robbins, Morgan Freeman, Bob Gunton",
                    "Elijah Wood, Ian McKellen, Orlando Bloom",
                    "Marlon Brando, Al Pacino, James Caan",
                    "Robert Downey Jr., Chris Evans, Scarlett Johansson",
                    "Russell Crowe, Joaquin Phoenix, Connie Nielsen",
                    "Leonardo DiCaprio, Kate Winslet, Billy Zane",
                    "Jodie Foster, Anthony Hopkins, Scott Glenn",
                    "Sam Neill, Laura Dern, Jeff Goldblum",
                    "Matthew Broderick, Jeremy Irons, James Earl Jones",
                    "Daniel Radcliffe, Rupert Grint, Emma Watson",
                    "Bruce Willis, Haley Joel Osment, Toni Collette",
                    "Brad Pitt, Diane Kruger, Eli Roth",
            };
            String[] summaries = {
                    "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.",
                    "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                    "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                    "When the menace known as The Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                    "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                    "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
                    "An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.",
                    "The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.",
                    "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                    "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.",
                    "An organized crime dynasty's aging patriarch transfers control of his clandestine empire to his reluctant son.",
                    "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.",
                    "A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery.",
                    "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.",
                    "A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.",
                    "During a preview tour, a theme park suffers a major power breakdown that allows its cloned dinosaur exhibits to run amok.",
                    "Lion prince Simba and his father are targeted by his bitter uncle, who wants to ascend the throne himself.",
                    "An orphaned boy enrolls in a school of wizardry, where he learns the truth about himself, his family and the terrible evil that haunts the magical world.",
                    "A boy who communicates with spirits seeks the help of a disheartened child psychologist.",
                    "In Nazi-occupied France during World War II, a plan to assassinate Nazi leaders by a group of Jewish U.S. soldiers coincides with a theatre owner's vengeful plans for the same.",
            };
            int[] releaseYears = {
                    2010, 1999, 2014, 2008, 2009, 1994, 1999, 1994, 1994, 2001, 1972, 2012, 2000, 1997, 1991, 1993, 1994, 2001, 1999, 2009,
            };
            String[] genres = {
                    "Sci-Fi", "Action", "Sci-Fi", "Action", "Sci-Fi", "Crime", "Drama", "Drama", "Drama", "Adventure", "Crime", "Action",
                    "Action", "Romance", "Crime", "Adventure", "Animation", "Adventure", "Mystery", "Adventure",
            };

            for (int i = 0; i < titles.length; i++) {
                MVmovie existingMovie = mvdb.selectMovieByTitle(titles[i]);
                if (existingMovie != null) {
                    continue;
                }

                MVmovie movie = new MVmovie();
                movie.setTitle(titles[i]);
                movie.setProducer(producers[i]);
                movie.setActors(actors[i]);
                movie.setSummary(summaries[i]);
                movie.setPoster(Tools.downloadImage(posterUrls[i]));
                movie.setReleaseYear(releaseYears[i]);
                movie.setGenre(genres[i]);
                mvdb.insertMovie(movie);
            }

            List<MVmovie> movies = mvdb.selectAllMovies();
            request.setAttribute("movies", movies);
            List<String> allTitles = new ArrayList<>();
            for (MVmovie movie : movies) {
                allTitles.add(movie.getTitle());
            }
            request.setAttribute("titles", allTitles);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/index.jsp");
            dispatcher.forward(request, response);
        }
        mvdb.closeConnection();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            USERdb userdb = new USERdb();
            if (userdb.validateUser(username, password)) {
                MVuser user = userdb.selectUserByUsername(username);
                request.getSession().setAttribute("user", user);
                response.sendRedirect("MV");
            } else {
                response.getWriter().println("Invalid username or password.");
            }
            userdb.closeConnection();
        } else if ("logout".equals(action)) {
            request.getSession().invalidate();
            response.sendRedirect("MV");
        } else if ("createUser".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Check if the username already exists
            USERdb userdb = new USERdb();
            boolean usernameExists = userdb.usernameExists(username);
            userdb.closeConnection();

            if (usernameExists) {
                response.getWriter().println("Username already exists. Please choose a different username.");
            } else {
                MVuser newUser = new MVuser();
                newUser.setLogin(username);
                newUser.setPassword(password);
                newUser.setPrivileges(1); // Default privileges for new users

                userdb = new USERdb();
                boolean success = userdb.insertUser(newUser);
                if (success) {
                    response.getWriter().println("User created successfully.");
                } else {
                    response.getWriter().println("Error creating user.");
                }
                userdb.closeConnection();
            }
        } else if ("changePrivileges".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int newPrivileges = Integer.parseInt(request.getParameter("newPrivileges"));

            USERdb userdb = new USERdb();
            boolean success = userdb.updateUserPrivileges(userId, newPrivileges);
            if (success) {
                response.sendRedirect("MV?page=AdminUsers.jsp");
            } else {
                response.getWriter().println("Error changing user privileges.");
            }
            userdb.closeConnection();
        } else if ("deleteUser".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));

            USERdb userdb = new USERdb();
            boolean success = userdb.deleteUser(userId);
            if (success) {
                response.sendRedirect("MV?page=AdminUsers.jsp");
            } else {
                response.getWriter().println("Error deleting user.");
            }
            userdb.closeConnection();
        } else if ("addFav".equals(action)) {
            MVuser user = (MVuser) request.getSession().getAttribute("user");
            if (user != null) {
                int userId = user.getId();
                int movieId = Integer.parseInt(request.getParameter("movieId"));

                USERdb userdb = new USERdb();
                boolean inserted = userdb.insertFavoriteMovie(userId, movieId);
                if (!inserted) {
                    response.getWriter().println("Failed to add movie to favourites.");
                } else {
                    response.getWriter().println("Movie added to favourites.");
                }
                userdb.closeConnection();

                // Fetch the movie details again to forward back to the same page
                MVdb mvdb = new MVdb();
                MVmovie movie = mvdb.selectMovieById(movieId);
                mvdb.closeConnection();

                if (movie != null) {
                    request.setAttribute("movie", movie);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/movieDetails.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.getWriter().println("Movie not found.");
                }
            } else {
                response.sendRedirect("/WEB-INF/view/index.jsp"); // Redirect to login page if user is not authenticated
            }
        }


     else if ("updateMovie".equals(action)) {
            MVdb mvdb = new MVdb();

            int movieId = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String summary = request.getParameter("summary");
            String actors = request.getParameter("actors");
            String producer = request.getParameter("producer");
            int releaseYear = Integer.parseInt(request.getParameter("releaseYear"));
            String genre = request.getParameter("genre");

            MVmovie movie = new MVmovie();
            movie.setId(movieId);
            movie.setTitle(title);
            movie.setSummary(summary);
            movie.setActors(actors);
            movie.setProducer(producer);
            movie.setReleaseYear(releaseYear);
            movie.setGenre(genre);

            boolean success = mvdb.updateMovie(movie);
            if (success) {
                response.sendRedirect("MV?action=movieDetails&id=" + movieId);
            } else {
                response.getWriter().println("Error updating movie.");
            }
     } else if ("backMovies".equals(action)) {
            MVdb mvdb = new MVdb();
            List<MVmovie> movies = mvdb.selectAllMovies();
            request.setAttribute("movies", movies);
            List<String> allTitles = new ArrayList<>();
            for (MVmovie movie : movies) {
                allTitles.add(movie.getTitle());
            }
            request.setAttribute("titles", allTitles);
            String page = "movies";
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/index.jsp?page=" + page);
            dispatcher.forward(request, response);
     }


    }

}
