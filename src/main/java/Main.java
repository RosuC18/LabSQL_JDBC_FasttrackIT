import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    CRUDonUser objUser=new CRUDonUser();
    CRUDonAdmin objAdmin=new CRUDonAdmin();
    static long id;
    public void Login(){

        User u=null;
        while(true){
            System.out.println("Please login:");
            System.out.println("Insert username:");
            Scanner sc = new Scanner(System.in);
            String username = sc.nextLine();
            System.out.println("Insert password:");
            String pwd = sc.nextLine();
            u = new User(username,pwd);
            id= objAdmin.login(u);
            u.setId(id);
            System.out.println(u);
            if(id!=-1)
            {  break;}
            else {
                System.out.println("Username or password incorrect,retry:");
            }

        }
        while(true) {

            boolean isAdmin = objAdmin.isAdmin(u);
            if(!isAdmin) {

                System.out.println("You are logged as User");
                objUser.UserBooks();
                break;
            }
            else
            {
                System.out.println("You are logged as Admin");
                objAdmin.Admin();
                break;
            }
        }
    }


    public static void main(String[] args)  {

        Main main=new Main();
        main.Login();
        }
    }

