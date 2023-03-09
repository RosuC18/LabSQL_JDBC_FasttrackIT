import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DemoCRUDPeUsers {

    public static void main(String[] args) throws SQLException {

        // cum fac CRUD pe un obiect de tip User

        DemoCRUDPeUsers obiect = new DemoCRUDPeUsers();

////        User u = new User("costica", "password1");
////
////        boolean isAdded = obiect.insert(u);
////        System.out.println(isAdded);
//
//           List result = obiect.readAllUsers();
//           System.out.println(result);


        User u = new User("costica", "anaarepere1");
      boolean ex = obiect.update(u);

        System.out.println(ex);



//        obiect.delete(u);

    }

    private boolean insert (User u) throws SQLException {

        // COD CARE SCRIE IN DB



        // daca are rezultate, citirea lor


        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="postgres";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        // rulare sql
        PreparedStatement pSt = conn.prepareStatement("insert into users(username, password) values(?, ?)");
        pSt.setString(1, u.getUsername());
        pSt.setString(2, u.getPassword());
        int val = pSt.executeUpdate(); // 1

        boolean ok = false;
        if(val!=0)
           ok=true;
        return ok;
    }

    private List<User> readAllUsers() throws SQLException {
        List<User> lu = new ArrayList<>();
        // citeste din db toti userii si returneaza lista lor



        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="postgres";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        // rulare sql
        Statement pSt = conn.createStatement();

        ResultSet rs = pSt.executeQuery("select * from users order by username asc");



        while(rs.next()) {

            String user = rs.getString("username").trim();
            String p = rs.getString("password").trim();
            User u = new User(user,p);
            lu.add(u);

        }
        return lu;
    }


    private boolean update (User u) throws SQLException {

        // COD CARE SCRIE IN DB



        // daca are rezultate, citirea lor


        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="postgres";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        // rulare sql
        PreparedStatement pSt = conn.prepareStatement("update users set password= ? where username = ?");
        pSt.setString(1, u.getPassword());
        pSt.setString(2, u.getUsername());
        int val = pSt.executeUpdate(); // 1

        boolean ok = false;
        if(val!=0)
            ok=true;
        return ok;
    }

}