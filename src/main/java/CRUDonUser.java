import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CRUDonUser {

    public void UserBooks(){
    CRUDonUser objUser=new CRUDonUser();
    Scanner intitle=new Scanner(System.in);
    Scanner inauthor=new Scanner(System.in);
    Scanner input=new Scanner(System.in);
    int Id=0;

    String title=null;
    String author=null;

    Books b=new Books(title,author);


    int n=0;

        while (n!=-1){
        n=menu();

        switch (n) {
            case 1:

                // objAdmin.readAllUsersBooks();
                objUser.readAllbooks();
                //  objAdmin.readAllbooks();
                break;
            case 2:
                System.out.println("Insert to which Id you want to add :");
                Id=input.nextInt();
                b.setId_user(Id);
                System.out.println("Insert title: ");
                title = intitle.nextLine();
                b.setTitle(title);
                System.out.println("Insert author: ");
                author = inauthor.nextLine();
                b.setAuthor(author);
                objUser.insert(b);
                objUser.readAllbooks();
                // objAdmin.readAllbooks(u);
                break;
            case 3:
                System.out.print("Insert ID you want to update:");
                Id = input.nextInt();
                b.setId_books(Id);
                while(true){
                    System.out.println("What do you want to update? :");
                    System.out.println("1.Title");
                    System.out.println("2.Author");
                    System.out.print("Choose : ");
                    Id = input.nextInt();
                    if (Id<1 || Id>2){
                        System.out.println("Wrong selection ,retry.");
                    }
                    else
                        break;
                }
                if (Id == 1) {
                    System.out.println("Insert new title:");
                    title = intitle.nextLine();
                    b.setTitle(title);
                    objUser.updateTitle(b);
                    objUser.readAllbooks();
                    break;
                } else {
                    System.out.print("Insert new author:");
                    author = inauthor.nextLine();
                    b.setAuthor(author);
                    System.out.print("Insert new title:");
                    title = intitle.nextLine();
                    b.setTitle(title);
                    objUser.updateTitle(b);
                    objUser.updateAuthor(b);
                    objUser.readAllbooks();
                }


                break;

            case 4:
                System.out.print("Insert Id for delete the record : ");
                Id=input.nextInt();
                b.setId_books(Id);
                objUser.delete(b);
                objUser.readAllbooks();
                break;
            case -1:
                break;
        }}
}
    private static int menu() {
        System.out.println("Menu");
        System.out.println("1-> display list! ");
        // System.out.println("2-> search a product!");
        System.out.println("2-> add a book!");
        System.out.println("3-> Update book!");
        System.out.println("4-> remove book!");
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


    protected List<Books> readAllbooks( )  {
        List<Books> lf = new ArrayList<>();
        try {
            PreparedStatement pSt = CRUDonAdmin.LoadConn().prepareStatement("SELECT * FROM books order by id asc" );

            ResultSet rs = pSt.executeQuery();

            while(rs.next()) {
                Long i =rs.getLong("id_user");
                Long ib= rs.getLong("id");
                String title = rs.getString("title").trim();
                String author = rs.getString("author").trim();
                Books b = new Books(title,author);
                b.setId_user(i);
                b.setId_books(ib);
                lf.add(b);
                System.out.println(b);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        }
        return lf;
    }

    private boolean insert (Books b) {

        int val = 0; // 1
        try {
            PreparedStatement pSt = CRUDonAdmin.LoadConn().prepareStatement("insert into books(title, author,id_user) values(?,?,?) ");
            pSt.setString(1, b.getTitle());
            pSt.setString(2, b.getAuthor());
           pSt.setLong(3,b.getId_user());
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
    private boolean updateTitle (Books b)  {

        int val = 0; // 1
        try {
            PreparedStatement pSt = CRUDonAdmin.LoadConn().prepareStatement("update books set title=? where id = ?");
            pSt.setLong(2,b.getId_books());
            pSt.setString(1, b.getTitle());
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
    private boolean updateAuthor (Books b)  {

        int val = 0; // 1
        try {
            PreparedStatement pSt = CRUDonAdmin.LoadConn().prepareStatement("update books set author= ?  where id = ?");
            pSt.setLong(2,b.getId_books());
            pSt.setString(1, b.getAuthor());

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
    private void delete(Books b )  {

        try {
            PreparedStatement pSt = CRUDonAdmin.LoadConn().prepareStatement("delete from books where id=? ");
            pSt.setLong(1,b.getId_books());
            int val = pSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        }
    }

}
