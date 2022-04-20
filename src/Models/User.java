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
import java.io.File;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *
 * @author ofilw
 */
public class User 
{
    private int id;
    private String pseudo,mdp,type,nom,prenom,tel1,tel2;

    public User() {
    }

    public User(String pseudo, String mdp, String type, String nom, String prenom, String tel1, String tel2) {
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.type = type;
        this.nom = nom;
        this.prenom = prenom;
        this.tel1 = tel1;
        this.tel2 = tel2;
    }

    
    public User(int id, String pseudo, String mdp, String type, String nom, String prenom, String tel1, String tel2) {
        this.id = id;
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.type = type;
        this.nom = nom;
        this.prenom = prenom;
        this.tel1 = tel1;
        this.tel2 = tel2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }
    
    
    
    public void create()
    {
        cnx=ClasseConnection.seConnecter();
        try
        {
            ps=cnx.prepareStatement("INSERT INTO utilisateurs(type,pseudo,mdp,nom,prenom,tel1,tel2) VALUES(?,?,?,?,?,?,?)");
            ps.setString(1, type);
            ps.setString(2, pseudo);
            ps.setString(3, mdp);
            ps.setString(4, nom);
            ps.setString(5, prenom);
           
           
            ps.setString(6, tel1);
            ps.setString(7, tel2);
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
        cnx=ClasseConnection.seConnecter();
        try
        {
            ps=cnx.prepareStatement("UPDATE utilisateurs set type=?,pseudo=?,mdp=?,nom=?,prenom=?,tel1=?,tel2=? WHERE id=?");

            
             ps.setString(1,type);
            ps.setString(2, pseudo);
            ps.setString(3, mdp);
            ps.setString(4, nom);
            ps.setString(5, prenom);
             
            
               
                 ps.setString(6, tel1);
                  ps.setString(7, tel2);
             ps.setInt(8, id);
            ps.executeUpdate();           
            ps.close();
            JOptionPane.showMessageDialog(null, "Informations modifiée avec succès","SyGestStock/Information",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public static User select(int id)
    {
        
         try
        {
          cnx=ClasseConnection.seConnecter();
          st=cnx.createStatement();
          res=st.executeQuery("SELECT * FROM utilisateurs WHERE id="+id);
          if(res.next())
          {
              return new User(res.getInt("id"),res.getString("type"),res.getString("pseudo"),res.getString("mdp"),res.getString("nom"),res.getString("prenom"),res.getString("tel1"),res.getString("tel2"));
          }
          else
          {
              return null;
          }
          
         }
         catch(SQLException e)
         {
             e.printStackTrace();
         }
         return null;
    }
    
    
    
   
    
    public static void delete(int id)
    {
        cnx=ClasseConnection.seConnecter();
        try
        {
            ps=cnx.prepareStatement("DELETE FROM utilisateurs WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
           JOptionPane.showMessageDialog(null, "Utilisteur supprimé avec succès","SyGestStock/Information",JOptionPane.INFORMATION_MESSAGE);
              
          ps.close();   
        }
        catch(HeadlessException | SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Impossible de supprimer l'utilisateur","SyGestStock/Information",JOptionPane.WARNING_MESSAGE);
        } 
    }
    
    
     public static void afficher_tous(DefaultTableModel tbm)
    {
        
        tbm.setRowCount(0);
        try
        {
          cnx=ClasseConnection.seConnecter();
          st=cnx.createStatement();
          res=st.executeQuery("SELECT * FROM utilisateurs ORDER BY nom,prenom");
          int num=0;
          while(res.next())
          {
              num++;
             tbm.addRow(new Object[]{num,res.getString("pseudo"),res.getString("type"),String.valueOf(res.getDate("dateAjout"))});
             
              
          }
          st.close();
          res.close();
          if(num==0)
          {
            JOptionPane.showMessageDialog(null,"Aucune donnée a afficher.Pas d'utilisateurs enregistré!","Information",JOptionPane.YES_OPTION);  
          }
        }
        catch(HeadlessException | SQLException e)
        {
            e.printStackTrace();
        }
       
    }
     
      public static String chargerNumeroCarte()
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
        return num;
    }
      
       public static void exportationListe()
   {
       try
        {
        //Cette classe permet de creer le fichier excel lesadherents.xsl lesadherents.xls
            WritableWorkbook wtable=Workbook.createWorkbook(new File("C://Users/Public/lesutilisteurs_sauvegarde.xls"));
        //on se position alors sur la premiere feuille excel c a d l'onglet 1//on cree le premier onglet
            WritableSheet ws=wtable.createSheet("Liste_des_utilisateurs", 0);
        //Mettre un font sur l'entete
            WritableFont font=new WritableFont(WritableFont.TIMES,12,WritableFont.BOLD,true,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);
            WritableCellFormat format=new WritableCellFormat(font);
            Label nom,prenom,pseudo,type,tel1,tel2;
            
            nom=new Label(0,0,"Nom",format);
            prenom=new Label(1,0,"Prenom",format);
            
            type=new Label(2,0,"Type",format);
            pseudo=new Label(3,0,"Pseudo",format);
           
            tel1=new Label(7,0,"Telephone 1",format);
            
            tel2=new Label(8,0,"Telephone 2",format);
          
            
            ws.addCell(nom);
            ws.addCell(prenom);
            ws.addCell(type);
            ws.addCell(pseudo);
            ws.addCell(tel1);
            ws.addCell(tel2);
        
          
          cnx=ClasseConnection.seConnecter();
                st=cnx.createStatement();
                res=st.executeQuery("SELECT * FROM utilisateurs");
                int i=1;
           while(res.next())
           {
                nom=new Label(0,i,res.getString("nom"),format);
                prenom=new Label(1,i,res.getString("prenom"),format);

                pseudo=new Label(2,i,res.getString("type"),format);
                type=new Label(3,i,res.getString("pseudo"),format);
                tel1=new Label(7,i,res.getString("tel1"),format);
                tel2=new Label(8,i,res.getString("tel2"),format);


                ws.addCell(nom);
                ws.addCell(prenom);
                ws.addCell(type);
                ws.addCell(pseudo);
                ws.addCell(tel1);
                ws.addCell(tel2);
              
               i++;
           }
             wtable.write();
          wtable.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            
        }
   }
       
        public static int getIdByPseudo(String pseudo)
       {
           int index=-1;
            try
        {
          cnx=ClasseConnection.seConnecter();
          st=cnx.createStatement();
          res=st.executeQuery("SELECT id FROM utilisateurs WHERE pseudo=\""+pseudo+"\"");
          
          if(res.next())
          {
             
             index=res.getInt("id");
              
          }
          res.close();
          st.close();
        }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            return index;
       }
}
