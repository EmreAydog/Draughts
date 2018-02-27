/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.s2id90.group48;
import nl.tue.s2id90.draughts.DraughtsState;
import java.util.*;
/**
 *
 * @author s141010
 */
/** A method that evaluates the given state. */
    // ToDo: write an appropriate evaluation function
public class Evaluate {
    static ArrayList<Integer> whiteplace =new ArrayList<>();
    static ArrayList<Integer> blackplace =new ArrayList<>();
    static int value =0;
    static int centerworth=1;
    static int balanceworth=1;
    static int outpostworth=1;
    static int neighboursworth=1;
    static int diagonalworth=1;
    static int tempiworth=1;
    static int pieceworth=1;
    
    //this cacluates how many pieces are in the center of the board this is a stronger postition
    static void center(){
      for(int i = 0; i < whiteplace.size(); i++){
        for(int j=0; j<10;j++){
            if(whiteplace.get(i)<2+j*5 && whiteplace.get(i)>4+j*5){ 
                value=value+centerworth;
            } 
        }
      }
      for(int i = 0; i < blackplace.size(); i++){
        for(int j=0; j<10;j++){
            if(blackplace.get(i)<=2+j*5 && blackplace.get(i)>=4+j*5){ 
                value=value-centerworth;
            } 
        }
      }
    }
    
    //this looks at the balance of pieces that are on the left and right side of the board, penanlties are given is one side has more pieces than the other
    static void balance(){
    int whitebalance =0;
    int blackbalance = 0;
      for(int i = 0; i < whiteplace.size(); i++){
        for(int j=0; j<10;j++){
            if(whiteplace.get(i)<3+j*5 && whiteplace.get(i)>=1+j*5){ 
                whitebalance=whitebalance+1;
            } 
            if(whiteplace.get(i)<=5+j*5 && whiteplace.get(i)>3+j*5){ 
                whitebalance=whitebalance-1;
            } 
        }
      }
      for(int i = 0; i < blackplace.size(); i++){
        for(int j=0; j<10;j++){
            if(blackplace.get(i)<3+j*5 && blackplace.get(i)>=1+j*5){ 
                blackbalance=blackbalance+1;
            } 
            if(blackplace.get(i)<=5+j*5 && blackplace.get(i)>3+j*5){ 
                blackbalance=blackbalance-1;
            } 
        }
      }
      value=value+balanceworth*Math.abs(blackbalance);
    }
    
