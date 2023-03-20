import java.sql.*;
import java.util.*;

public class CRUDonAdmin {

    public  void Admin()  {

        CRUDonAdmin objAdmin = new CRUDonAdmin();
        Scanner inname=new Scanner(System.in);
        Scanner inpass=new Scanner(System.in);
        Scanner input=new Scanner(System.in);
        int Id=0;

        String name=null;
        String pass=null;
        String title=null;
        String author=null;
        User u = new User( name, pass);
        Books b=new Books(title,author);


       int n=0;

        while (n!=-1){
            n=menu();

        switch (n) {
            case 1:

                // objAdmin.readAllUsersBooks();
                objAdmin.readAllUsers();
                //  objAdmin.readAllbooks();
                break;
            case 2:
                System.out.println("Insert name: ");
                name = inname.nextLine();
                u.setUsername(name);
                System.out.println("Insert password: ");
                pass = inpass.nextLine();
                u.setPassword(pass);
                objAdmin.insert(u);
                objAdmin.readAllUsers();
                // objAdmin.readAllbooks(u);
                break;
            case 3:
                System.out.print("Insert ID you want to update:");
                Id = input.nextInt();
                u.setId(Id);
                while(true){
                System.out.println("What do you want to update? :");
                System.out.println("1.Username");
                System.out.println("2.Password");
                System.out.print("Choose : ");
                Id = input.nextInt();
                if (Id<1 || Id>2){
                    System.out.println("Wrong selection ,retry.");
                }
                else
                    break;
                }
                    if (Id == 1) {
                        System.out.println("Insert new name:");
                        name = inname.nextLine();
                        u.setUsername(name);
                        objAdmin.updateUsername(u);
                        objAdmin.readAllUsers();
                        break;
                    } else {
                        System.out.print("Insert new password:");
                        pass = inpass.nextLine();
                        u.setPassword(pass);
                        objAdmin.updatePassword(u);
                        objAdmin.readAllUsers();
                    }


                    break;

            case 4:
                System.out.print("Insert Id for delete the record : ");
                Id=input.nextInt();
               u.setId(Id);
                objAdmin.delete(u);
                objAdmin.readAllUsers();
                break;
            case -1:
                break;
        }}
    }
   static Connection LoadConn()
    {
        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="Postgres.2023";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        }
        return conn;
    }
   private static int menu() {
       System.out.println("Menu");
        System.out.println("1-> display list! ");
       // System.out.println("2-> search a product!");
       System.out.println("2-> add a product!");
        System.out.println("3-> modify a product!");
       System.out.println("4-> remove a product!");
        System.out.println("-1 -> exit!");
       System.out.print("Choose! : ");
        int nr = 0;
        try {
            nr = new Scanner(System.in).nextInt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally
        {
            return nr;
        }}


    private boolean insert (User u) {

        int val = 0; // 1
        try {
            PreparedStatement pSt = LoadConn().prepareStatement("insert into users(username, password) values(?,?)");
            pSt.setString(1, u.getUsername());
            pSt.setString(2, u.getPassword());
            val = pSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally
        {
            boolean ok = false;
            if(val!=0)
                ok=true;
            return ok;
        }



    }

    private List<User> readAllUsers()  {
        List<User> lu = new ArrayList<>();
        List<Books>lb=new ArrayList<>();

        try {
            Statement pSt = LoadConn().createStatement();

            ResultSet rs = pSt.executeQuery("select * from users order by username asc");

            while(rs.next()) {
                Long i =rs.getLong("iduser");
                String user = rs.getString("username").trim();
                String p = rs.getString("password").trim();
                User u = new User(user,p);
                u.setId(i);
                System.out.println(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        }

        return lu;

    }


    private boolean updateUsername (User u)  {

        int val = 0; // 1
        try {
            PreparedStatement pSt = LoadConn().prepareStatement("update users set username=? where iduser = ?");
            pSt.setLong(2,u.getId());
            pSt.setString(1, u.getUsername());
            val = pSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        }

        boolean ok = false;
        if(val!=0)
            ok=true;
        return ok;
    }
    private boolean updatePassword (User u)  {

        int val = 0; // 1
        try {
            PreparedStatement pSt = LoadConn().prepareStatement("update users set password= ?  where iduser = ?");
            pSt.setLong(2,u.getId());
            pSt.setString(1, u.getPassword());

            val = pSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        }

        boolean ok = false;
        if(val!=0)
            ok=true;
        return ok;
    }
    private void delete(User u )  {

        try {
            PreparedStatement pSt = LoadConn().prepareStatement("delete from users where id=? ");
            pSt.setLong(1,u.getId());
            int val = pSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        }
    }




    private List<Object> readAllAdmin()  {
       List<Object> lo = new ArrayList<>();

        try {
            Statement pSt = LoadConn().createStatement();

            ResultSet rs = pSt.executeQuery("select * from users,books where users.iduser=books.id_user");


            while(rs.next()) {

                Long i =rs.getLong("iduser");
                String user = rs.getString("username").trim();
                String p = rs.getString("password").trim();
                Long ib =rs.getLong("id_user");
                String title = rs.getString("title").trim();
                String author = rs.getString("author").trim();


                User u = new User(user,p);
                u.setId(i);
                Books b = new Books(title,author);
                b.setId_user(ib);
                lo.add(u);
                lo.add(b);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        }

        for(Object o:lo)
        System.out.println(o);

        return lo;

    }
    long login (User u)    {

        // -1 daca nu exista , si id-ul usaerului daca exista


        long id = -1;

        try {
            PreparedStatement pSt = LoadConn().prepareStatement("select iduser from users where username=? and password=? ");

            pSt.setString(1,u.getUsername());
            pSt.setString(2,u.getPassword());
            ResultSet rs = pSt.executeQuery();


            while(rs.next()) {

                id = rs.getLong("iduser");
                return id;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return id;

    }


    boolean isAdmin (User u)  {

        // -1 daca nu exista , si id-ul usaerului daca exista


        boolean isAdmin=false;


        try {

            // rulare sql
            PreparedStatement pSt = LoadConn().prepareStatement("select isadmin from users where username=? and password=? ");

            pSt.setString(1,u.getUsername());
            pSt.setString(2,u.getPassword());
            ResultSet rs = pSt.executeQuery();


            while(rs.next()) {

                isAdmin = rs.getBoolean("isadmin");
                return isAdmin;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return isAdmin;

    }



}