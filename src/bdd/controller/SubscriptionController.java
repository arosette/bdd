package bdd.controller;

import bdd.model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bdd.model.User;
import bdd.view.DialogBox;
import bdd.view.SubscriptionView;

public class SubscriptionController {

    private SubscriptionView subscriptionView;
    private DialogBox dialogBox;

    public SubscriptionController() {
	subscriptionView = new SubscriptionView();
    }   
}