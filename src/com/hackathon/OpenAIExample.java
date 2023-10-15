package com.hackathon;


import java.util.Random;
import java.util.Scanner;

public class OpenAIExample {
	
	static boolean myTurn = true;
	
	static boolean cheats = false;
	
    public static void main(String[] args) {
        char[][] ocean = new char[10][10];
        char[][] opOcean = new char[10][10];
        
        initializeOcean(ocean);
        initializeOcean(opOcean);
        
        placeShips(ocean);
        selfPlaceShips(opOcean);
        
        int myRemainingShips = 5;
        int opRemainingShips = 5;
        
        
        while (myRemainingShips > 0 && opRemainingShips > 0) {
        	int[] target;
        	int result;
        	if (myTurn) {
        		printOcean(ocean);
        		target = getUserInput();
        		result = takeShot(ocean, target);
        		if (result == 1) {
                    System.out.println("Congratulations! You sunk a battleship!");
                    opRemainingShips--;
                } else if (result == 0) {
                    System.out.println("Oops, you missed.");
                    myTurn = false;
                } else {
                    System.out.println("You already fired at this location. Try again.");
                }
        	}
        	
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        	if (!myTurn) {
        		//printOcean(ocean);
        		target = getOPInput();
        		
        		result = takeOPShot(opOcean, target);
        		if (result == 1) {
                    System.out.println("OP sunk a battleship!");
                    myRemainingShips--;
                } else if (result == 0) {
                    System.out.println("OP missed.");
                    myTurn = true;
                }
        	}
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
        }
        
        
        if (myRemainingShips == 0) {
        	System.out.println("Mission Failed, You Lost! We'll get 'em next time.");
        }
        else {
        	System.out.println("Congratulations You Won! You've sunk all the battleships!");
        }
        
    }
    
    public static void initializeOcean(char[][] ocean) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                ocean[row][col] = ' ';
            }
        }
    }
    
    public static void printOcean(char[][] ocean) {
    	
    	if (myTurn) {
    		System.out.println("Your OP's ocean");
    	}
    	else {
    		System.out.println("Your ocean");
    	}
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int row = 0; row < 10; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < 10; col++) {
            	if (cheats) {
            		System.out.print(ocean[row][col] + " ");
            	}
            	else {
            		if (ocean[row][col] == 'S') {
                    	//System.out.print(ocean[row][col] + " ");
                    	System.out.print("  ");
                    }
                    else {
                    	System.out.print(ocean[row][col] + " ");
                    }
            	}
                
            }
            System.out.println();
        }
    }
    
    public static void placeShips(char[][] ocean) {
        Random rand = new Random();
        int shipsPlaced = 0;
        
        while (shipsPlaced < 5) {
            int row = rand.nextInt(10);
            int col = rand.nextInt(10);
            
            if (ocean[row][col] != 'S') {
                ocean[row][col] = 'S';
                shipsPlaced++;
            }
        }
    }
    
    public static void selfPlaceShips(char[][] ocean) {
    	Scanner scanner = new Scanner(System.in);
        int shipsPlaced = 0;
        
        System.out.println("You're Placing Battleships: ");
        
        while (shipsPlaced < 5) {
        	System.out.print("Enter the row (0-9): ");
        	int row = scanner.nextInt();
            System.out.print("Enter the column (0-9): ");
            int col = scanner.nextInt();
            
            if ((row < 0 || row > 9) || (col < 0 || col > 9)) {
            	if ((row == 14) && (col == 53)) {
            		System.out.println("Cheats on!");
            		cheats = true;
            	}
            	System.out.println("Please enter a coordinate that is valid and empty!");
            }
            else {
            	if (ocean[row][col] != 'S') {
            		ocean[row][col] = 'S';
                    shipsPlaced++;
            	}
            	else {
            		System.out.println("Please enter a coordinate that is valid and empty!");
            	}
            }
        }
    }
    
    public static int[] getUserInput() {
        Scanner scanner = new Scanner(System.in);
        int[] target = new int[2];
        
        
        System.out.println("Its your turn to attack!");
        
        System.out.print("Enter the row (0-9): ");
        target[0] = scanner.nextInt();
        System.out.print("Enter the column (0-9): ");
        target[1] = scanner.nextInt();
        
        if ((target[0] < 0 || target[0] > 9) || (target[1] < 0 || target[1] > 9)) {
        	if ((target[0] == 14) && (target[1] == 53)) {
        		System.out.println("Cheats toggled!");
        		cheats = !cheats;
        	}
        	System.out.println("Please enter a coordinate that is valid and empty!");
        	target = getUserInput();
        }
        
        
        return target;
    }
    
    public static int[] getOPInput() {
    	Random rand = new Random();
        int[] target = new int[2];

        target[0] = rand.nextInt(10);

        target[1] = rand.nextInt(10);
        

        //System.out.print("OP fired at [" + target[0] + "][" + target[1] + "]");
        
        return target;
    }
    
    public static int takeShot(char[][] ocean, int[] target) {
        int row = target[0];
        int col = target[1];
        
        if (ocean[row][col] == 'S') {
            ocean[row][col] = '!';
            return 1;  // Sunk a ship
        } else if (ocean[row][col] == ' ') {
            ocean[row][col] = '-';
            return 0;  // Missed
        } else {
            return -1;  // Already fired at this location
        }
    }
    
    public static int takeOPShot(char[][] ocean, int[] target) {
        int row = target[0];
        int col = target[1];
        
        System.out.println("OP fired at [" + row + "][" + col + "]");
        
        if (ocean[row][col] == 'S') {
            ocean[row][col] = '!';
            return 1;  // Sunk a ship
        } else if (ocean[row][col] == ' ') {
            ocean[row][col] = '-';
            return 0;  // Missed
        } else {
            return -1;  // Already fired at this location
        }
    }
    
}
