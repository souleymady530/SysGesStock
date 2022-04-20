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
import javax.swing.JComboBox;
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
public class Categorie 
{
 
    private int id;
    private String titre,code,description="";

    public Categorie() {
    }

    public Categorie(String titre, String code, String description) {
        this.titre = titre;
        this.code = code;
        this.description = description;
    }

    public Categorie(int id, String titre, String code, String description) {
        this.id = id;
        this.titre = titre;
        this.code = code;
        this.description = description;
    }

    public static void initaliserLesCategories(JComboBox jcb)
    {
        try
        {
            int index=0;
            cnx=ClasseConnection.seConnecter();
            st=cnx.createStatement();
            res=st.executeQuery("SELECT id FROM les_categories ORDER titre");
            while(res.next())
            {
                jcb.addItem(res.getString("titre"));
            }
            res.close();
            st.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
    
     public void create()
    {
        cnx=ClasseConnection.seConnecter();
        try
        {
            ps=cnx.prepareStatement("INSERT INTO lescategories(titre,code,description) VALUES(?,?,?)");
            ps.setString(1, titre);
            ps.setString(2, code);
            ps.setString(3,description);
           
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
            ps=cnx.prepareStatement("UPDATE lescategories set titre=?,description=? WHERE code=?");

            
             ps.setString(1,titre);
            ps.setString(2,description);
             ps.setString(3, code);
           
           
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
    public static void  delete(String code)
    {
        cnx=ClasseConnection.seConnecter();
        try
        {
            ps=cnx.prepareStatement("DELETE FROM les_categories WHERE code=?");
            ps.setString(1, code);
            ps.executeUpdate();
            ps.close();
           JOptionPane.showMessageDialog(null, "Catégorie supprimée avec succès","SyGestStock/Information",JOptionPane.INFORMATION_MESSAGE);
              
          ps.close();   
        }
        catch(HeadlessException | SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Impossible de supprimer la catégorie.","SyGestStock/Information",JOptionPane.WARNING_MESSAGE);
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
            res=st.executeQuery("SELECT id FROM les_categories ORDER BY id DESC");
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
        return "CATE_NO_"+num;
    }
    
    public static int getIdByTitre(String titre)
    {
         int index=0;
        try
        {
           
            cnx=ClasseConnection.seConnecter();
            st=cnx.createStatement();
            res=st.executeQuery("SELECT id FROM les_categories WHERE titre=\""+titre+"\"");
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
    
    public static void afficher_tous(DefaultTableModel tbm)
    {
        
        tbm.setRowCount(0);
        try
        {
          cnx=ClasseConnection.seConnecter();
          st=cnx.createStatement();
          res=st.executeQuery("SELECT * FROM les_categories ORDER BY titre");
          int num=0;
          while(res.next())
          {
              num++;
             tbm.addRow(new Object[]{num,res.getString("code"),res.getString("titre"),res.getDate("dateAjout").toString()});
             
              
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
    
    public static Categorie getByCode(String code)
    {
        Categorie cat=null;
        //Categorie(String titre, String code, String description)
        try
        {
          cnx=ClasseConnection.seConnecter();
          st=cnx.createStatement();
          res=st.executeQuery("SELECT * FROM les_categories WHERE code=\""+code+"\"");
          if(res.next())
          {
              cat=new Categorie(res.getString("titre"),res.getString("code"),res.getString("description"));
              return cat;
          }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return cat;
    }
    
    
     public static void exportationListe()
   {
       try
        {
        //Cette classe permet de creer le fichier excel lesadherents.xsl lesadherents.xls
            WritableWorkbook wtable=Workbook.createWorkbook(new File("C://Users/Public/lescategories.xls"));
        //on se position alors sur la premiere feuille excel c a d l'onglet 1//on cree le premier onglet
            WritableSheet ws=wtable.createSheet("Liste_des_categories", 0);
        //Mettre un font sur l'entete
            WritableFont font=new WritableFont(WritableFont.TIMES,12,WritableFont.BOLD,true,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);
            WritableCellFormat format=new WritableCellFormat(font);
            Label code,titre,dateAjout;
            
            code=new Label(0,0,"Code",format);
            titre=new Label(1,0,"Titre",format);
            
            dateAjout=new Label(2,0,"Ajouté le",format);
            
          
            
            ws.addCell(code);
            ws.addCell(titre);
            ws.addCell(dateAjout);
            
        
          
          cnx=ClasseConnection.seConnecter();
                st=cnx.createStatement();
                res=st.executeQuery("SELECT * FROM les_categoires");
                int i=1;
           while(res.next())
           {
               code=new Label(0,i,res.getString("code"),format);
                titre=new Label(1,i,res.getString("titre"),format);

                dateAjout=new Label(2,i,res.getDate("dateAjout").toString(),format);
               

 ws.addCell(code);
            ws.addCell(titre);
            ws.addCell(dateAjout);
            
              
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
     
     public static void creerExcel()
    {
        try
        {
        //Cette classe permet de creer le fichier excel lesadherents.xsl lesadherents.xls
            WritableWorkbook wtable=Workbook.createWorkbook(new File("C://Users/Public/modele_liste_categories.xls"));
        //on se position alors sur la premiere feuille excel c a d l'onglet 1//on cree le premier onglet
            WritableSheet ws=wtable.createSheet("Liste_des_categories", 0);
        //Mettre un font sur l'entete
            WritableFont font=new WritableFont(WritableFont.TIMES,12,WritableFont.BOLD,true,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);
            WritableCellFormat format=new WritableCellFormat(font);
             Label code,titre,desc;
            
            code=new Label(0,0,"Code",format);
            titre=new Label(1,0,"Titre",format);
            desc=new Label(2,0,"Description",format);
          
            
          
            
            ws.addCell(code);
            ws.addCell(titre);
            ws.addCell(desc);
            
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

                 
                 
           if(tab[0][0].equals("Code")  & tab[0][1].equals("Titre") & tab[0][2].equals("Description"))
           {
              

               for (int i = 1; i < nl; i++)
               {
                 String var_code=Categorie.chargerCode(),
                   var_titre=tab[i][0],
                 var_desc=tab[i][0];
                 
                 
                 
                   



                 Categorie cate=new Categorie(var_titre,var_code,var_desc);
               cate.create();
                        

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
