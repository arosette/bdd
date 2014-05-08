package bdd;

import bdd.model.MysqlConnection;
import bdd.controller.LoginController;

public class Main {

    public static void main(String[] args) {
	try {
	    MysqlConnection instance = MysqlConnection.getInstance();
	    instance.dataBaseExists();
	    instance.showDataBase();
	    instance.deleteDataBase();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	new LoginController();
    }
}
