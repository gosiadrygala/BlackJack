package client.core;

import javafx.application.Application;
import javafx.stage.Stage;

public class BlackJackApp extends Application
{
  public void start(Stage stage) throws Exception{
    ClientFactory clientFactory = new ClientFactory();
    ModelFactory modelFactory = new ModelFactory(clientFactory);
    ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
    ViewHandler vh = new ViewHandler(viewModelFactory);
    vh.start();
  }
}
