import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DemoCRUDPeUsers {

    public static void main(String[] args) throws SQLException {

        DemoCRUDPeUsers obiect = new DemoCRUDPeUsers();
        Scanner inname=new Scanner(System.in);
        Scanner inpass=new Scanner(System.in);
        Scanner input=new Scanner(System.in);
        int Id=0;

        String name=null;
        String pass=null;
        User u = new User(Id,name, pass);



       int n=0;

        while (n!=-1){
            n=menu();

        switch (n) {
            case 1:
                obiect.readAllUsers();
                break;
            case 2:
                System.out.println("Insert name: ");
                name=inname.nextLine();
                u.setUsername(name);
                System.out.println("Insert password: ");
                pass=inpass.nextLine();
                u.setPassword(pass);


                obiect.insert(u);
                obiect.readAllUsers();
                break;
            case 3 :
                System.out.print("Insert ID you want to update:");
                Id=input.nextInt();
                u.setId(Id);
              //  System.out.println("Insert new name:");
              //  name=inname.nextLine();
             //   u.setUsername(name);
                System.out.print("Insert new password:");
                pass=inpass.nextLine();
                u.setPassword(pass);
                obiect.update(u);
                obiect.readAllUsers();
                break;
            case 4:
                System.out.print("Insert Id for delete the record : ");
                Id=input.nextInt();
                u.setId(Id);
                obiect.delete(u);
                obiect.readAllUsers();
                break;
            case -1:
                break;
        }}





    }
    private static Connection LoadConn() throws SQLException {
        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="Postgres.2023";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);
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
    private boolean insert (User u) throws SQLException {

        // COD CARE SCRIE IN DB

        // daca are rezultate, citirea lor

        // rulare sql

        PreparedStatement pSt = LoadConn().prepareStatement("insert into users(username, password) values(?,?)");
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


        // rulare sql
        Statement pSt = LoadConn().createStatement();

        ResultSet rs = pSt.executeQuery("select * from users order by username asc");



        while(rs.next()) {
            Integer i =rs.getInt("id");
            String user = rs.getString("username").trim();
            String p = rs.getString("password").trim();
            User u = new User(i,user,p);
            lu.add(u);
            System.out.println(u);
        }
        return lu;

    }


    private boolean update (User u) throws SQLException {

        // COD CARE SCRIE IN DB

        // daca are rezultate, citirea lor

        // rulare sql
        PreparedStatement pSt = LoadConn().prepareStatement("update users set password= ? where id = ?");
        pSt.setInt(2,u.getId());
        pSt.setString(1, u.getPassword());
       // pSt.setString(3, u.getUsername());
        int val = pSt.executeUpdate(); // 1

        boolean ok = false;
        if(val!=0)
            ok=true;
        return ok;
    }
    private void delete(User u ) throws SQLException {

        PreparedStatement pSt = LoadConn().prepareStatement("delete from users where id=? ");
        pSt.setInt(1,u.getId());
        int val = pSt.executeUpdate();
    }

}