package bdd;

import bdd.model.MysqlConnection;
import bdd.parser.DataParser;
import bdd.controller.LoginController;

public class Main {

    private static MysqlConnection mysqlConnection = null;

    public static void main(String[] args) {

	if (args.length == 0) {
	    // Lancement normal de l'application
	    try {
		mysqlConnection = MysqlConnection.getInstance();
		mysqlConnection.dataBaseExists();

		new LoginController();

	    } catch (Exception e) {
		e.printStackTrace();
	    }
	} else if (args.length == 1) {
	    if ("delete".equals(args[0])) {
		// On supprime les tables
		try {
		    mysqlConnection = MysqlConnection.getInstance();
		    mysqlConnection.deleteDataBase();

		} catch (Exception e) {
		    e.printStackTrace();
		}

	    } else {
		// Erreur
		System.out
			.println("Pour lancer l'application en mode normal : java -jar rss-manager.jar\nPour supprimer les tables : java -jar rss-manager.jar delete\nPour peupler la base de donnees a partir d'un fichier xml : java -jar rss-manager.jar add <fichier xml>");
	    }
	} else if (args.length == 2) {
	    if ("add".equals(args[0])) {
		// On peuple la bdd a partir d'un fichier xml
		try {
		    mysqlConnection = MysqlConnection.getInstance();
		    mysqlConnection.dataBaseExists();

		} catch (Exception e) {
		    e.printStackTrace();
		}
		DataParser dataParser = new DataParser(args[1]);
		dataParser.populateDb();
	    } else {
		// Erreur
		System.out
			.println("Pour lancer l'application en mode normal : java -jar rss-manager.jar\nPour supprimer les tables : java -jar rss-manager.jar delete\nPour peupler la base de donnees a partir d'un fichier xml : java -jar rss-manager.jar add <fichier xml>");
	    }
	} else {
	    // Erreur
	    System.out
		    .println("Pour lancer l'application en mode normal : java -jar rss-manager.jar\nPour supprimer les tables : java -jar rss-manager.jar delete\nPour peupler la base de donnees a partir d'un fichier xml : java -jar rss-manager.jar add <fichier xml>");
	}

    }
}