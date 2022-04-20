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
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
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
public class Produit 
{
    private int id,qte,qteMin,idCate;
    private String libelle,prixUnitaire;

    public Produit() {
    }

    public Produit(int qte, int qteMin, int idCate, String libelle, String prixUnitaire) {
        this.qte = qte;
        this.qteMin = qteMin;
        this.idCate = idCate;
        this.libelle = libelle;
        this.prixUnitaire = prixUnitaire;
    }

    public Produit(int id, int qte, int qteMin, int idCate, String libelle, String prixUnitaire) {
        this.id = id;
        this.qte = qte;
        this.qteMin = qteMin;
        this.idCate = idCate;
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

    public int getQteMin() {
        return qteMin;
    }

    public void setQteMin(int qteMin) {
        this.qteMin = qteMin;
    }

    public int getIdCate() {
        return idCate;
    }

    public void setIdCate(int idCate) {
        this.idCate = idCate;
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
            ps=cnx.prepareStatement("INSERT INTO lesproduits(idCate,libelle,prixU,qte,qteMin) VALUES(?,?,?,?,?)");
            ps.setInt(1, idCate);
            ps.setString(2, libelle);
            ps.setString(3,prixUnitaire);
           ps.setInt(3, qte);
           ps.setInt(4, qteMin);
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
    
     public static int getIdByLibelle(String lib)
     {
         int index=0;
         try
        {
           
            cnx=ClasseConnection.seConnecter();
            st=cnx.createStatement();
            res=st.executeQuery("SELECT * FROM lesproduits WHERE libelle=\""+lib+"\"");
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
    public void update()
    {
         try
        {
            ps=cnx.prepareStatement("UPDATE lesproduits set idCate=?,libelle=?,prixU=?,qte=?,qteMin=? WHERE id=?");

            
             ps.setInt(1, idCate);
            ps.setString(2, libelle);
            ps.setString(3,prixUnitaire);
           ps.setInt(4, qte);
           ps.setInt(5, qteMin);
           ps.setInt(6, id);
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
    
    //Cette fonction renvoi le nombre de produits restant
    
    public static void delete(String lib)
    {
        cnx=ClasseConnection.seConnecter();
        try
        {
            ps=cnx.prepareStatement("DELETE FROM lesproduits WHERE libelle=?");
            ps.setString(1, lib);
            ps.executeUpdate();
            ps.close();
           JOptionPane.showMessageDialog(null, "Produit supprimé avec succès","SyGestStock/Information",JOptionPane.INFORMATION_MESSAGE);
              
          ps.close();   
        }
        catch(HeadlessException | SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Impossible de supprimer le produit.","SyGestStock/Information",JOptionPane.WARNING_MESSAGE);
        } 
    }
    
    public static Produit getProdByLibelle(String libelle)
    {
        //    public Produit(int id, int qte, int qteMin, int idCate, String libelle, String prixUnitaire) {
//INNER JOIN livres ON (emprunts.lv=livres.code)"
        Produit prod=null;
        try
        {
           
            cnx=ClasseConnection.seConnecter();
            st=cnx.createStatement();
            res=st.executeQuery("SELECT * FROM lesproduits WHERE libelle=\""+libelle+"\" INNER JOIN les_categories ON (categories.id=les_produits.idCate)");
            if(res.next())
            {
               prod=new Produit(res.getInt("id"),res.getInt("qte"),res.getInt("qteMin"),res.getInt("idCate"),res.getString("libelle"),res.getString("prixU")); 
            }
        }
         catch(SQLException e)
         {
             e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Le produit n'existe pas. Verifiez le nom du produit.","SyGestStock/Information",JOptionPane.WARNING_MESSAGE);
         }
        return prod;
    }
    
    
    
    public static int getNbreRestantProduit(String lib)
    {
        Produit p=Produit.getProdByLibelle(lib);
       
         return p.qte>p.qteMin? p.qte:-1;
       
    }
    
    
    public static void afficher_tous(DefaultTableModel tbm)
    {
        
        tbm.setRowCount(0);
        try
        {
          cnx=ClasseConnection.seConnecter();
          st=cnx.createStatement();
          res=st.executeQuery("SELECT * FROM les_produits ORDER BY libelle");
          int num=0;
          while(res.next())
          {
              num++;
             tbm.addRow(new Object[]{num,res.getString("libelle"),res.getString("categorie"),String.valueOf(res.getInt("qte")),String.valueOf(res.getInt("qteMin")),res.getString("prixU")});
             
              
          }
          st.close();
          res.close();
          if(num==0)
          {
            JOptionPane.showMessageDialog(null,"Aucune donnée a afficher.Pas de catégorie enregistrée!","SyGestStock / Information",JOptionPane.YES_OPTION);  
          }
        }
        catch(HeadlessException | SQLException e)
        {
            e.printStackTrace();
        }
       
    }
    
    
    
    
    public static void creerExcel()
    {
        try
        {
        //Cette classe permet de creer le fichier excel lesadherents.xsl lesadherents.xls
            WritableWorkbook wtable=Workbook.createWorkbook(new File("C://Users/Public/modele_liste_produits.xls"));
        //on se position alors sur la premiere feuille excel c a d l'onglet 1//on cree le premier onglet
            WritableSheet ws=wtable.createSheet("Liste_des_produits_modeles", 0);
        //Mettre un font sur l'entete
            WritableFont font=new WritableFont(WritableFont.TIMES,12,WritableFont.BOLD,true,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);
            WritableCellFormat format=new WritableCellFormat(font);
             Label libelle,qte,qteMin,cate,prixU;
            
            libelle=new Label(0,0,"Libellé",format);
            qte=new Label(1,0,"Quantité",format);
            
            qteMin=new Label(2,0,"Quantité Minimale",format);
            cate=new Label(3,0,"Catégorie",format);
            prixU=new Label(4,0,"Prix Unitaire",format);
            
            ws.addCell(libelle);
            ws.addCell(qte);
            ws.addCell(qteMin);
            ws.addCell(cate);
            ws.addCell(prixU);
            
          wtable.write();
          wtable.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            
        }
        
        
    }
     
    
    
    
    
    
     public static void exportationListe()
   {
       try
        {
        //Cette classe permet de creer le fichier excel lesadherents.xsl lesadherents.xls
            WritableWorkbook wtable=Workbook.createWorkbook(new File("C://Users/Public/lesproduits.xls"));
        //on se position alors sur la premiere feuille excel c a d l'onglet 1//on cree le premier onglet
            WritableSheet ws=wtable.createSheet("Liste_des_produits", 0);
        //Mettre un font sur l'entete
            WritableFont font=new WritableFont(WritableFont.TIMES,12,WritableFont.BOLD,true,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);
            WritableCellFormat format=new WritableCellFormat(font);
            Label libelle,qte,qteMin,cate,prixU;
            
            libelle=new Label(0,0,"Libellé",format);
            qte=new Label(1,0,"Quantité",format);
            
            qteMin=new Label(2,0,"Quantité Minimale",format);
            cate=new Label(3,0,"Catégorie",format);
            prixU=new Label(4,0,"Prix Unitaire",format);
            
            ws.addCell(libelle);
            ws.addCell(qte);
            ws.addCell(qteMin);
            ws.addCell(cate);
            ws.addCell(prixU);
            
        
          
          cnx=ClasseConnection.seConnecter();
                st=cnx.createStatement();
                res=st.executeQuery("SELECT * FROM les_produits INNER JOIN les_categories ON (les_categories.id=les_produits.idCate)");
                int i=1;
           while(res.next())
           {
               libelle=new Label(0,i,res.getString("libelle"),format);
                qte=new Label(1,i,String.valueOf(res.getInt("qte")),format);
                 qteMin=new Label(2,i,String.valueOf(res.getInt("qteMin")),format);
                 cate=new Label(3,i,res.getString("titre"),format);
                prixU=new Label(4,i,res.getString("prixU"));

                ws.addCell(libelle);
                ws.addCell(qte);
                ws.addCell(qteMin);
                ws.addCell(cate);
                ws.addCell(prixU);
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
     
       public static void importer(File fl)
    {
      try
      {
          String filename=fl.getName(),tab[][];
         String ext=filename.substring(filename.indexOf("."));
         System.out.print(ext);
       if(ext.equals(".xls") || ext.equals(" .xls") || ext.equals(".xls "))
       {
           System.out.print(ext);
            Workbook wb=Workbook.getWorkbook(fl.getAbsoluteFile());//recupere un fichier workbook depuis le fichier fl
           Sheet sh=wb.getSheet(0);
           tab=new String[sh.getRows()][sh.getColumns()];
           
            int nl=sh.getRows(),nc=sh.getColumns();
                for(int i=0;i<nl;i++)
                {
                    for(int j=0;j<nc;j++)
                    {
                        Cell ce=sh.getCell(j,i);
                        CellType ceType=ce.getType();
                        if (ceType==CellType.LABEL)
                        {
                         tab[i][j]= ce.getContents();
                        }

                        if (ceType==CellType.NUMBER)
                        {
                         tab[i][j]= ce.getContents();
                        }
                    }
                }

                 
                 
           if(tab[0][0].equals("Libellé")  & tab[0][1].equals("Quantité") & tab[0][2].equals("Quantité Minimale") & tab[0][3].equals("Catégorie") & tab[0][2].equals("Prix Unitaire"))
           {
              

               for (int i = 1; i < nl; i++)
               {
                 String 
                   libelle=tab[i][0],cate=tab[i][3],prixU=tab[i][4];
                int  qte=Integer.valueOf(tab[i][1]),qteMin=Integer.valueOf(tab[i][2]);
                 
                 
                 //Categorie(String titre, String code, String description) 
                 Categorie categorie=new Categorie(cate,Categorie.chargerCode(),"Indeterminé");
                 categorie.create();
                 int idCate=Categorie.getIdByTitre(cate);
                 // public Produit(int qte, int qteMin, int idCate, String libelle, String prixUnitaire)
                 Produit prod=new Produit(qte,qteMin,idCate,libelle,prixU);
                 prod.create();
                 
                   



                
             }
               JOptionPane.showMessageDialog(null, "Importation Reussie" ,"Information",JOptionPane.INFORMATION_MESSAGE);
           }
           else
           {
                                  JOptionPane.showMessageDialog(null, "Echec de l'importation, verifiez les noms des colonnes et leurs ordres" ,"Information",JOptionPane.WARNING_MESSAGE);
           }
       }
       else
       {
                   JOptionPane.showMessageDialog(null, "Echec de l'importqtion. L,extension du fichier doit etre de type .xls" ,"Information",JOptionPane.WARNING_MESSAGE);
       }
        
         
          
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
    }
       
      
}
