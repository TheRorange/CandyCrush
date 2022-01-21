public class Game extends Main {

    private static String[][] mainTable = new String[10][10];
    int score = 0;
    int flag = 0, hintFlag = 0;
    static int chosenX = -1, chosenY = -1, chosen2X = -1, chosen2Y = -1;

    // constructor for primary table
    Game(String[][] mainTable) throws Exception{
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                this.mainTable[i][j] = mainTable[i][j];
            }
        }
        scanTable();
    }

    // constructor for testing hint options
    Game(String[][] mainTable, int hint) throws Exception{
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                this.mainTable[i][j] = mainTable[i][j];
            }
        }
        hintTable();
    }

    // constructor for a possible swap of candies
    Game(String[][] mainTable, int chosenX, int chosenY, int chosen2X, int chosen2Y) throws Exception {
        this.chosenX = chosenX;
        this.chosenY = chosenY;
        this.chosen2X = chosen2X;
        this.chosen2Y = chosen2Y;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                this.mainTable[i][j] = mainTable[i][j];
            }
        }
        scanTable();
    }

    // main table getter
    public String[][] getMainTable() throws Exception{
        return mainTable;
    }

    // checking options of swap for hint
    public void hintTable() throws Exception{
        hintFlag = 0;
        if (overallCheck())
            hintFlag = 1;
    }

    // removing options of more than 3 candies in a row or a column til there is none
    public void scanTable() throws Exception{
        flag = 0;
        while (overallCheck()){
            flag = 1;
            tableCheck();
        }
    }

    // checking if there is any option to be removed automatically
    public boolean overallCheck() throws Exception{
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++) {
                if (j <= 7 && mainTable[i][j].charAt(2) == mainTable[i][j + 1].charAt(2) &&
                        mainTable[i][j + 1].charAt(2) == mainTable[i][j + 2].charAt(2))
                    return true;
                if (i <= 7 && mainTable[i][j].charAt(2) == mainTable[i + 1][j].charAt(2) &&
                        mainTable[i + 1][j].charAt(2) == mainTable[i + 2][j].charAt(2))
                    return true;
            }
        return false;
    }

    // removing options of more than 3 candies in a row or a column and generating candies with feature of explosion
    // divided into different situations and checking them separately(i.e. if LR./LC./RC. is generated or how many
    // candies are in a row/column, etc.)
    public void tableCheck() throws Exception {
        boolean checked1 = false, checked2 = false, checked3 = false, checked4 = false;
        int irc = -1, jrc = -1;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                int lst1 = 1, lst2 = 1;
                if (i <= 7)
                    lst1 = rowCheck(i, j);
                if (j <= 7)
                    lst2 = colCheck(i, j);
                if (i <= 7 && lst1 > lst2) {
                    if (lst1 > 2) {
                        for (int i2 = i; i2 < i + lst1; i2++) {
                            if (mainTable[i2][j].startsWith("LR")) {
                                lrExplosion(j);
                                checked3 = true;
                            } else if (mainTable[i2][j].startsWith("LC")) {
                                lcExplosion(i2);
                            } else if (mainTable[i2][j].startsWith("RC")) {
                                irc = i2;
                                jrc = j;
                                checked4 = true;
                            }
                            if (i2 == i + lst1 - 1 && !checked3) {
                                for (int k = j; k > 0; k--)
                                    for (int q = i; q < i + lst1; q++) {
                                        if (lst1 == 4 && k == j && q == chosenX && k == Math.min(chosenY, chosen2Y) && !checked1) {
                                            mainTable[q][k] = "LR" + mainTable[q][k].charAt(2);
                                            checked1 = true;
                                        } else if (lst1 == 4 && k == j && q == chosenX && k == Math.max(chosenY, chosen2Y) && !checked2) {
                                            mainTable[q][k] = "LR" + mainTable[q][k].charAt(2);
                                            checked1 = true;
                                            checked2 = true;
                                        } else if (lst1 == 4 && k == j && q == i && checked1)
                                            mainTable[q][k] = "LR" + mainTable[q][k].charAt(2);
                                        else if (lst1 >= 5 && k == j && q == chosenX && k == Math.min(chosenY, chosen2Y) && !checked1) {
                                            mainTable[q][k] = "RC" + mainTable[q][k].charAt(2);
                                            checked1 = true;
                                        } else if (lst1 == 5 && k == j && q == chosenX && k == Math.max(chosenY, chosen2Y) && !checked2) {
                                            mainTable[q][k] = "RC" + mainTable[q][k].charAt(2);
                                            checked1 = true;
                                            checked2 = true;
                                        } else if (lst1 == 5 && k == j && q == i && checked1)
                                            mainTable[q][k] = "RC" + mainTable[q][k].charAt(2);
                                        else if (lst1 == 3 || (q != chosenX && (lst1 == 4 || lst1 == 5))) {
                                            if (lst1 == 3 && q == chosenX && k == Math.min(chosenY, chosen2Y))
                                                checked1 = true;
                                            mainTable[q][k] = mainTable[q][k - 1];
                                        }
                                        if (chosenX != -1)
                                            score += 5;
                                    }
                                for (int q = i; q < i + lst1; q++) {
                                    if (lst1 == 4 && q == chosenX && Math.min(chosenY, chosen2Y) == 0 && !checked1) {
                                        mainTable[q][0] = "LR" + mainTable[q][0].charAt(2);
                                        checked1 = true;
                                    } else if (lst1 == 4 && j == 0 && q == i && checked1)
                                        mainTable[q][0] = "LR" + mainTable[q][0].charAt(2);
                                    else if (lst1 >= 5 && q == chosenX && Math.min(chosenY, chosen2Y) == 0 && !checked1) {
                                        mainTable[q][0] = "RC" + mainTable[q][0].charAt(2);
                                        checked1 = true;
                                    } else if (lst1 == 5 && j == 0 && q == i && checked1)
                                        mainTable[q][0] = "RC" + mainTable[q][0].charAt(2);
                                    else if (lst1 == 3 || (q != chosenX && (lst1 == 4 || lst1 == 5)))
                                        mainTable[q][0] = randomGenerator();
                                    if (chosenX != -1)
                                        score += 5;
                                }
                                if (checked4){
                                    rcExplosion(irc, jrc);
                                }
                            }
                        }
                    }
                } else if (j <= 7 && lst1 <= lst2) {
                    if (lst2 > 2) {
                        for (int j2 = j; j2 < j + lst2; j2++) {
                            if (mainTable[i][j2].startsWith("LR")) {
                                lrExplosion(j2);
                            } else if (mainTable[i][j2].startsWith("LC")) {
                                checked3 = true;
                                lcExplosion(i);
                            } else if (mainTable[i][j2].startsWith("RC")) {
                                checked4 = true;
                                irc = i;
                                jrc = j2;
                            }
                            if (j2 == j + lst2 - 1 && !checked3) {
                                int q = j;
                                for (int k = j + lst2 - 1; k >= j; k--) {
                                    if (q == 0 && lst2 == 4 && k == j + lst2 - 1 && chosenX != -1)
                                        mainTable[i][k] = "LC" + mainTable[i][k].charAt(2);
                                    else if (q == 0 && lst2 == 5 && k == j + lst2 - 1 && chosenX != -1)
                                        mainTable[i][k] = "RC" + mainTable[i][k].charAt(2);
                                    else if (q > 0) {
                                        if (lst2 == 4 && k == j + lst2 - 1 && chosenX != -1)
                                            mainTable[i][k] = "LC" + mainTable[i][k].charAt(2);
                                        else if (lst2 == 5 && k == j + lst2 - 1 && chosenX != -1)
                                            mainTable[i][k] = "RC" + mainTable[i][k].charAt(2);
                                        else {
                                            mainTable[i][k] = mainTable[i][q - 1];
                                            q--;
                                        }
                                    } else
                                        mainTable[i][k] = randomGenerator();
                                    if (chosenX != -1)
                                        score += 5;
                                }
                                for (int k = j - 1; k >= lst2; k--) {
                                    mainTable[i][k] = mainTable[i][q - 1];
                                    q--;
                                }
                                if (j > 0)
                                    for (int k = lst2 - 1; k >= 0; k--)
                                        mainTable[i][k] = randomGenerator();
                                if (checked4)
                                    rcExplosion(irc, jrc);
                            }
                        }
                    }
                }
            }
    }

    // explosion of an exploding candy for a row
    // also checking if there is LC./RC. in the row or not and making them explode too
    public void lrExplosion(int j) throws Exception {
        int flagrcx = -1, flagrcy = -1, flaglcx = -1, flaglcy = -1;
        for (int i = 0; i < 10; i++) {
            for (int k = j; k > 0; k--) {
                if (mainTable[i][k].startsWith("LC")) {
                    flaglcx = i;
                    flaglcy = k;
                    mainTable[i][k] = randomGenerator();
                }
                else if (mainTable[i][k].startsWith("RC")) {
                    flagrcx = i;
                    flagrcy = k;
                    mainTable[i][k] = randomGenerator();
                }
                else {
                    if (chosenX != -1) {
                        if (mainTable[i][k].startsWith("LR"))
                            score += 10;
                        else
                            score += 5;
                    }
                    mainTable[i][k] = mainTable[i][k - 1];
                }
            }
            if (mainTable[i][0].startsWith("LC")) {
                flaglcx = i;
                flaglcy = 0;
                mainTable[i][0] = randomGenerator();
            }
            else if (mainTable[i][0].startsWith("RC")) {
                flagrcx = i;
                flagrcy = 0;
                mainTable[i][0] = randomGenerator();
            }
            else{
                if (chosenX != -1) {
                    if (mainTable[i][0].startsWith("LR"))
                        score += 10;
                    else
                        score += 5;
                }
                mainTable[i][0] = randomGenerator();
            }
        }
        if (flagrcx != -1 && flagrcy != -1)
            rcExplosion(flagrcx, flagrcy);
        if (flaglcx != -1 && flaglcy != -1)
            lcExplosion(flaglcx);
    }

    // explosion of an exploding candy for a column
    // also checking if there is LR./RC. in the column or not and making them explode too
    public void lcExplosion(int i) throws Exception {
        int flagrcx = -1, flagrcy = -1, flaglrx = -1, flaglry = -1;
        for (int j = 0; j < 10; j++) {
            if (mainTable[i][j].startsWith("LR")) {
                flaglrx = i;
                flaglry = j;
                mainTable[i][j] = randomGenerator();
            }
            else if (mainTable[i][j].startsWith("RC")) {
                flagrcx = i;
                flagrcy = j;
                mainTable[i][j] = randomGenerator();
            }
            else {
                if (chosenX != -1) {
                    if (mainTable[i][j].startsWith("LC"))
                        score += 10;
                    else
                        score += 5;
                }
                mainTable[i][j] = randomGenerator();
            }
        }
        if (flaglrx != -1 && flaglry != -1)
            lrExplosion(flaglry);
        if (flagrcx != -1 && flagrcy != -1)
            rcExplosion(flagrcx, flagrcy);
    }

    // explosion of an exploding candy for a 5 * 5 square
    // also checking if there is LR./LC./RC. in the 5 * 5 square or not and making them explode too
    public void rcExplosion(int i, int j) throws Exception {
        int flagrcx = -1, flagrcy = -1, flaglrx = -1, flaglry = -1, flaglcx = -1, flaglcy = -1;
        for (int i2 = Math.max(0, i - 2); i2 <= Math.min(9, i + 2); i2++)
            for (int j2 = Math.min(9, j + 2); j2 >= Math.max(0, j - 2); j2--) {
                if (chosenX != -1) {
                    if (mainTable[i2][j2].startsWith("RC"))
                        score += 15;
                    else
                        score += 5;
                }
                if (mainTable[i2][j2].startsWith("LR")) {
                    flaglrx = i2;
                    flaglry = j2;
                    mainTable[i2][j2] = randomGenerator();
                }
                else if (mainTable[i2][j2].startsWith("LC")){
                    flaglcx = i2;
                    flaglcy = j2;
                    mainTable[i2][j2] = randomGenerator();
                }
                else if (mainTable[i2][j2].startsWith("RC") && i2 != i && j2 != j) {
                    flagrcx = i2;
                    flagrcy = j2;
                    mainTable[i2][j2] = randomGenerator();
                }
                else if (j2 > 4)
                    mainTable[i2][j2] = mainTable[i2][j2 - 5];
                else
                    mainTable[i2][j2] = randomGenerator();
            }
        if (flagrcx != -1 && flagrcy != -1)
            rcExplosion(flagrcx, flagrcy);
        if (flaglrx != -1 && flaglry != -1)
            lrExplosion(flaglry);
        if (flaglcx != -1 && flaglcy != -1)
            lcExplosion(flaglcx);
    }

    // checking candies with the same color in a row and returning the number of them
    public int rowCheck(int frst, int col) throws Exception{
        int ans = 1;
        for(int i = frst; i < 9; i++)
            if(mainTable[i][col].charAt(2) == mainTable[i + 1][col].charAt(2))
                ans++;
            else
                return ans;
        return ans;
    }

    // checking candies with the same color in a column and returning the number of them
    public int colCheck(int row, int frst) throws Exception{
        int ans = 1;
        for(int i = frst; i < 9; i++)
            if(mainTable[row][i].charAt(2) == mainTable[row][i + 1].charAt(2))
                ans++;
            else
                return ans;
        return ans;
    }
}
