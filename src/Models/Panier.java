/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import static Models.Main.cnx;
import static Models.Main.ps;
import static Models.Main.res;
import static Models.Main.st;
import java.awt.HeadlessException;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ofilw
 */
public class Panier 
{
    private int id;
    private String code;

    public Panier() {
    }

    public Panier( String code) {
        this.code = code;
    }

    public Panier(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    
     public void create()
    {
        cnx=ClasseConnection.seConnecter();
        try
        {
            ps=cnx.prepareStatement("INSERT INTO les_paniers(code) VALUES(?)");
            
            ps.setString(1, code);
            ps.execute();           
            ps.close();
          
            
          ps.close();
            //JOptionPane.showMessageDialog(null,"Enregistrement effectué avec succès","SyGestStock/Information",JOptionPane.INFORMATION_MESSAGE);  
        }
        catch(Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Echec de l'enregistrement. Veuillez bien verifier les informqtion et reéssayer","SyGestStock/Information",JOptionPane.WARNING_MESSAGE);  
        }
    }
    
    public void update()
    {
        
    }
    
    public void select()
    {
        
    }
    public void delete()
    {
        cnx=ClasseConnection.seConnecter();
        try
        {
            ps=cnx.prepareStatement("DELETE FROM les_paniers WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
           JOptionPane.showMessageDialog(null, "Panier supprimé avec succès","SyGestStock/Information",JOptionPane.INFORMATION_MESSAGE);
              
          ps.close();   
        }
        catch(HeadlessException | SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Impossible de supprimer le panier.","SyGestStock/Information",JOptionPane.WARNING_MESSAGE);
        } 
    }
    
    
      public static String chargerCode()
    {
        String num="";
        //Ici on recupere le dernier ide dans la table ,on multiplie pas trois et on soustrait dans 100. pour 
        try
        {
            int index=0;
            cnx=ClasseConnection.seConnecter();
            st=cnx.createStatement();
            res=st.executeQuery("SELECT id FROM utilisateurs ORDER BY id DESC");
            if(res.next())
            {
                index=res.getInt("id");
            }
            
            num="000000";
            
            String  str=String.valueOf(index);
            index++;
             int len=str.length();
              num=num.substring(0,6-len);
            num+=String.valueOf(index);
           
                    
                  
             st.close();
          res.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return "F A C_NO_"+num;
    }
      
      public static int getIdByCode(String code)
      {
          int index=0;
          try
        {
            cnx=ClasseConnection.seConnecter();
            st=cnx.createStatement();
            res=st.executeQuery("SELECT id FROM les_paniers WHERE code=\""+code+"\"");
            if(res.next())
            {
                index=res.getInt("id");
            }
            
        }
          catch(SQLException e)
          {
              e.printStackTrace();
          }
          return index;
      }
      
}
