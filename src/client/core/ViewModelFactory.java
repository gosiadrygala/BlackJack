package client.core;


import client.view.FirstView.FirstViewModel;

import java.io.IOException;

public class ViewModelFactory
{
  private FirstViewModel firstViewModel;
  //private SecondViewModel secondViewModel;
  private ModelFactory modelFactory;

  public ViewModelFactory(ModelFactory modelFactory){
    this.modelFactory = modelFactory;
  }

  public FirstViewModel getFirstViewModel() throws IOException
  {
    if (firstViewModel == null)
      firstViewModel = new FirstViewModel(modelFactory.getBlackJackDataModel());

    return firstViewModel;
  }

}
