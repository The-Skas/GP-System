/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package module;

/**
 *
 * @author skas
 * 
 * This interface serves to have different classes communicate to each other.
 * It is used to pass values from one class (JFrame/JPanel) to another.
 */
public interface Broadcastable {
    
    public void broadcast (Object obj);
   
}
