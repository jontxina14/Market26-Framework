package bean;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import businessLogic.BLFacade;
import configuration.UtilDate;
import gui.Utils;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

@Named("createSaleBean")
@RequestScoped
public class CreateSaleBean {
	
	//proba

	BLFacade facadeBL = FacadeBean.getBusinessLogic();

	private String izenburua;
	private String deskribapena;
	private float prezioa;
	private String egoera;
	private Date data = new Date();
	private Part argazkia;
	private String mezua;
	private List<String> egoerak;

	// De momento fijo; luego lo sacas del bean de login (sesión)
	private String sellerMail = "seller@gmail.com";

	@PostConstruct
	public void init() {
		egoerak = Utils.getStatus();
	}

	public String sortu() {
		try {
			// Part -> File temporal (createSale espera un File)
			File fitxategia = null;
			if (argazkia != null && argazkia.getSize() > 0) {
				fitxategia = File.createTempFile("argazkia_", "_" + argazkia.getSubmittedFileName());
				try (InputStream in = argazkia.getInputStream()) {
					Files.copy(in, fitxategia.toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
			}

			int numStatus = egoerak.indexOf(egoera);

			facadeBL.createSale(izenburua, deskribapena, numStatus, prezioa,
					UtilDate.trim(data), sellerMail, fitxategia);

			mezua = "Produktua ondo sortu da";
			return null; // se queda en la misma página
		} catch (Exception e) {
			mezua = e.getMessage();
			return null;
		}
	}

	public String getIzenburua() { return izenburua; }
	public void setIzenburua(String izenburua) { this.izenburua = izenburua; }

	public String getDeskribapena() { return deskribapena; }
	public void setDeskribapena(String deskribapena) { this.deskribapena = deskribapena; }

	public float getPrezioa() { return prezioa; }
	public void setPrezioa(float prezioa) { this.prezioa = prezioa; }

	public String getEgoera() { return egoera; }
	public void setEgoera(String egoera) { this.egoera = egoera; }

	public Date getData() { return data; }
	public void setData(Date data) { this.data = data; }

	public Part getArgazkia() { return argazkia; }
	public void setArgazkia(Part argazkia) { this.argazkia = argazkia; }

	public String getMezua() { return mezua; }

	public List<String> getEgoerak() { return egoerak; }
}