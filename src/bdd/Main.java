package bdd;

import bdd.model.MysqlConnection;
import bdd.controller.LoginController;

public class Main {

    private static MysqlConnection mysqlConnection = null;

    public static void main(String[] args) {
	try {
	    mysqlConnection = MysqlConnection.getInstance();
	    mysqlConnection.dataBaseExists();
	    //mysqlConnection.showDataBase();
	    //mysqlConnection.deleteDataBase();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	new LoginController();
    }
}