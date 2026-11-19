package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import gui.Utils;


import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Sale;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("querySalesBean")
@SessionScoped
public class QuerySalesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	BLFacade facadeBL = FacadeBean.getBusinessLogic();

	private String bilaketa = "";
	private List<Sale> salmentak;
	private Sale aukeratua;
	private String mezua;
	private List<String> egoerak = Utils.getStatus();

	public String bilatu() {
		try {
			Date today = UtilDate.trim(new Date());
			salmentak = facadeBL.getPublishedSales(bilaketa, today);
			mezua = "";
			if(salmentak.isEmpty()) {
				mezua = "Ez da produkturik aurkitu";
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && !e.getMessage().isEmpty()) {
				mezua = e.getMessage();
			} else {
				mezua = e.toString();
			}		}
		return null;
	}

	public String ikusi(Sale s) {
		this.aukeratua = s;
		return "EskaintzaIkusi";
	}
	

	public String getEgoeraTestua() {
		if (aukeratua == null) return "";
		int n = aukeratua.getStatus();
		return (n >= 0 && n < egoerak.size()) ? egoerak.get(n) : String.valueOf(n);
	}

	public String getBilaketa() { return bilaketa; }
	public void setBilaketa(String bilaketa) { this.bilaketa = bilaketa; }

	public List<Sale> getSalmentak() { return salmentak; }

	public Sale getAukeratua() { return aukeratua; }

	public String getMezua() { return mezua; }
}