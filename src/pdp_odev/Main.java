/**
*
* @author Emre Zengin emre.zengin5@ogr.sakarya.edu.tr
* @since 18.04.2023
* <p>
* Main sınıfı konsoldan aldığı argüman olan *.java dosyasını okur ve dosyadaki fonksiyonları
* ve yorum satırlarını eşler.
* </p>
*/


package pdp_odev;
import java.io.*;
import java.io.FileWriter;
import java.util.*;
import java.util.regex.*;


public class Main {
    public static void main(String[] args) throws Exception {
    	String fileName = args[0]; 
        BufferedReader oku = new BufferedReader(new FileReader(fileName));
        String line;
        Pattern funcPattern = Pattern.compile("(public|private|protected) +[a-zA-Z]+ +([a-zA-Z]+)\\(.*\\) *\\{");
        Pattern constructorPattern = Pattern.compile("(public|private|protected) +([a-zA-Z]+)\\((.*?)\\) *\\{");
        String constructorName="";
        Map<Integer,FonksiyonDeposu> fonksiyonMap = new HashMap<>();
        int i=0;
        while ((line = oku.readLine()) != null) {
            Matcher constructorMatcher = constructorPattern.matcher(line);
            Matcher funcMatcher = funcPattern.matcher(line);
        /*
         * aşağıdaki if bloklarının amacı regex patternleri istenilen yapıyla
         * eşleştiğinde girilmesi için konulmuştur.
         * bunları bulduğumuzda hepsini daha önce tanımladığımız map'e yerleştiririz.
         */
            if (constructorMatcher.find()) {
                constructorName = constructorMatcher.group(2);
                fonksiyonMap.put(i,new FonksiyonDeposu(constructorName));
                i++;
            }
            if (funcMatcher.find()) {
                String functionName = funcMatcher.group(2);
                fonksiyonMap.put(i,new FonksiyonDeposu(functionName));
                i++;
            }
          

            if (line.contains("}")) {
            	funcPattern = Pattern.compile("(public|private|protected) +[a-zA-Z]+ +([a-zA-Z]+)\\(.*\\) *\\{");
            	constructorPattern = Pattern.compile("(public|private|protected) +([a-zA-Z]+)\\((.*?)\\) *\\{");
                
            }
            
        }
        oku.close();
        oku = new BufferedReader(new FileReader(fileName));
        Pattern javaDocPattern =  Pattern.compile("/\\*\\*.*?\\*/", Pattern.DOTALL);
        Pattern cokSatirPattern = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL);
        Pattern tekSatirPattern = Pattern.compile("//.*");
        int javaDocNum=0;
        int cokSatirNum=0;
        int tekSatirNum=0;
        int funcIndex=0;
        int yorumIndex=0;
        int sonYorumIndex=0;
        String yorumKismi="";
        String butunKod="";
    	BufferedWriter javaDocYaz = new BufferedWriter(new FileWriter("javadoc.txt"));
    	BufferedWriter tekSatirYaz = new BufferedWriter(new FileWriter("teksatir.txt"));
    	BufferedWriter cokSatirYaz = new BufferedWriter(new FileWriter("coksatir.txt"));
        StringBuilder birlesikString = new StringBuilder();
        /*
         * StringBuilder metni oluşturduktan sonra istenilen yorum satırlarını buluruz
         */
        while ((line = oku.readLine()) != null) {
        	birlesikString.append(line).append(System.lineSeparator());
        	butunKod = birlesikString.toString();
            Matcher javaDocMatcher = javaDocPattern.matcher(butunKod);
            if (javaDocMatcher.find()) {
            	/*
            	 * yorumIndex ve sonYorumIndex ile sadece istenilen aralığı buluruz.
            	 */
            	yorumIndex = butunKod.indexOf("/**");
            	sonYorumIndex = butunKod.indexOf("*/");
            	yorumKismi= butunKod.substring(yorumIndex,sonYorumIndex+2);
            	javaDocYaz.write("Fonksiyon: ");
            	javaDocYaz.write(fonksiyonMap.get(funcIndex).getFuncName());
            	javaDocYaz.newLine();
            	javaDocYaz.write(yorumKismi);
            	javaDocYaz.newLine();
            	javaDocYaz.write("------------------------------");
            	javaDocYaz.newLine();
            	javaDocNum++;
            	birlesikString.setLength(0);
            	yorumIndex=0;
            	sonYorumIndex=0;
            	yorumKismi="";
            	butunKod="";
            }
            if(!(butunKod.contains("/**")||(butunKod.contains("/*")))) {
            	  Matcher tekSatirMatcher = tekSatirPattern.matcher(butunKod);
                  if (tekSatirMatcher.find()) {
                	yorumIndex = butunKod.indexOf("//");
                  	yorumKismi= butunKod.substring(yorumIndex);
                	tekSatirYaz.write("Fonksiyon: ");
                   	tekSatirYaz.write(fonksiyonMap.get(funcIndex).getFuncName());
                   	tekSatirYaz.newLine();
                	tekSatirYaz.write(yorumKismi);  
                	tekSatirYaz.newLine();
                	tekSatirYaz.write("------------------------------");
                	tekSatirYaz.newLine();
                  	tekSatirNum++;
                  	birlesikString.setLength(0);
                  	yorumIndex=0;
                  	butunKod="";
                  	yorumKismi="";
                  }
            }
          
            Matcher cokSatirMatcher = cokSatirPattern.matcher(butunKod);
            if (cokSatirMatcher.find()) {
            	yorumIndex = butunKod.indexOf("/*");
            	sonYorumIndex = butunKod.indexOf("*/");
            	yorumKismi= butunKod.substring(yorumIndex,sonYorumIndex+2);
            	cokSatirYaz.write("Fonksiyon: ");
            	cokSatirYaz.write(fonksiyonMap.get(funcIndex).getFuncName());
            	cokSatirYaz.newLine();
            	cokSatirYaz.write(yorumKismi); 
            	cokSatirYaz.newLine();
            	cokSatirYaz.write("------------------------------");
            	cokSatirYaz.newLine();
            	cokSatirNum++;
            	birlesikString.setLength(0);
            	yorumIndex=0;
            	sonYorumIndex=0;
            	butunKod="";
            	yorumKismi="";
            }
          

            if (line.contains("}")) {
            	if(funcIndex<i) {String fonksiyonAdi = fonksiyonMap.get(funcIndex).getFuncName();
           	 	fonksiyonMap.replace(funcIndex,new FonksiyonDeposu(fonksiyonAdi,javaDocNum,cokSatirNum,tekSatirNum));
           	 	javaDocNum=0;
           	 	cokSatirNum=0;
           	 	tekSatirNum=0;
            	funcIndex++;
            	butunKod="";
            	birlesikString.setLength(0);
                javaDocPattern =  Pattern.compile("/\\*\\*.*?\\*/", Pattern.DOTALL);
                cokSatirPattern = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL);
                tekSatirPattern = Pattern.compile("//.*");}
            	
            	
            }
            
        }
        oku.close();
        javaDocYaz.close();
        tekSatirYaz.close();
        cokSatirYaz.close();
        System.out.println("Sınıf:" + constructorName);
        System.out.println(); 
        for (Map.Entry<Integer, FonksiyonDeposu> entry : fonksiyonMap.entrySet()) {
            String value = entry.getValue().getFuncName(); 
            int int3= entry.getValue().getJavaDocNum();
            int int2= entry.getValue().getCokSatirNum();
            int int1= entry.getValue().getTekSatirNum();
            System.out.printf("%-10s%s%n%-34s%s%n%-34s%s%n%-34s%s%n%-37s%n",
                    "       Fonksiyon:", value,
                    "           Tek Satır Yorum Sayısı:   ", int1,
                    "           Çok Satırlı Yorum Sayısı: ", int2,
                    "           JavaDoc Yorum Sayısı:     ", int3,
                    "---------------------------------------");
           
        }
        }
    }