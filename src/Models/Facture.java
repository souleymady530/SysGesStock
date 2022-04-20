/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import static Models.Main.cnx;
import static Models.Main.ps;
import java.awt.HeadlessException;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ofilw
 */
public class Facture
{
    
    private int id,idpan;
    private String emplacement;

    public Facture() {
    }

    public Facture(int idpan,String emplacement) {
        this.emplacement = emplacement;
        this.idpan=idpan;
    }

    public Facture(int id, int idpan,String emplacement) {
        this.id = id;
        this.emplacement = emplacement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }
    
    
     public void create()
    {
         cnx=ClasseConnection.seConnecter();
        try
        {
            ps=cnx.prepareStatement("INSERT INTO les_factures(emplacement,idpan) VALUES(?,?)");
            ps.setString(1, emplacement);
           ps.setInt(2, idpan);
            ps.execute();           
            ps.close();
          
            
          ps.close();
            JOptionPane.showMessageDialog(null,"Enregistrement effectué avec succès","SyGestStock/Information",JOptionPane.INFORMATION_MESSAGE);  
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
            ps=cnx.prepareStatement("DELETE FROM les_factures WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
           JOptionPane.showMessageDialog(null, "Facture supprimée avec succès","SyGestStock/Information",JOptionPane.INFORMATION_MESSAGE);
              
          ps.close();   
        }
        catch(HeadlessException | SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Impossible de supprimer la facture","SyGestStock/Information",JOptionPane.WARNING_MESSAGE);
        } 
    }
    
    public void generer()
    {
        
    }
}
