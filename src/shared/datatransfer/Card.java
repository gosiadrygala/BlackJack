package shared.datatransfer;

import java.io.Serializable;

public class Card implements Serializable
{
  private String symbol;
  private int number;
  private String imageUrl;

  public Card(String symbol, int number, String imageUrl){
    this.symbol = symbol;
    this.number = number;
    this.imageUrl = imageUrl;
  }

  public String getCardSymbol(){
    return symbol;
  }

  public int getCardNumber(){
    return number;
  }

  public String getImageUrl(){
    return imageUrl;
  }

  public String toString(){
      return imageUrl;
    }
  }

