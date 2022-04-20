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
public class Achat 
{
    private int id,qte;
    private String codePanier,libelle,prixUnitaire;

    public Achat(int id, int qte, String codePanier, String libelle, String prixUnitaire) {
        this.id = id;
        this.qte = qte;
        this.codePanier = codePanier;
        this.libelle = libelle;
        this.prixUnitaire = prixUnitaire;
    }

    public Achat() {
    }

    public Achat(int qte, String codePanier, String libelle, String prixUnitaire) {
        this.qte = qte;
        this.codePanier = codePanier;
        this.libelle = libelle;
        this.prixUnitaire = prixUnitaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getCodePanier() {
        return codePanier;
    }

    public void setCodePanier(String codePanier) {
        this.codePanier = codePanier;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(String prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    
    
     public void create()
    {
         cnx=ClasseConnection.seConnecter();
        try
        {
            ps=cnx.prepareStatement("INSERT INTO les_achats(id,codePanier,libelle,qte,prixU) VALUES(?,?,?,?,?)");
          
            ps.setString(1,codePanier);
            ps.setString(2, libelle);
            ps.setInt(3, qte);
            ps.setString(4,prixUnitaire);
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
         try
        {
            ps=cnx.prepareStatement("UPDATE les_achats set codePanier=?,libelle=?,qte=?,prixU=? WHERE id=?");

            
            
            ps.setString(1,codePanier);
            ps.setString(2, libelle);
            ps.setInt(3, qte);
            ps.setString(4,prixUnitaire);
           ps.setInt(5, id);
            ps.executeUpdate();           
            ps.close();
            JOptionPane.showMessageDialog(null, "Informations modifiée avec succès","SyGestStock/Information",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void select()
    {
        
    }
    public void delete()
    {
        cnx=ClasseConnection.seConnecter();
        try
        {
            ps=cnx.prepareStatement("DELETE FROM les_achats WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
           JOptionPane.showMessageDialog(null, "Achat supprimé avec succès","SyGestStock/Information",JOptionPane.INFORMATION_MESSAGE);
              
          ps.close();   
        }
        catch(HeadlessException | SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Impossible de supprimer l'achat.","SyGestStock/Information",JOptionPane.WARNING_MESSAGE);
        } 
    }
}