    //points are awarded for having more neighbours and for making diagonal lines of at leat 3 pieces
    static void formations(int[] pieces){
    int neighbours=0;
    int diagonal=0;
      for(int i = 0; i < whiteplace.size(); i++){
            if(whiteplace.get(i)==1){       //top left corner
                if(pieces[whiteplace.get(i)+1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+5]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+6]==1){
                    neighbours=neighbours+1;
                    if(pieces[whiteplace.get(i)+11]==1){ 
                        diagonal=diagonal+1;
                    }                   
                }
            }else if(whiteplace.get(i)==5){ //top right corner
                if(pieces[whiteplace.get(i)-1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+5]==1){
                    neighbours=neighbours+1;
                    if(pieces[i+9]==1){ 
                        diagonal=diagonal+1;
                    }
                }
            }else if(whiteplace.get(i)==46){ //bottom left corner
                if(pieces[whiteplace.get(i)+1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-5]==1){neighbours=neighbours+1;}
            }else if(whiteplace.get(i)==50){ //bottom right corner
                if(pieces[whiteplace.get(i)-1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-5]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-6]==1){neighbours=neighbours+1;}
            }else if(whiteplace.get(i)>=2 && whiteplace.get(i)<=4){ //top row
                if(pieces[whiteplace.get(i)-1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+5]==1){
                    neighbours=neighbours+1;
                    if(pieces[whiteplace.get(i)+9]==1){
                        diagonal=diagonal+1;
                    }
                }
                if(pieces[whiteplace.get(i)+6]==1){
                    neighbours=neighbours+1;
                    if(pieces[whiteplace.get(i)+11]==1){
                        diagonal=diagonal+1;
                    }
                }
            }else if(whiteplace.get(i)>=2 && whiteplace.get(i)<=4){ //bottom row
                if(pieces[whiteplace.get(i)-1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-5]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-6]==1){neighbours=neighbours+1;}
            }else if(whiteplace.get(i)==6 || whiteplace.get(i)==16 || whiteplace.get(i)==26 || whiteplace.get(i)==36){ //most left colom
                if(pieces[whiteplace.get(i)+1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-5]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+5]==1){
                    neighbours=neighbours+1;
                    if(pieces[whiteplace.get(i)+11]==1){
                        diagonal=diagonal+1;
                    }
                }
            }else if(whiteplace.get(i)==11 || whiteplace.get(i)==21 || whiteplace.get(i)==31 || whiteplace.get(i)==41){ //second most left colom
                if(pieces[whiteplace.get(i)+1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-4]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-5]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+5]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+6]==1){
                    neighbours=neighbours+1;
                    if(whiteplace.get(i)!=41){
                        if(pieces[whiteplace.get(i)+11]==1){
                            diagonal=diagonal+1;
                        }
                    }
                }
            }else if(whiteplace.get(i)==15 || whiteplace.get(i)==25 || whiteplace.get(i)==35 || whiteplace.get(i)==45){ //most right colom
                if(pieces[whiteplace.get(i)-1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-5]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+5]==1){
                    neighbours=neighbours+1;
                    if(whiteplace.get(i)!= 45){ 
                        if(pieces[whiteplace.get(i)+9]==1){
                            diagonal=diagonal+1;
                        }
                    }
                }
            }else if(whiteplace.get(i)==10 || whiteplace.get(i)==20 || whiteplace.get(i)==30 || whiteplace.get(i)==40){ //second most right colom
                if(pieces[whiteplace.get(i)-1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-5]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-6]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+4]==1){
                    neighbours=neighbours+1;
                    if(pieces[whiteplace.get(i)+9]==1){
                        diagonal=diagonal+1;
                    }
                }
            }else if((whiteplace.get(i)>=12 && whiteplace.get(i)<=14) || (whiteplace.get(i)>=22 && whiteplace.get(i)<=24) || (whiteplace.get(i)>=32 && whiteplace.get(i)<=34)){ //even rows
                if(pieces[whiteplace.get(i)-1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-4]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-5]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+5]==1){
                    neighbours=neighbours+1;
                    if(pieces[whiteplace.get(i)+9]==1){
                        diagonal=diagonal+1;
                    }
                }
                if(pieces[whiteplace.get(i)+6]==1){
                    neighbours=neighbours+1;
                    if(pieces[whiteplace.get(i)+11]==1){
                        diagonal=diagonal+1;
                    }
                }
            }else if(whiteplace.get(i)>=42 && whiteplace.get(i)<=44 ){ //second last row
                if(pieces[whiteplace.get(i)-1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-4]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-5]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+5]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+6]==1){neighbours=neighbours+1;}
            }else if(whiteplace.get(i)>=47 && whiteplace.get(i)<=49 ){ // last row
                if(pieces[whiteplace.get(i)-1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-4]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-5]==1){neighbours=neighbours+1;}
             }else{ //unevenrows
                if(pieces[whiteplace.get(i)-1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+1]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-5]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)-6]==1){neighbours=neighbours+1;}
                if(pieces[whiteplace.get(i)+4]==1){
                    neighbours=neighbours+1;
                    if(pieces[whiteplace.get(i)+9]==1){
                        diagonal=diagonal+1;
                    }
                }
                if(pieces[whiteplace.get(i)+5]==1){
                    neighbours=neighbours+1;
                    if(pieces[whiteplace.get(i)+11]==1){
                        diagonal=diagonal+1;
                    }
                }     
             }
          }
      
      neighbours=neighbours/2;
      value= value+neighbours*neighboursworth+diagonal*diagonalworth;
    }
    
    // the closer the pieces are to the tempi line (which makes kings) the more points are given
    static void tempi(){
        for(int i = 0; i < whiteplace.size(); i++){
            for(int j=0; j<10;j++){
                if(whiteplace.get(i)<1+j*5 && whiteplace.get(i)>5+j*5){ 
                    value=value+(9-j)*tempiworth;
                }
            }
        }            
        for(int i = 0; i < blackplace.size(); i++){
            for(int j=0; j<10;j++){
                if(blackplace.get(i)<1+j*5 && blackplace.get(i)>5+j*5){ 
                    value=value-(9-j)*tempiworth;
                }
            }
        }
    }
    
    // the value of the cuurent viewd state is calculated, taking the amount of pieces into account
    // furtermore the places where pieces are on the board are stored in an arraylist. for the places on the board see the beginning of draughtstate
    public static int evaluate(DraughtsState state) { 
        int[] pieces = state.getPieces();
        for(int i=1; i<51; i++){
            if(pieces[i]==1){
                value= value+pieceworth;
                whiteplace.add(i);
            }else if(pieces[i]==2){
                value= value-pieceworth;
                blackplace.add(i);
            }else if(pieces[i]==3){
                value= value+3*pieceworth;
                whiteplace.add(i);
            }else if(pieces[i]==4){
                value= value-3*pieceworth;
                blackplace.add(i);
            }
        }
        center();
        balance();
        formations(pieces);
        tempi();
        return value;
    }
    
}


