package Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

public class DataBase {
    public static void insertB(String nume_fisier, String nume_tabela, int map, int playerX, int playerY, int playerSpeed, int scor, int maxHP, int maxST, int maxMP, int ability0, int ability1, int ability2, int ability3, int numberofEnemies0, int numberofEnemies1, int numberofEnemies2, String item0, String item1, String item2, String item3, int itemAmount0, int itemAmount1, int itemAmount2, int itemAmount3, int scores0, int scores1, int scores2) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + nume_fisier + ".db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            String del = "DELETE FROM TableForGame WHERE ROWID = 1;";
            stmt.executeUpdate(del);

            scor = GameScreen.scor;
            if(GameScreen.scoreSet == 3) {
                int shortestIndex = 0;
                for(int i = 0; i < 3; i++) {
                    if(GameScreen.scores[i] < GameScreen.scores[shortestIndex]) {
                        shortestIndex = i;
                        GameScreen.scoreSet = i;
                        break;
                    }
                }
                if(scor > GameScreen.scores[shortestIndex]) {
                    GameScreen.scoreSet = shortestIndex;
                }
            } else {
                if(scor > GameScreen.scores[GameScreen.scoreSet]) {
                    GameScreen.scores[GameScreen.scoreSet] = scor;
                    switch (GameScreen.scoreSet) {
                        case 0 -> scores0 = scor;
                        case 1 -> scores1 = scor;
                        case 2 -> scores2 = scor;
                    }
                }
            }

            String sql = "INSERT INTO " + nume_tabela + "(onMap, playerX, playerY, playerSpeed, scor, HP, ST, MP, ability0, ability1, ability2, ability3, numberOfEnemiesLeft0, numberOfEnemiesLeft1, numberOfEnemiesLeft2, itemAmount0, itemAmount1, itemAmount2, itemAmount3, scores0, scores1, scores2) " + "VALUES (" + map + "," + playerX + "," + playerY + "," + playerSpeed + "," + scor + "," + maxHP + "," + maxST + "," + maxMP + "," + ability0 + "," + ability1 + "," + ability2 + "," + ability3 + "," + numberofEnemies0 + "," + numberofEnemies1 + "," + numberofEnemies2 + ","  + itemAmount0 + "," + itemAmount1 + "," + itemAmount2 + "," + itemAmount3 + "," + scores0 + "," + scores1 + "," + scores2 + ");";
            String sql1 = null;
            /*if(item0 != null) {
                sql1 = "INSERT INTO " + nume_tabela + "(itemName0) " + "VALUES (" + item0  + ");";
                stmt.executeUpdate(sql1);
            }
            if(item1 != null) {
                sql1 = "INSERT INTO " + nume_tabela + "(itemName1) " + "VALUES (" + item1  + ");";
                stmt.executeUpdate(sql1);
            }
            if(item2 != null) {
                sql1 = "INSERT INTO " + nume_tabela + "(itemName2) " + "VALUES (" + item2  + ");";
                stmt.executeUpdate(sql1);
            }
            if(item3 != null) {
                sql1 = "INSERT INTO " + nume_tabela + "(itemName3) " + "VALUES (" + item3  + ");";
                stmt.executeUpdate(sql1);
            }*/
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static Object getB(String nume_fisier, String nume_tabela, String varType, String s) {
        Connection c = null;
        Statement stmt = null;
        Object value = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + nume_fisier + ".db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM " + nume_tabela + ";");
            while (rs.next()) {
                if(varType == "int")
                    value = rs.getInt(s);
                if(varType == "String")
                    value = rs.getString(s);
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return value;
    }


    public static int descending(String nume_fisier, String nume_tabela, int x) {
        int value = 0;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + nume_fisier + ".db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            String sql = "SELECT * FROM " + nume_tabela + " ORDER BY scor DESC;";
            //stmt.executeUpdate(sql);
            ResultSet rs = stmt.executeQuery(sql);
            for (int i = 0; i < x; i++) {
                rs.next();
                value = rs.getInt("scor");
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return value;

    }
}