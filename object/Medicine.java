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
    private String relevant_amount;
    
    
    public Medicine(int id, String name, String description, String relevant_amount)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.relevant_amount = relevant_amount;
    }    
    
    public Medicine(String name, String description, String relevant_amount)
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
    
    public void setRelevant_amount(String relevant_amount)
    {
        this.relevant_amount = relevant_amount;
    }
    
    public String getRelevant_amount()
    {
        return relevant_amount;
    }
    
    @Override
    public String toString()
    {
        return this.getName();
    }
    
}
