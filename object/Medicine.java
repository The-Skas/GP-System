/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import framework.GPSISObject;

/**
 *
 * @author oa305
 */
public class Medicine extends GPSISObject{
    
    private int id;
    private String name;
    private String description;
    private int relevant_amount;
    
    
    public Medicine(int id, String name, String description, int relevant_amount)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.relevant_amount = relevant_amount;
    }    
    
    public Medicine(String name, String description, int relevant_amount)
    {
        this.name = name;
        this.description = description;
        this.relevant_amount = relevant_amount;
    }
    
    public void setID(int id)
    {
        this.id = id;
    }
    
    public int getID()
    {
        return id;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setRelevant_amount(int relevant_amount)
    {
        this.relevant_amount = relevant_amount;
    }
    
    public int getRelevant_amount()
    {
        return relevant_amount;
    }
    
}
