/**
*
* @author Emre Zengin emre.zengin5@ogr.sakarya.edu.tr
* @since 18.04.2023
* <p>
* FonksiyonDeposu sınıfı Main sınıfının okuduğu .java dosyasının içerisindeki fonksiyonları saptar ve
* içinde yorum satırıyla olan ilişkisini tutar. Bu işlemler Java'da olan hazır veri yapılarıyla da yapılabilirdi ama
* istediğim işlemleri daha iyi yönetmek için farklı bir sınıfta tutmak istedim.
* 
* </p>
*/

package pdp_odev;

public class FonksiyonDeposu {
	 	private final String fonksiyonAdi;  //fonksiyon adi
	    private final int javaDocSayisi;   //javadoc sayisi
	    private final int cokSatirSayisi;
	    private final int tekSatirSayisi;
	    /**
	     * 
	     * @param FonksiyonDeposu
	     * <p>
	     * Bu kurucu fonksiyon ilk defa Main'de fonksiyon bulmak
	     * için çağırdığımız zaman çağrılır.
	     * </p>
	     */
	    public FonksiyonDeposu(String fonksiyonAdi) {
	        this.fonksiyonAdi = fonksiyonAdi;
	        this.javaDocSayisi = 0;
	        this.cokSatirSayisi = 0;
	        this.tekSatirSayisi = 0;
	    }
	    /**
	     * 
	     * @param FonksiyonDeposu
	     * <p>
	     * Bu kurucu fonksiyon yorumları bulduktan sonra saklanması için çağrılır.
	     * </p>
	     */
	    public FonksiyonDeposu(String name, int javaDocSayisi, int cokSatirSayisi, int tekSatirSayisi) {
	        this.fonksiyonAdi = name;
	        this.javaDocSayisi = javaDocSayisi;
	        this.cokSatirSayisi = cokSatirSayisi;
	        this.tekSatirSayisi = tekSatirSayisi;
	    }

	    public String getFuncName() {
	        return fonksiyonAdi;
	    }

	    public int getJavaDocNum() {
	        return javaDocSayisi;
	    }

	    public int getCokSatirNum() {
	        return cokSatirSayisi;
	    }

	    public int getTekSatirNum() {
	        return tekSatirSayisi;
	    }
}
