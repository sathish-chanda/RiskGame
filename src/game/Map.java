package game;

import game.model.Continent;
import game.model.Country;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * This class implements the idea of a map.
 * It contains two variables, countryMap and countinentMap.
 * The countryMap is an ArrayList which contains all the countries.
 * The continentMao is an ArrayList which contains several sub-maps as continents.
 * This class also contains two methods to verify the correctness of input maps.
 */
public class Map {

    ArrayList<Country> countryMap;//the elements in the countryMap is examples of Country class
    ArrayList<Continent> continentMap;//the element in the continentMap is examples of Continent class

    Map(String mapName) {
        // ToDo the input of constructor is the name of the .txt
        // file some code for reading the file should be applied here
    }

    public Country searchCountry(String countryName) {
        for (int i = 0; i < countryMap.size(); i++)
            if (countryMap.get(i).getCountryName() == countryName)
                return countryMap.get(i);
        return null;
    }

    public Continent searchContinent(String continentName) {
        for (int i = 0; i < continentMap.size(); i++)
            if (continentMap.get(i).getContinentName() == continentName)
                return continentMap.get(i);
        return null;
    }

    /**
     * The verifyContinentMap method verify if all the country in a continent is connected.
     * First it traverse the ArrayList continentMap.
     * Then for each continent, the method traverse all the country in that continent.
     * if one country in the continent is in the adjacentCountryList of another country,
     * it means these two countries are connected.
     *
     * @return boolean
     */
    private boolean verifyContinentMap() {
        int continentMapSize = continentMap.size();
        for (int i = 0; i < continentMapSize; i++) {
            int countryMapSize = continentMap.get(i).getcountryMap().size();
            if ((countryMapSize == 1) || (countryMapSize == 0)) {
                return true;
            } else {
                ArrayList<Country> countryList = continentMap.get(i).getcountryMap();
                for (int j = 0; j < countryMapSize; j++) {
                    String countryName = countryList.get(j).getCountryName();
                    for (int k = 0; k < countryMapSize; k++) {
                        if (countryList.get(k).getAdjacentCountry().contains(countryName))
                            break;
                        else {
                            System.out.println("the input map is invalid");
                            System.out.println("the problem is in the country" + countryMap.get(k).getCountryName());
                            return false;
                        }
                    }
                }
            }


            Queue<Country> q = new LinkedList<Country>();
            int totalVisitedCountry = 0;
            Country startCountry = continentMap.get(i).getcountryMap().get(0);
            totalVisitedCountry++;
            startCountry.visitedContinentMap = true;
            q.offer(startCountry);
            while (!q.isEmpty()) {
                Country c = q.poll();
                for (int y = 0; y < c.getAdjacentCountry().size(); y++) {
                    Country adjacentCountry = searchCountry(c.getAdjacentCountry().get(y));
                    if ((adjacentCountry.visitedContinentMap == false) && (adjacentCountry.getContinentName() == continentMap.get(i).getContinentName())) {
                        totalVisitedCountry++;
                        adjacentCountry.visitedWholeMap = true;
                        q.offer(adjacentCountry);
                    }
                }
            }
            if (totalVisitedCountry != continentMap.get(i).getcountryMap().size()) {
                System.out.println("the input map is invalid");
                System.out.println("the problem is in the continent" + continentMap.get(i).getContinentName());
                return false;
            } else {
                System.out.println("the continent" + continentMap.get(i).getContinentName() + " map is valid");
            }
        }
        return true;
    }


    /**
     * The verifyCountryMap method verify if all the country of the whole map is connected.
     * The method traverse the ArrayList countryMap.
     * For every country in the countryMap, its adjacent countries(in the adjacentCountry), must also be in the countryMap.
     *
     * @return boolean
     */
    private boolean verifyCountryMap() {
        int countryMapSize = countryMap.size();
        for (int i = 0; i < countryMapSize; i++) {
            ArrayList<String> adjacentCountryNameList = countryMap.get(i).getAdjacentCountry();
            for (int j = 0; j < adjacentCountryNameList.size(); j++) {
                String adjacentCountryName = adjacentCountryNameList.get(j);
                boolean flag = false;
                for (int k = 0; k < countryMapSize; k++) {
                    if (countryMap.get(k).getCountryName() == adjacentCountryName) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    System.out.println("the input map is invalid");
                    System.out.println("the problem is in the country" + countryMap.get(i).getCountryName());
                    return false;
                }
            }
        }
        Queue<Country> q = new LinkedList<Country>();
        int totalVisitedCountry = 0;
        countryMap.get(0).visitedWholeMap = true;
        q.offer(countryMap.get(0));
        totalVisitedCountry++;
        while (!q.isEmpty()) {
            Country c = q.poll();
            for (int j = 0; j < c.getAdjacentCountry().size(); j++) {
                Country adjacentCountry = searchCountry(c.getAdjacentCountry().get(j));
                if (adjacentCountry.visitedWholeMap == false) {
                    totalVisitedCountry++;
                    adjacentCountry.visitedWholeMap = true;
                    q.offer(adjacentCountry);
                }
            }
        }
        if (totalVisitedCountry != countryMapSize) {
            System.out.println("the input map is invalid");
            return false;
        } else {
            System.out.println("the input map is valid");
            return true;
        }
    }

    void fortification(String countryName1, String countryName2, int playerID) {
        Queue<Country> q = new LinkedList<Country>();
        boolean flag = false;
        Country startCountry = searchCountry(countryName1);
        q.offer(startCountry);

        while (!q.isEmpty()) {
            Country c = q.poll();
            for (int i = 0; i < c.getAdjacentCountry().size(); i++) {
                Country adjacentCountry = searchCountry(c.getAdjacentCountry().get(i));
                if (adjacentCountry.getCountryName() == countryName2) {
                    flag = true;
                } else if (adjacentCountry.getPlayerID() == playerID) {
                    q.offer(adjacentCountry);
                } else
                    continue;
            }
        }
        if (flag) {
            int moveArmyNum = 0;
            System.out.println("please input the number of armys you want to move");
            System.out.println("you can move up to " + searchCountry(countryName1).getArmyNum() + " armies");
            Scanner readInput = new Scanner(System.in);
            if (readInput.hasNextInt())
                moveArmyNum = readInput.nextInt();
            while (moveArmyNum > searchCountry(countryName1).getArmyNum()) {
                System.out.println("exceed maximum number you can move");
                System.out.println("please input the number of armys you want to move");
                System.out.println("you can move up to " + searchCountry(countryName1).getArmyNum() + " armies");
                if (readInput.hasNextInt())
                    moveArmyNum = readInput.nextInt();
            }
            searchCountry(countryName1).updateArmyNum(0-moveArmyNum);
            searchCountry(countryName2).updateArmyNum(moveArmyNum);

        }
        else
            return;
    }
}