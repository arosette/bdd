package bdd;

import bdd.model.MySQLAccess;
import bdd.controller.LoginController;

public class Main {

    public static void main(String[] args) {
//	try {
//	    MySQLAccess instance = new MySQLAccess("test2user", "");
//	    instance.dataBaseExists();
//	    instance.showDataBase();
//	    instance.deleteDataBase();
//	} catch (Exception e) {
//	    e.printStackTrace();
//	}
	new LoginController();
    }
}
