package bean;

import java.util.Date;
import java.util.List;

import businessLogic.BLFacade;
import configuration.UtilDate;
import gui.Utils;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("createSaleBean")
@RequestScoped
public class CreateSaleBean {

	BLFacade facadeBL = FacadeBean.getBusinessLogic();

	private String izenburua;
	private String deskribapena;
	private float prezioa;
	private String egoera;
	private Date data = new Date();
	private String mezua;
	private List<String> egoerak = Utils.getStatus();

	private String sellerMail = "seller1@gmail.com";

	public String sortu() {
		try {
			int numStatus = egoerak.indexOf(egoera);

			facadeBL.createSale(izenburua, deskribapena, numStatus, prezioa, UtilDate.trim(data), sellerMail, null);

			mezua = "Produktua ondo sortu da. ";
			return null;
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

	public String getMezua() { return mezua; }

	public List<String> getEgoerak() { return egoerak; }
}