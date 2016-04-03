package limeri.cards.model;

import limeri.cards.*;

public class CardModel extends Model implements Comparable<CardModel>
{
    private String name;
    private int    rank;
    private String suit;
    private int    suitRank;
    private String imagePath;

    public String getName(){return this.name;}
    public void setName(String name){this.name = name;}

    public int getRank(){return this.rank;}
    public void setRank(int rank){this.rank = rank;}

    public String getSuit(){return this.suit;}
    public void setSuit(String suit){this.suit = suit;}

    public int getSuitRank() {return suitRank;}
    public void setSuitRank(int suitRank) {this.suitRank = suitRank;}

    public String getImagePath() {return imagePath;}
    public void setImagePath(String imagePath) {this.imagePath = imagePath;}

    public CardModel(String name, String suit, int rank, int suitRank, String imagePath)
    {
        setName(name);
        setSuit(suit);
        setRank(rank);
        setSuitRank(suitRank);
        setImagePath(imagePath);
    }

    @Override
    public int compareTo(CardModel compareCard) {
        int compareSuitRank = compareCard.getSuitRank();
        int mySuitRank      = this.getSuitRank();

        if (mySuitRank == compareSuitRank) {
            // If the suits are equal, compare the rank of the cards.
            int compareRank = compareCard.getRank();
            int myRank      = this.getRank();
            if (myRank > compareRank) {
                return 1;
            }
            else if (myRank < compareRank) {
                return -1;
            }
            else {
                return 0;
            }
        }
        else {
            // Compare the suits.  The suit rank is Spades, Hearts, Clubs, Diamonds.
            if (mySuitRank > compareSuitRank) {
                return 1;
            }
            else {
                return -1;
            }
        }
    }

} // end class
